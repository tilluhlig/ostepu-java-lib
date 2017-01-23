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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
abstract public class structure {

    /**
     *
     * @param target
     * @param source
     * @return
     */
    protected final JsonObject merge(JsonObject target, JsonObject source) {
        for (Map.Entry a : source.entrySet()) {
            target.add((String) a.getKey(), (JsonElement) a.getValue());
        }
        return target;
    }

    /**
     *
     * @param target
     * @param elementName
     * @param element
     */
    protected final void addIfSet(JsonObject target, String elementName, String element) {
        if (element != null) {
            target.addProperty(elementName, element);
        }
    }
    
        protected final void addIfSet(JsonObject target, String elementName, Object element) {
        if (element != null) {
            if (element.getClass() == String.class){
                addIfSet(target, elementName, (String) element);
            } else {
                addIfSet(target, elementName, (structure) element);                
            }
        }
    }

    /**
     *
     * @param target
     * @param elementName
     * @param element
     */
    protected final void addIfSet(JsonObject target, String elementName, structure element) {
        if (element != null) {
            String enc = element.encode();
            JsonElement obj = new JsonParser().parse(String.join("", enc));
            if (obj.getAsJsonObject().size() > 0) {
                target.add(elementName, obj);
            }
        }
    }

    /**
     *
     * @param content
     * @param name
     * @param defaultValue
     * @return
     */
    protected final String handleStringEntry(JsonObject content, String name, String defaultValue) {
        if (content.has(name)) {
            return content.get(name).getAsString();
        }
        return defaultValue;
    }

    /**
     *
     * @param content
     * @param name
     * @param defaultValue
     * @return
     */
    protected final Integer handleIntEntry(JsonObject content, String name, Integer defaultValue) {
        if (content.has(name)) {
            return content.get(name).getAsInt();
        }
        return defaultValue;
    }

    /**
     *
     * @param content
     * @return
     */
    protected static Object decode(String content) {
        return null;
    }

    /**
     *
     * @param content
     * @return
     */
    protected static Object decode(JsonObject content) {
        return null;
    }

    /**
     *
     * @param content
     * @return
     */
    protected static Object[] decode(JsonArray content) {
        return null;
    }

    /**
     *
     * @param content
     * @return
     */
    protected static Object[] decodeArray(String content) {
        return null;
    }

    /**
     *
     * @return
     */
    public String encode() {
        return encodeToObject().toString();
    }

    /**
     *
     * @return
     */
    public JsonObject encodeToObject() {
        JsonObject tmp = new JsonObject();
        addIfSet(tmp, "sender", getSender());
        addIfSet(tmp, "status", getStatus());
        addIfSet(tmp, "messages", getMessages());
        addIfSet(tmp, "structure", getStructure());
        return tmp;
    }

    private String sender;
    private String status;
    private String messages;
    private String structure;
    private String language;

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the messages
     */
    public String getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(String messages) {
        this.messages = messages;
    }

    /**
     * @return the structure
     */
    public String getStructure() {
        return structure;
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(String structure) {
        this.structure = structure;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
