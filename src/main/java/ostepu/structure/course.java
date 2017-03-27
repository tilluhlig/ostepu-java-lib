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
 * die Course-Struktur von OSTEPU
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
 */
public class course extends structure {

    /*
     * die ID der Veranstaltung
     */
    private String id = null;

    /*
     * der Name der Veranstaltung (sowas wie "Bildverarbeitung" oder
     * "Rechnernetze 16/2017"
     */
    private String name = null;

    /*
     * das Semester als Text ("SS 2017", "WS 2016/2017")
     */
    private String semester = null;

    /*
     * die Standardgruppengröße der Übungsserien (dient nur als Vorgabe beim
     * Erstellen von Übungsserien)
     */
    private String defaultGroupSize = null;

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
        return new course(newObj);
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content als JSON Objekt (Eingabe)
     * @return das Objekt
     */
    public static Object decode(JsonObject content) {
        return new course(content);
    }

    /**
     * der Standardkonstruktor
     */
    public course() {
        // kein Inhalt
    }

    /**
     * initialisiert das Objekt anhand eines JSON-Objekts
     *
     * @param content der zukünftige Inhalt
     */
    public course(JsonObject content) {
        id = handleStringEntry(content, "id", id);
        name = handleStringEntry(content, "name", name);
        semester = handleStringEntry(content, "semester", semester);
        defaultGroupSize = handleStringEntry(content, "defaultGroupSize", defaultGroupSize);
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
        addIfSet(tmp, "name", name);
        addIfSet(tmp, "semester", semester);
        addIfSet(tmp, "defaultGroupSize", defaultGroupSize);
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
