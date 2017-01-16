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

/**
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class submission extends structure {

    private String id = null;
    private String studentId = null;
    private String exerciseId = null;
    private String exerciseSheetId = null;
    private String comment = null;
    private file file = null;
    private String accepted = null;
    private String selectedForGroup = null;
    private String date = null;
    private String exerciseNumber = null;
    private String flag = null;
    private String leaderId = null;
    private String hideFile = null;
    private String exerciseName = null;

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
        return new submission(newObj);
    }

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(JsonObject content) {
        return new submission(content);
    }

    /**
     *
     */
    public submission() {

    }

    /**
     *
     * @param content
     */
    public submission(JsonObject content) {
        id = handleStringEntry(content, "id", id);
        studentId = handleStringEntry(content, "studentId", studentId);
        exerciseId = handleStringEntry(content, "exerciseId", exerciseId);
        exerciseSheetId = handleStringEntry(content, "exerciseSheetId", exerciseSheetId);
        comment = handleStringEntry(content, "comment", comment);
        accepted = handleStringEntry(content, "accepted", accepted);
        selectedForGroup = handleStringEntry(content, "selectedForGroup", selectedForGroup);
        date = handleStringEntry(content, "date", date);
        exerciseNumber = handleStringEntry(content, "exerciseNumber", exerciseNumber);
        flag = handleStringEntry(content, "flag", flag);
        leaderId = handleStringEntry(content, "leaderId", leaderId);
        hideFile = handleStringEntry(content, "hideFile", hideFile);
        exerciseName = handleStringEntry(content, "exerciseName", exerciseName);
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
        addIfSet(tmp, "studentId", studentId);
        addIfSet(tmp, "exerciseId", exerciseId);
        addIfSet(tmp, "exerciseSheetId", exerciseSheetId);
        addIfSet(tmp, "comment", comment);
        addIfSet(tmp, "accepted", accepted);
        addIfSet(tmp, "selectedForGroup", selectedForGroup);
        addIfSet(tmp, "date", date);
        addIfSet(tmp, "exerciseNumber", exerciseNumber);
        addIfSet(tmp, "flag", flag);
        addIfSet(tmp, "leaderId", leaderId);
        addIfSet(tmp, "hideFile", hideFile);
        addIfSet(tmp, "exerciseName", exerciseName);
        addIfSet(tmp, "file", file);
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
     * @return the studentId
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
     * @return the exerciseSheetId
     */
    public String getExerciseSheetId() {
        return exerciseSheetId;
    }

    /**
     * @param exerciseSheetId the exerciseSheetId to set
     */
    public void setExerciseSheetId(String exerciseSheetId) {
        this.exerciseSheetId = exerciseSheetId;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * @return the accepted
     */
    public String getAccepted() {
        return accepted;
    }

    /**
     * @param accepted the accepted to set
     */
    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    /**
     * @return the selectedForGroup
     */
    public String getSelectedForGroup() {
        return selectedForGroup;
    }

    /**
     * @param selectedForGroup the selectedForGroup to set
     */
    public void setSelectedForGroup(String selectedForGroup) {
        this.selectedForGroup = selectedForGroup;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the exerciseNumber
     */
    public String getExerciseNumber() {
        return exerciseNumber;
    }

    /**
     * @param exerciseNumber the exerciseNumber to set
     */
    public void setExerciseNumber(String exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the leaderId
     */
    public String getLeaderId() {
        return leaderId;
    }

    /**
     * @param leaderId the leaderId to set
     */
    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    /**
     * @return the hideFile
     */
    public String getHideFile() {
        return hideFile;
    }

    /**
     * @param hideFile the hideFile to set
     */
    public void setHideFile(String hideFile) {
        this.hideFile = hideFile;
    }

    /**
     * @return the exerciseName
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * @param exerciseName the exerciseName to set
     */
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

}
