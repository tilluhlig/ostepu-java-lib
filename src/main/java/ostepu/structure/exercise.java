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
 * die Exercise-Struktur von OSTEPU
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
 */
public class exercise extends structure {

    /*
     * die ID der Aufgabe
     */
    private String id = null;

    /*
     * die ID der Veranstaltung
     */
    private String courseId = null;

    /*
     * die ID der Übungsserie
     */
    private String sheetId = null;

    /*
     * die Maximalpunktzahl
     */
    private String maxPoints = null;

    /*
     * die ID des Aufgabentyps
     */
    private String type = null;

    /*
     * gibt die Hauptaufgabe an (dient der Aufgabenreihenfolge) Bsp.: 1 ist dann
     * Aufgabe 1
     */
    private String link = null;

    /*
     * true = Bonusaufgabe, false = sonst
     */
    private String bonus = null;

    /*
     * dient der Aufgabenreihenfolge und der Nummerierung (gibt die Position in
     * der Hauptaufgabe an) Bsp. link=4 bedeutet Unteraufgabe c
     */
    private String linkName = null;

    /*
     * ob bei dieser Aufgabe etwas eingesendet werden kann true = ja, false =
     * nein
     */
    private String submittable = null;

    /*
     * ob die Korrekturdatei noch vor Ablauf des Übungszeitraums sichtbar ist
     * (für den Studenten) true = ja, false = nein
     */
    private String resultVisibility = null;

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
        return new exercise(newObj);
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content als JSON Objekt (Eingabe)
     * @return das Objekt
     */
    public static Object decode(JsonObject content) {
        return new exercise(content);
    }

    /**
     * der Standardkonstruktor
     */
    public exercise() {
        // kein Inhalt
    }

    /**
     * initialisiert das Objekt anhand eines JSON-Objekts
     *
     * @param content der zukünftige Inhalt
     */
    public exercise(JsonObject content) {
        id = handleStringEntry(content, "id", id);
        courseId = handleStringEntry(content, "courseId", courseId);
        sheetId = handleStringEntry(content, "sheetId", sheetId);
        maxPoints = handleStringEntry(content, "maxPoints", maxPoints);
        type = handleStringEntry(content, "type", type);
        link = handleStringEntry(content, "link", link);
        bonus = handleStringEntry(content, "bonus", bonus);
        linkName = handleStringEntry(content, "linkName", linkName);
        submittable = handleStringEntry(content, "submittable", submittable);
        resultVisibility = handleStringEntry(content, "resultVisibility", resultVisibility);
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
        addIfSet(tmp, "courseId", courseId);
        addIfSet(tmp, "sheetId", sheetId);
        addIfSet(tmp, "maxPoints", maxPoints);
        addIfSet(tmp, "type", type);
        addIfSet(tmp, "link", link);
        addIfSet(tmp, "bonus", bonus);
        addIfSet(tmp, "linkName", linkName);
        addIfSet(tmp, "submittable", submittable);
        addIfSet(tmp, "resultVisibility", resultVisibility);
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
     * @return the courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * @return the sheetId
     */
    public String getSheetId() {
        return sheetId;
    }

    /**
     * @param sheetId the sheetId to set
     */
    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    /**
     * @return the maxPoints
     */
    public String getMaxPoints() {
        return maxPoints;
    }

    /**
     * @param maxPoints the maxPoints to set
     */
    public void setMaxPoints(String maxPoints) {
        this.maxPoints = maxPoints;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the bonus
     */
    public String getBonus() {
        return bonus;
    }

    /**
     * @param bonus the bonus to set
     */
    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    /**
     * @return the linkName
     */
    public String getLinkName() {
        return linkName;
    }

    /**
     * @param linkName the linkName to set
     */
    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    /**
     * @return the submittable
     */
    public String getSubmittable() {
        return submittable;
    }

    /**
     * @param submittable the submittable to set
     */
    public void setSubmittable(String submittable) {
        this.submittable = submittable;
    }

    /**
     * @return the resultVisibility
     */
    public String getResultVisibility() {
        return resultVisibility;
    }

    /**
     * @param resultVisibility the resultVisibility to set
     */
    public void setResultVisibility(String resultVisibility) {
        this.resultVisibility = resultVisibility;
    }

}
