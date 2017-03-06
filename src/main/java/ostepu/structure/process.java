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
import java.util.List;

/**
 * die Process-Struktur von OSTEPU
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class process extends structure {

    /*
     * die ID des Vorgangs oder Prozess-Eintrags
     */
    private String processId = null;

    /*
     * die zugehörige Aufgabe
     */
    private exercise exercise = null;

    /*
     * die Informationen über die Komponente, welche des Vorgang ausführen soll
     */
    private component target = null;

    /*
     * eventuelle Parameter (beliebig kodiert)
     */
    private String parameter = null;

    /*
     * Vorgangsanhänge erster Art
     */
    private List<attachment> attachment = null;

    /*
     * Vorgangsanhänge zweiter Art
     */
    private List<attachment> workFiles = null;

    /*
     * die Einsendung, welche dann so auch gespeichert werden soll
     */
    private submission submission = null;

    /*
     * die unbehandelte Einsendung des Studenten
     */
    private submission rawSubmission = null;

    /*
     * ein Korrekturobjekt
     */
    private marking marking = null;

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
        return new process(newObj);
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content als JSON Objekt (Eingabe)
     * @return das Objekt
     */
    public static Object decode(JsonObject content) {
        return new process(content);
    }

    /**
     * der Standardkonstruktor
     */
    public process() {
        // kein Inhalt
    }

    /**
     * initialisiert das Objekt anhand eines JSON-Objekts
     *
     * @param content der zukünftige Inhalt
     */
    public process(JsonObject content) {
        processId = handleStringEntry(content, "processId", processId);
        parameter = handleStringEntry(content, "parameter", parameter);
        if (content.has("exercise")) {
            JsonObject rawExercise = content.get("exercise").getAsJsonObject();
            exercise = new exercise(rawExercise);
        }
        if (content.has("target")) {
            JsonObject rawComponent = content.get("target").getAsJsonObject();
            target = new component(rawComponent);
        }
        if (content.has("submission")) {
            JsonObject rawSubmission2 = content.get("submission").getAsJsonObject();
            submission = new submission(rawSubmission2);
        }
        if (content.has("rawSubmission")) {
            JsonObject rawrawSubmission = content.get("rawSubmission").getAsJsonObject();
            rawSubmission = new submission(rawrawSubmission);
        }
        if (content.has("marking")) {
            JsonObject rawMarking = content.get("marking").getAsJsonObject();
            marking = new marking(rawMarking);
        }
        if (content.has("attachment")) {
            attachment = new ArrayList<>();
            JsonArray rawAttachment = content.get("attachment").getAsJsonArray();
            for (JsonElement a : rawAttachment) {
                attachment.add(new attachment(a.getAsJsonObject()));
            }
        }
        if (content.has("workFiles")) {
            workFiles = new ArrayList<>();
            JsonArray rawworkFiles = content.get("workFiles").getAsJsonArray();
            for (JsonElement a : rawworkFiles) {
                workFiles.add(new attachment(a.getAsJsonObject()));
            }
        }

    }

    /**
     * wandelt das Objekt in eine Textdarstellung um (JSON)
     *
     * @return die Textdarstellung (als JSON)
     */
    @Override
    public String encode() {
        JsonObject tmp = new JsonObject();
        addIfSet(tmp, "processId", processId);
        addIfSet(tmp, "exercise", exercise);
        addIfSet(tmp, "target", target);
        addIfSet(tmp, "parameter", parameter);
        // Todo hier fehlt noch das encode!!!
        //addIfSet(tmp, "attachment", attachment);
        //addIfSet(tmp, "workFiles", workFiles);
        addIfSet(tmp, "submission", submission);
        addIfSet(tmp, "rawSubmission", rawSubmission);
        addIfSet(tmp, "marking", marking);
        tmp = super.encodeToObject(tmp);
        return tmp.toString();
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

    /**
     * @return the exercise
     */
    public exercise getExercise() {
        return exercise;
    }

    /**
     * @param exercise the exercise to set
     */
    public void setExercise(exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * @return the target
     */
    public component getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(component target) {
        this.target = target;
    }

    /**
     * @return the parameter
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * @param parameter the parameter to set
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * @return the attachment
     */
    public List<attachment> getAttachment() {
        return attachment;
    }

    /**
     * @param attachment the attachment to set
     */
    public void setAttachment(List<attachment> attachment) {
        this.attachment = attachment;
    }

    /**
     * @return the workFiles
     */
    public List<attachment> getWorkFiles() {
        return workFiles;
    }

    /**
     * @param workFiles the workFiles to set
     */
    public void setWorkFiles(List<attachment> workFiles) {
        this.workFiles = workFiles;
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
     * @return the rawSubmission
     */
    public submission getRawSubmission() {
        return rawSubmission;
    }

    /**
     * @param rawSubmission the rawSubmission to set
     */
    public void setRawSubmission(submission rawSubmission) {
        this.rawSubmission = rawSubmission;
    }

    /**
     * @return the marking
     */
    public marking getMarking() {
        return marking;
    }

    /**
     * @param marking the marking to set
     */
    public void setMarking(marking marking) {
        this.marking = marking;
    }

}
