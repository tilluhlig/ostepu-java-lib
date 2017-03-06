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
import java.util.ArrayList;

/**
 * die Component-Struktur von OSTEPU
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class component extends structure {

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
        return new component(newObj);
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content content als JSON Objekt (Eingabe)
     * @return das Objekt
     */
    public static Object decode(JsonObject content) {
        return new component(content);
    }

    /**
     * der Standardkonstruktor
     */
    public component() {
        // kein Inhalt
    }

    /**
     * initialisiert das Objekt anhand eines JSON-Objekts
     *
     * @param content der zukünftige Inhalt
     */
    public component(JsonObject content) {
        id = handleStringEntry(content, "id", id);
        name = handleStringEntry(content, "name", name);
        address = handleStringEntry(content, "address", address);
        option = handleStringEntry(content, "option", option);
        prefix = handleStringEntry(content, "prefix", prefix);
        if (content.has("links")) {
            JsonArray rawLinks = content.get("links").getAsJsonArray();
            for (JsonElement rawLink : rawLinks) {
                link newLink = new link(rawLink.getAsJsonObject());
                addLink(newLink);
            }
        }
    }

    /**
     * die globale ID
     */
    private String id = null;

    /**
     * der Name
     */
    private String name = null;

    /**
     * die absolute Adresse der Komponente
     */
    private String address = null;

    /**
     * zusätzliche Optionen
     */
    private String option = null;

    /**
     * behandelete Präfixe (veraltet)
     */
    private String prefix = null;

    /**
     * eine Liste der verfügbaren Links
     */
    private ArrayList<link> links = null;

    /**
     * wandelt das Objekt in eine Textdarstellung um (JSON)
     *
     * @return die Textdarstellung (als JSON)
     */
    @Override
    public String encode() {
        JsonObject tmp = new JsonObject();
        addIfSet(tmp, "id", id);
        addIfSet(tmp, "name", name);
        addIfSet(tmp, "address", address);
        addIfSet(tmp, "option", option);
        addIfSet(tmp, "prefix", prefix);
        //addIfSet(tmp, "links", accepted);
        tmp = super.encodeToObject(tmp);
        return tmp.toString();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the option
     */
    public String getOption() {
        return option;
    }

    /**
     * @param option the option to set
     */
    public void setOption(String option) {
        this.option = option;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the links
     */
    public ArrayList<link> getLinks() {
        if (links == null) {
            return new ArrayList<link>();
        }
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(ArrayList<link> links) {
        this.links = links;
    }

    /**
     * fügt einen Link zur Linkliste hinzu
     *
     * @param newLink der neue Link
     */
    public final void addLink(link newLink) {
        if (links == null) {
            links = new ArrayList<link>();
        }
        links.add(newLink);
    }

}
