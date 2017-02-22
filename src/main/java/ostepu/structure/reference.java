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
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content die Texteingabe
     * @return das Objekt
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
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content als JSON Objekt (Eingabe)
     * @return das Objekt
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
        tmp = super.encodeToObject(tmp);
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
