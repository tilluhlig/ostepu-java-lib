/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ostepu.structure;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class course extends structure {

    private String id = null;
    private String name = null;
    private String semester = null;
    private String defaultGroupSize = null;

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
        return new course(newObj);
    }

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(JsonObject content) {
        return new course(content);
    }

    /**
     *
     */
    public course() {

    }

    /**
     *
     * @param content
     */
    public course(JsonObject content) {
        id = handleStringEntry(content, "id", id);
        name = handleStringEntry(content, "name", name);
        semester = handleStringEntry(content, "semester", semester);
        defaultGroupSize = handleStringEntry(content, "defaultGroupSize", defaultGroupSize);
    }

    /**
     *
     * @return
     */
    @Override
    public String encode() {
        JsonObject tmp = new JsonObject();
        addIfSet(tmp, "id", id);
        addIfSet(tmp, "name", name);
        addIfSet(tmp, "semester", semester);
        addIfSet(tmp, "defaultGroupSize", defaultGroupSize);
        
        tmp = merge(tmp, super.encodeToObject());
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
     * @return the semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * @param semester the semester to set
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * @return the defaultGroupSize
     */
    public Integer getDefaultGroupSize() {
        return Integer.parseInt(defaultGroupSize);
    }

    /**
     * @param defaultGroupSize the defaultGroupSize to set
     */
    public void setDefaultGroupSize(Integer defaultGroupSize) {
        this.defaultGroupSize = defaultGroupSize.toString();
    }

}
