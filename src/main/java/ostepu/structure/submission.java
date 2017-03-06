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
 * die Submission-Struktur von OSTEPU
 *
 * @author Till Uhlig <till.uhlig@student.uni-halle.de>
 */
public class submission extends structure {

    /*
     * die ID der Einsendung
     */
    private String id = null;

    /*
     * die ID des Studenten, der sie eingereicht hat
     */
    private String studentId = null;

    /*
     * die ID der Aufgabe
     */
    private String exerciseId = null;

    /*
     * die ID der Übungsserie
     */
    private String exerciseSheetId = null;

    /*
     * der Kommentar des Studenten zu seiner Einsendung
     */
    private String comment = null;

    /*
     * die Datei der Einsendung
     */
    private file file = null;

    /*
     * 1 = die Einsendung wurde akzeptiert, 0 = nicht akzeptiert (eventuell
     * wurde sie zu spät eingesendet)
     */
    private String accepted = null;

    /*
     * ob die Datei in die Punkte eingeht (ob sie also von der Gruppe zur
     * Korrektur ausgewählt wurde)
     */
    private String selectedForGroup = null;

    /*
     * der Unix-Zeitstempel des Einsendens
     */
    private String date = null;

    /*
     * der Textname der Aufgabe (wenn vorhanden)
     */
    private String exerciseNumber = null;

    /*
     * ein Status der Einsendung 0 = wurde durch den Studenten zum Löschen
     * markiert
     */
    private String flag = null;

    /*
     * die Nutzer-ID des Gruppenführers
     */
    private String leaderId = null;

    /*
     * ob die Einsendung als Datei in der Plattform angezeigt werden soll 1 =
     * nein, 0 = ja wird normal angezeigt
     */
    private String hideFile = null;

    /*
     * der Textname der Aufgabe (wenn vorhanden) (wieso doppelt?)
     */
    private String exerciseName = null;

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
        return new submission(newObj);
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content als JSON Objekt (Eingabe)
     * @return das Objekt
     */
    public static Object decode(JsonObject content) {
        return new submission(content);
    }

    /**
     * der Standardkonstruktor
     */
    public submission() {
        // kein Inhalt
    }

    /**
     * initialisiert das Objekt anhand eines JSON-Objekts
     *
     * @param content der zukünftige Inhalt
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
     * wandelt das Objekt in eine Textdarstellung um (JSON)
     *
     * @return die Textdarstellung (als JSON)
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
