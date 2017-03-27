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
package ostepu.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * Diese Klasse erlaubt http-Aufrufe
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
 */
public class httpRequest {

    /**
     *
     * @param url    die Zieladresse
     * @param method die Aufrufmethode (sowas wie GET, POST, DELETE)
     * @return das Anfrageresultat
     */
    public static httpRequestResult custom(String url, String method) {
        return custom(url, method, "", new noAuth());
    }

    /**
     *
     * @param url    die Zieladresse
     * @param method die Aufrufmethode (sowas wie GET, POST, DELETE)
     * @param auth   eine Authentifizierungsmethode (noAuth, httpAuth)
     * @return das Anfrageresultat
     */
    public static httpRequestResult custom(String url, String method, authentication auth) {
        return custom(url, method, "", auth);
    }

    /**
     *
     * @param url     die Zieladresse
     * @param method  die Aufrufmethode (sowas wie GET, POST, DELETE)
     * @param content der Anfrageinhalt
     * @return das Anfrageresultat
     */
    public static httpRequestResult custom(String url, String method, String content) {
        return custom(url, method, content, new noAuth());
    }

    /**
     * führt eine benutzerdefinierte Anfrage aus (falls eine besondere
     * Anfrageform benötigt wird)
     *
     * @param url     die Zieladresse
     * @param method  die Aufrufmethode (sowas wie GET, POST, DELETE)
     * @param content der Anfrageinhalt
     * @param auth    eine Authentifizierungsmethode (noAuth, httpAuth)
     * @return das Anfrageresultat
     */
    public static httpRequestResult custom(String url, String method, String content, authentication auth) {
        URL urlurl;
        try {
            urlurl = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(httpRequest.class.getName()).log(Level.SEVERE, null, ex);
            return new httpRequestResult(500, new byte[]{});
        }
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) urlurl.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(httpRequest.class.getName()).log(Level.SEVERE, null, ex);
            return new httpRequestResult(500, new byte[]{});
        }

        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException ex) {
            // falsche Methode
            Logger.getLogger(httpRequest.class.getName()).log(Level.SEVERE, null, ex);
            return new httpRequestResult(500, new byte[]{});
        }
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        // connection.setIfModifiedSince(0);
        // connection.setRequestProperty("Cache-Control","no-cache");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        if (auth != null) {
            auth.performAuth(connection);
        }

        if (!"".equals(content)) {
            connection.setRequestProperty("Content-Length", String.valueOf(content.length()));
        }

        // führt die Anfrage aus
        OutputStreamWriter writer = null;
        httpRequestResult Result;
        try {
            if (!"".equals(content)) {
                try {
                    writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(content);
                    writer.flush();
                } catch (IOException ex) {
                    Logger.getLogger(httpRequest.class.getName()).log(Level.SEVERE, null, ex);
                    return new httpRequestResult(500, new byte[]{});
                }
            }
        } finally {
            Result = new httpRequestResult();
            try {
                Result.setStatus(connection.getResponseCode());
            } catch (IOException ex) {
                Logger.getLogger(httpRequest.class.getName()).log(Level.SEVERE, null, ex);
                return new httpRequestResult(500, new byte[]{});
            }

            Result.setHeaders(connection.getHeaderFields());
            // Result.setContent((String) connection.getContent());
            InputStream stream = connection.getErrorStream();
            if (stream == null) {
                try {
                    stream = connection.getInputStream();
                } catch (IOException e) {
                    stream = null;
                }
            }

            if (stream != null) {
                // This is a try with resources, Java 7+ only
                // If you use Java 6 or less, use a finally block instead
                byte[] res;
                try {
                    res = IOUtils.toByteArray(stream);
                    stream.close();
                } catch (IOException ex) {
                    Logger.getLogger(httpRequest.class.getName()).log(Level.SEVERE, null, ex);
                    return new httpRequestResult(500, new byte[]{});
                }
                Result.setContent(res);
            } else {
                Result.setContent("".getBytes());
            }

            Result.setMethod(method);
            Result.setUrl(url);
        }

        // ab hier wird die Antwort zusammengebaut
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException ex) {
                // der Stream konnte nicht geschlossen werden
                Logger.getLogger(httpRequest.class.getName()).log(Level.SEVERE, null, ex);
                return new httpRequestResult(500, new byte[]{});
            }
        }
        connection.disconnect();

        return Result;
    }

    /**
     * führt eine DELETE-Anfrage aus
     *
     * @param url die Zieladresse
     * @return das Anfrageresultat
     */
    public static httpRequestResult delete(String url) {
        return custom(url, "DELETE", "");
    }

    /**
     * führt eine DELETE-Anfrage aus
     *
     * @param url  die Zieladresse
     * @param auth eine Authentifizierungsmethode (noAuth, httpAuth)
     * @return das Anfrageresultat
     */
    public static httpRequestResult delete(String url, authentication auth) {
        return custom(url, "DELETE", "", auth);
    }

    /**
     * führt eine GET-Anfrage aus
     *
     * @param url die Zieladresse
     * @return das Anfrageresultat
     */
    public static httpRequestResult get(String url) {
        return custom(url, "GET", "");
    }

    /**
     * führt eine GET-Anfrage aus
     *
     * @param url  die Zieladresse
     * @param auth eine Authentifizierungsmethode (noAuth, httpAuth)
     * @return das Anfrageresultat
     */
    public static httpRequestResult get(String url, authentication auth) {
        return custom(url, "GET", "", auth);
    }

    /**
     * führt eine POST-Anfrage aus
     *
     * @param url     die Zieladresse
     * @param content der Anfrageinhalt
     * @return das Anfrageresultat
     */
    public static httpRequestResult post(String url, String content) {
        return custom(url, "POST", content);
    }

    /**
     * führt eine POST-Anfrage aus
     *
     * @param url     die Zieladresse
     * @param content der Anfrageinhalt
     * @param auth    eine Authentifizierungsmethode (noAuth, httpAuth)
     * @return das Anfrageresultat
     */
    public static httpRequestResult post(String url, String content, authentication auth) {
        return custom(url, "POST", content, auth);
    }

    /**
     * führt eine PUT-Anfrage aus
     *
     * @param url     die Zieladresse
     * @param content der Anfrageinhalt
     * @return das Anfrageresultat
     */
    public static httpRequestResult put(String url, String content) {
        return custom(url, "PUT", content);
    }

    /**
     * führt eine PUT-Anfrage aus
     *
     * @param url     die Zieladresse
     * @param content der Anfrageinhalt
     * @param auth    eine Authentifizierungsmethode (noAuth, httpAuth)
     * @return das Anfrageresultat
     */
    public static httpRequestResult put(String url, String content, authentication auth) {
        return custom(url, "PUT", content, auth);
    }

}
