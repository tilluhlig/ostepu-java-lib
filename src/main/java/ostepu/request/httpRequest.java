/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import org.apache.commons.io.IOUtils;

/**
 * Diese Klasse erlaubt http-Aufrufe
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class httpRequest {

    /**
     *
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static httpRequestResult custom(String url) throws MalformedURLException, ProtocolException, IOException {
        return custom(url, "GET", "", new noAuth());
    }

    /**
     *
     * @param url
     * @param auth
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static httpRequestResult custom(String url, authentication auth) throws MalformedURLException, ProtocolException, IOException {
        return custom(url, "GET", "", auth);
    }

    /**
     *
     * @param url
     * @param method
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static httpRequestResult custom(String url, String method) throws MalformedURLException, ProtocolException, IOException {
        return custom(url, method, "", new noAuth());
    }

    /**
     *
     * @param url
     * @param method
     * @param auth
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static httpRequestResult custom(String url, String method, authentication auth) throws MalformedURLException, ProtocolException, IOException {
        return custom(url, method, "", auth);
    }

    /**
     *
     * @param url
     * @param method
     * @param content
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static httpRequestResult custom(String url, String method, String content) throws MalformedURLException, ProtocolException, IOException {
        return custom(url, method, content, new noAuth());
    }

    /**
     * führt eine benutzerdefinierte Anfrage aus (falls eine besondere
     * Anfrageform benötigt wird)
     *
     * @param url     die Zieladresse
     * @param method  die Aufrufmethode (sowas wie GET, POST, DELETE)
     * @param content der Anfrageinhalt
     * @param auth
     * @return das Anfrageresultat
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static httpRequestResult custom(String url, String method, String content, authentication auth) throws MalformedURLException, ProtocolException, IOException {
        URL urlurl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlurl.openConnection();
        connection.setRequestMethod(method);
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
                writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(content);
                writer.flush();
            }
        } finally {
            Result = new httpRequestResult();
            Result.setStatus(connection.getResponseCode());
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
                byte[] res = IOUtils.toByteArray(stream);
                stream.close();
                Result.setContent(res);
            } else {
                Result.setContent("".getBytes());
            }
            
            Result.setMethod(method);
            Result.setUrl(url);
        }

        // ab hier wird die Antwort zusammengebaut
        if (writer != null) {
            writer.close();
        }
        connection.disconnect();
        
        return Result;
    }

    /**
     * führt eine DELETE-Anfrage aus
     *
     * @param url die Zieladresse
     * @return das Anfrageresultat
     * @throws Exception
     */
    public static httpRequestResult delete(String url) throws Exception {
        return custom(url, "DELETE", "");
    }

    /**
     *
     * @param url
     * @param auth
     * @return
     * @throws Exception
     */
    public static httpRequestResult delete(String url, authentication auth) throws Exception {
        return custom(url, "DELETE", "", auth);
    }

    /**
     * führt eine GET-Anfrage aus
     *
     * @param url die Zieladresse
     * @return das Anfrageresultat
     * @throws Exception
     */
    public static httpRequestResult get(String url) throws Exception {
        return custom(url, "GET", "");
    }

    /**
     *
     * @param url
     * @param auth
     * @return
     * @throws Exception
     */
    public static httpRequestResult get(String url, authentication auth) throws Exception {
        return custom(url, "GET", "", auth);
    }

    /**
     * führt eine POST-Anfrage aus
     *
     * @param url     die Zieladresse
     * @param content der Anfrageinhalt
     * @return das Anfrageresultat
     * @throws Exception
     */
    public static httpRequestResult post(String url, String content) throws Exception {
        return custom(url, "POST", content);
    }

    /**
     *
     * @param url
     * @param content
     * @param auth
     * @return
     * @throws Exception
     */
    public static httpRequestResult post(String url, String content, authentication auth) throws Exception {
        return custom(url, "POST", content, auth);
    }

    /**
     * führt eine PUT-Anfrage aus
     *
     * @param url     die Zieladresse
     * @param content der Anfrageinhalt
     * @return das Anfrageresultat
     * @throws Exception
     */
    public static httpRequestResult put(String url, String content) throws Exception {
        return custom(url, "PUT", content);
    }

    /**
     *
     * @param url
     * @param content
     * @param auth
     * @return
     * @throws Exception
     */
    public static httpRequestResult put(String url, String content, authentication auth) throws Exception {
        return custom(url, "PUT", content, auth);
    }
    
}
