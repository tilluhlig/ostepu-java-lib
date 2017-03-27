/* 
 * Copyright (C) 2017 Till Uhlig <till.uhlig@student.uni-halle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ostepu.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import ostepu.cconfig.cconfig;
import ostepu.request.authentication;
import ostepu.request.httpRequestResult;
import ostepu.request.noAuth;
import ostepu.structure.component;
import ostepu.structure.file;
import ostepu.structure.reference;

/**
 * Diese Klasse enthält Methoden für den Umgang mit Dateien zwischen OSTEPU und
 * externen Bibliotheken
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
 */
public class fileUtils {

    /**
     * liefert den Inhalt einer Datei, welches sich hinter dem file-Object
     * befindet
     *
     * @param context    der Kontext des Servlet
     * @param fileObject das Dateiobjekt
     * @param useCache   ob die Datei lokal gespeichert und abgefragt werden
     *                   soll (true = nutze Cache, false = sonst)
     * @return der Inhalt der Datei
     */
    public static byte[] getFile(ServletContext context, file fileObject, boolean useCache) {
        return getFile(context, fileObject, useCache, new noAuth());
    }

    /**
     * liefert den Inhalt einer Datei, welches sich hinter dem file-Object
     * befindet
     *
     * @param context    der Kontext des Servlet
     * @param fileObject das Dateiobjekt
     * @return der Inhalt der Datei
     */
    public static byte[] getFile(ServletContext context, file fileObject) {
        return getFile(context, fileObject, true, new noAuth());
    }

    /**
     * liefert den Inhalt einer Datei, welches sich hinter dem file-Object
     * befindet
     *
     * @param context    der Kontext des Servlet
     * @param fileObject das Dateiobjekt
     * @param auth       die Authentifizierungsdaten
     * @return der Inhalt der Datei
     */
    public static byte[] getFile(ServletContext context, file fileObject, authentication auth) {
        return getFile(context, fileObject, true, auth);
    }

    /**
     * liefert den Inhalt einer Datei, welches sich hinter dem file-Object
     * befindet
     *
     * @param context    der Kontext des Servlet
     * @param fileObject das Dateiobjekt
     * @param useCache   ob die Datei lokal gespeichert und abgefragt werden
     *                   soll (true = nutze Cache, false = sonst)
     * @param auth       die Authentifizierungsdaten
     * @return der Inhalt der Datei
     */
    public static byte[] getFile(ServletContext context, file fileObject, boolean useCache, authentication auth) {
        if (fileObject.getAddress() != null) {
            // das Dateiobjekt hat eine normale Adresse
            return getFile(context, "/" + fileObject.getAddress() + "/" + fileObject.getDisplayName(), useCache, auth);
        } else {
            if (fileObject.getBody() != null) {
                Object body = fileObject.getBody();
                if (body.getClass() == String.class) {
                    // es ist ein base64 inhalt
                    Decoder dec = Base64.getDecoder();
                    return dec.decode((String) body);
                } else {
                    // es ist eine Referenz
                    reference ref = (reference) body;
                    if (ref.getGlobalRef() != null) {
                        // es gibt eine globale Adresse, welche wir verwenden können
                        return getFile(context, "/" + ref.getGlobalRef() + "/" + fileObject.getDisplayName(), useCache, auth);
                    }
                    // wenn es keine gültige globale Adresse gibt, dann können
                    // wir hier nichts weiter machen
                }
            }
        }
        return null;
    }

    /**
     * liefert den Inhalt einer Datei, welche sich hinter URL befindet
     *
     * @param context der Kontext des Servlet
     * @param URL     die Addresse der Datei
     * @return der Inhalt der Datei
     */
    public static byte[] getFile(ServletContext context, String URL) {
        return getFile(context, URL, true, new noAuth());
    }

    /**
     * liefert den Inhalt einer Datei, welche sich hinter URL befindet
     *
     * @param context der Kontext des Servlet
     * @param URL     die Addresse der Datei
     * @param auth    die Authentifizierungsdaten
     * @return der Inhalt der Datei
     */
    public static byte[] getFile(ServletContext context, String URL, authentication auth) {
        return getFile(context, URL, true, auth);
    }

    /**
     *
     * @param context  der Kontext des Servlet
     * @param URL      die Addresse der Datei
     * @param useCache ob die Datei lokal gespeichert und abgefragt werden soll
     *                 (true = nutze Cache, false = sonst)
     * @return der Inhalt der Datei
     */
    public static byte[] getFile(ServletContext context, String URL, boolean useCache) {
        return getFile(context, URL, useCache, new noAuth());
    }

    /**
     * liefert den Inhalt einer Datei, welche sich hinter URL befindet dabei
     * wird der getFile Ausgang genutzt und OSTEPU mittels URL angefragt
     *
     * @param context  der Kontext des Servlet
     * @param URL      die Addresse der Datei
     * @param useCache ob die Datei lokal gespeichert und abgefragt werden soll
     *                 (true = nutze Cache, false = sonst)
     * @param auth     die Authentifizierungsdaten
     * @return der Inhalt der Datei
     */
    public static byte[] getFile(ServletContext context, String URL, boolean useCache, authentication auth) {
        String filePath = null;

        if (useCache) {
            filePath = fileCache.getCachedFilePath(context, URL);
        }

        if (filePath != null) {
            try {
                // die Datei existiert bereits lokal, also können wir diese nutzen
                InputStream inp = new FileInputStream(filePath);
                byte[] res = IOUtils.toByteArray(inp);
                inp.close();
                return res;
            } catch (IOException ex) {
                Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            try {
                // die Datei muss beim Zielsystem abgerufen werden
                component conf = cconfig.loadConfig(context);
                httpRequestResult result = cconfig.callLinkByName(conf, "getFile", URL, "GET", "", auth);

                if (result.getStatus() == 401) {
                    // der Zugang wurde verweigert
                    Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, "Zugang verweigert");
                    return null;
                }

                if (result.getStatus() != 200) {
                    // es gab einen Fehler beim Abrufen
                    Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, "Datei konnte nicht abgerufen werden");
                    return null;
                }

                // nun soll die Datei bei uns zwischengespeichert werden
                if (useCache) {
                    fileCache.cacheFile(context, result.getContent(), URL);
                }

                return result.getContent();
            } catch (Exception ex) {
                Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
    }

    /**
     * kodiert einen String als Base64
     *
     * @param content der zu kodierende Text
     * @return der Base64 kodierte Text
     */
    public static String encodeBase64(String content) {
        Encoder enc = Base64.getEncoder();
        return new String(enc.encode(content.getBytes()));
    }

    /**
     * dekodiert einen Base64 Text
     *
     * @param content der Base64 kodierte Text
     * @return der resultierende Text
     */
    public static String decodeBase64(String content) {
        Decoder dec = Base64.getDecoder();
        return new String(dec.decode(content));
    }

}
