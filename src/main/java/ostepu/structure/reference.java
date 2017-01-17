/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.structure;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class reference extends structure {

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(String content) {
        JsonElement obj = new JsonParser().parse(String.join("", content));
        if (obj.isJsonNull()) {
            return null;
        }
        JsonObject newObj = obj.getAsJsonObject();
        return new reference(newObj);
    }

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(JsonObject content) {
        return new reference(content);
    }

    private String globalRef = null;
    private String localRef = null;

    /**
     *
     */
    public reference() {

    }

    /**
     *
     * @param content
     */
    public reference(JsonObject content) {
        localRef = handleStringEntry(content, "localRef", localRef);
        globalRef = handleStringEntry(content, "globalRef", globalRef);
    }

    /**
     *
     * @return
     */
    @Override
    public String encode() {
        JsonObject tmp = new JsonObject();
        addIfSet(tmp, "localRef", getLocalRef());
        addIfSet(tmp, "globalRef", getGlobalRef());

        tmp = merge(tmp, super.encodeToObject());
        return tmp.toString();
    }

    /**
     * @return the globalRef
     */
    public String getGlobalRef() {
        return globalRef;
    }

    /**
     * @param globalRef the globalRef to set
     */
    public void setGlobalRef(String globalRef) {
        this.globalRef = globalRef;
    }

    /**
     * @return the localRef
     */
    public String getLocalRef() {
        return localRef;
    }

    /**
     * @param localRef the localRef to set
     */
    public void setLocalRef(String localRef) {
        this.localRef = localRef;
    }

}
