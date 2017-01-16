/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.structure;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class component extends structure {

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
        return new component(newObj);
    }

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(JsonObject content) {
        return new component(content);
    }

    /**
     *
     */
    public component() {

    }

    /**
     *
     * @param content
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
     *
     * @return
     */
    @Override
    public String encode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
     *
     * @param newLink
     */
    public final void addLink(link newLink) {
        if (links == null) {
            links = new ArrayList<link>();
        }
        links.add(newLink);
    }

}
