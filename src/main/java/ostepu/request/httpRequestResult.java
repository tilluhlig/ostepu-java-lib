/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.request;

import java.util.List;
import java.util.Map;

/**
 * Diese Klasse stellt das Resultat eines http-Aufrufs dar
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class httpRequestResult {

    /**
     * der Inhalt der Anfrage (body)
     */
    private byte[] content;

    /**
     * der http-Status (sowas wie 200, 201, 404)
     */
    private int status;

    /**
     * eine Liste der Header
     */
    private Map<String, List<String>> headers;

    /**
     * die aufgerufene URL
     */
    private String url;

    /**
     * die Methode des Aufrufs
     */
    private String method;

    /**
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the headers
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

}
