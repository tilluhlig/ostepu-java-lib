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
public class attachment extends structure {

    private String id=null;
    private String exerciseId=null;
    private file file=null;
    private String processId=null;
    
    /**
     *
     * @param content
     * @return
     */
    public static Object decode(String content) {
        JsonElement obj = new JsonParser().parse(String.join("", content));
        if (obj == null) {
            return null;
        }
        JsonObject newObj = obj.getAsJsonObject();
        return new attachment(newObj);
    }

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(JsonObject content) {
        return new attachment(content);
    }

    /**
     *
     */
    public attachment() {

    }

    /**
     *
     * @param content
     */
    public attachment(JsonObject content) {
        id = handleStringEntry(content, "id", id);
        exerciseId = handleStringEntry(content, "exerciseId", exerciseId);
        processId = handleStringEntry(content, "processId", processId);
        if (content.has("file")) {
            JsonObject rawFile = content.get("file").getAsJsonObject();
            file = new file(rawFile);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String encode() {
        JsonObject tmp = new JsonObject();
        addIfSet(tmp, "id", id);
        addIfSet(tmp, "exerciseId", exerciseId);
        addIfSet(tmp, "file", file);
        addIfSet(tmp, "processId", processId);
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
     * @return the exerciseId
     */
    public String getExerciseId() {
        return exerciseId;
    }

    /**
     * @param exerciseId the exerciseId to set
     */
    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * @return the file
     */
    public file getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(file file) {
        this.file = file;
    }

    /**
     * @return the processId
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

}
