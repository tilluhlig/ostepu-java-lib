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
public class marking extends structure {

    private String id = null;
    private submission submission = null;
    private String tutorId = null;
    private String tutorComment = null;
    private file file = null;
    private String points = null;
    private String outstanding = null;
    private String status = null;
    private String date = null;
    private String hideFile = null;

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
        return new marking(newObj);
    }

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(JsonObject content) {
        return new marking(content);
    }

    /**
     *
     */
    public marking() {

    }

    /**
     *
     * @param content
     */
    public marking(JsonObject content) {
        id = handleStringEntry(content, "id", id);
        tutorId = handleStringEntry(content, "tutorId", tutorId);
        tutorComment = handleStringEntry(content, "tutorComment", tutorComment);
        points = handleStringEntry(content, "points", points);
        outstanding = handleStringEntry(content, "outstanding", outstanding);
        status = handleStringEntry(content, "status", status);
        date = handleStringEntry(content, "date", date);
        hideFile = handleStringEntry(content, "hideFile", hideFile);
        if (content.has("file")) {
            JsonObject rawFile = content.get("file").getAsJsonObject();
            file = new file(rawFile);
        }
        if (content.has("submission")) {
            JsonObject rawSubmission = content.get("submission").getAsJsonObject();
            submission = new submission(rawSubmission);
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
        addIfSet(tmp, "tutorId", tutorId);
        addIfSet(tmp, "tutorComment", tutorComment);
        addIfSet(tmp, "points", points);
        addIfSet(tmp, "outstanding", outstanding);
        addIfSet(tmp, "status", status);
        addIfSet(tmp, "date", date);
        addIfSet(tmp, "hideFile", hideFile);
        addIfSet(tmp, "file", file);
        addIfSet(tmp, "submission", submission);
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
     * @return the submission
     */
    public submission getSubmission() {
        return submission;
    }

    /**
     * @param submission the submission to set
     */
    public void setSubmission(submission submission) {
        this.submission = submission;
    }

    /**
     * @return the tutorId
     */
    public String getTutorId() {
        return tutorId;
    }

    /**
     * @param tutorId the tutorId to set
     */
    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    /**
     * @return the tutorComment
     */
    public String getTutorComment() {
        return tutorComment;
    }

    /**
     * @param tutorComment the tutorComment to set
     */
    public void setTutorComment(String tutorComment) {
        this.tutorComment = tutorComment;
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
     * @return the points
     */
    public String getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(String points) {
        this.points = points;
    }

    /**
     * @return the outstanding
     */
    public String getOutstanding() {
        return outstanding;
    }

    /**
     * @param outstanding the outstanding to set
     */
    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
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

}
