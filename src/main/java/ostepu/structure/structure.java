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
 * die Basisstruktur von OSTEPU
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
abstract public class structure {

    /**
     * die Attribute zweier JSON-Objekte werden vereint
     *
     * @param target das Zielobjekt
     * @param source das Quellobjekt
     * @return das Zielobjekt mit den hinzugefügten Attributen
     */
    protected final JsonObject merge(JsonObject target, JsonObject source) {
        for (Map.Entry a : source.entrySet()) {
            target.add((String) a.getKey(), (JsonElement) a.getValue());
        }
        return target;
    }

    /**
     * setzt elementName in target, wenn element nicht null ist
     *
     * @param target      das Zielobjekt
     * @param elementName der Schlüssel des Attributs
     * @param element     der Wert des Attributs
     */
    protected final void addIfSet(JsonObject target, String elementName, String element) {
        if (element != null) {
            target.addProperty(elementName, element);
        }
    }

    /**
     * setzt elementName in target, wenn element nicht null ist
     *
     * @param target      das Zielobjekt
     * @param elementName der Schlüssel des Attributs
     * @param element     der Wert des Attributs
     */
    protected final void addIfSet(JsonObject target, String elementName, Object element) {
        if (element != null) {
            if (element.getClass() == String.class) {
                addIfSet(target, elementName, (String) element);
            } else {
                addIfSet(target, elementName, (structure) element);
            }
        }
    }

    /**
     * setzt elementName in target, wenn element nicht null ist
     *
     * @param target      das Zielobjekt
     * @param elementName der Schlüssel des Attributs
     * @param element     der Wert des Attributs
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
     * liefert den Inhalt des Attributs oder defaultValue
     *
     * @param content      das Objekt
     * @param name         der gesuchte Attributname
     * @param defaultValue der Wert, falls die Zelle nicht existiert
     * @return der Inhalt von content[name] oder defaultValue
     */
    protected final String handleStringEntry(JsonObject content, String name, String defaultValue) {
        if (content.has(name)) {
            return content.get(name).getAsString();
        }
        return defaultValue;
    }

    /**
     * liefert den Inhalt des Attributs oder defaultValue
     *
     * @param content      das Objekt
     * @param name         der gesuchte Attributname
     * @param defaultValue der Wert, falls die Zelle nicht existiert
     * @return der Inhalt von content[name] oder defaultValue
     */
    protected final Integer handleIntEntry(JsonObject content, String name, Integer defaultValue) {
        if (content.has(name)) {
            return content.get(name).getAsInt();
        }
        return defaultValue;
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content die Texteingabe
     * @return das Objekt
     */
    protected static Object decode(String content) {
        return null;
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content als JSON Objekt (Eingabe)
     * @return das Objekt
     */
    protected static Object decode(JsonObject content) {
        return null;
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt[] um
     *
     * @param content als JSON Objekt[] (Eingabe)
     * @return das Objekt[]
     */
    protected static Object[] decode(JsonArray content) {
        return null;
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt[] um
     *
     * @param content als JSON Objekt[] (Eingabe)
     * @return das Objekt[]
     */
    protected static Object[] decodeArray(String content) {
        return null;
    }

    /**
     * wandelt das Objekt in eine Textdarstellung um (JSON)
     *
     * @return die Textdarstellung (als JSON)
     */
    public String encode() {
        return encodeToObject().toString();
    }

    /**
     * wandelt das Objekt in eine Textdarstellung um (JSON)
     *
     * @return die Textdarstellung (als JSON)
     */
    public JsonObject encodeToObject() {
        return encodeToObject(new JsonObject());
    }

    /**
     * bereit ein encode vor, indem die Daten dieses Objekts an object
     * angehangen werden
     *
     * @param object das Eingabeobjekt
     * @return das Eingabeobjekt + {sender, messages, status, structure}
     */
    public JsonObject encodeToObject(JsonObject object) {
        if (messages != null) {
            JsonArray mess = new JsonArray();
            for (String me : messages) {
                mess.add(me);
            }
            object.add("messages", mess);
        }

        addIfSet(object, "sender", getSender());
        addIfSet(object, "status", getStatus());
        addIfSet(object, "structure", getStructure());
        return object;
    }

    /*
     * der Absender (keine Verwendung)
     */
    private String sender;

    /*
     * ein HTTP-Status zum Objekt (sowas wie 201 = erstellt, 409 = Problem)
     */
    private String status;

    /*
     * Meldungen, welche dem Nutzer so mitgeteilt werden können
     */
    private String[] messages;

    /*
     * der Strukturtyp (keine Verwendung)
     */
    private String structure;

    /*
     * die bevorzugte Sprache (de, en, ...) (keine Verwendung)
     */
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
    public String[] getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(String[] messages) {
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
