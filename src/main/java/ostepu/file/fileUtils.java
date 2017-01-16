/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import ostepu.cconfig.cconfig;
import ostepu.request.authentication;
import ostepu.request.httpRequestResult;
import ostepu.request.noAuth;
import ostepu.structure.component;

/**
 * Diese Klasse enthält Methoden für den Umgang mit Dateien zwischen OSTEPU und
 * externen Bibliotheken
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class fileUtils {

    /**
     * liefert den Inhalt einer Datei, welche sich hinter URL befindet
     *
     * @param context der Kontext des Servlet
     * @param URL     die Addresse der Datei
     * @return der Inhalt der Datei
     * @throws IOException
     * @throws Exception
     */
    public static byte[] getFile(ServletContext context, String URL) throws IOException, Exception {
        return getFile(context, URL, true, new noAuth());
    }

    /**
     *
     * @param context
     * @param URL
     * @param auth
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static byte[] getFile(ServletContext context, String URL, authentication auth) throws IOException, Exception {
        return getFile(context, URL, true, auth);
    }

    /**
     *
     * @param context  der Kontext des Servlet
     * @param URL      die Addresse der Datei
     * @param useCache ob die Datei lokal gespeichert und abgefragt werden soll
     *                 (true = nutze Cache, false = sonst)
     * @return der Inhalt der Datei
     * @throws IOException
     * @throws Exception
     */
    public static byte[] getFile(ServletContext context, String URL, boolean useCache) throws IOException, Exception {
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
     * @param auth
     * @return der Inhalt der Datei
     * @throws IOException
     * @throws Exception
     */
    public static byte[] getFile(ServletContext context, String URL, boolean useCache, authentication auth) throws IOException, Exception {
        String filePath = null;

        if (useCache) {
            filePath = fileCache.getCachedFilePath(context, URL);
        }

        if (filePath != null) {
            // die Datei existiert bereits lokal, also können wir diese nutzen
            InputStream inp = new FileInputStream(filePath);
            byte[] res = IOUtils.toByteArray(inp);
            inp.close();
            return res;
        } else {
            // die Datei muss beim Zielsystem abgerufen werden
            component conf = cconfig.loadConfig(context);
            httpRequestResult result = cconfig.callLinkByName(conf, "getFile", URL, "GET", "", auth);

            // nun soll die Datei bei uns zwischengespeichert werden
            if (useCache) {
                fileCache.cacheFile(context, result.getContent(), URL);
            }

            return result.getContent();
        }
    }

}
