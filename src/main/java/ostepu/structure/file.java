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
 * die File-Struktur von OSTEPU
 *
 * @author Till Uhlig {@literal <till.uhlig@student.uni-halle.de>}
 */
public class file extends structure {

    /*
     * die ID der Datei
     */
    private String fileId = null;

    /*
     * der Anzeigename (Bsp.: meineEinsendung.pdf)
     */
    private String displayName = null;

    /*
     * die Adresse der Datei im Dateisystem (ist nur eine relative
     * Abfrageadresse) sowas wie: /file/a/b/c/ababab
     */
    private String address = null;

    /*
     * der Unix-Zeitstempel der Erstellung oder des Entgegennehmens
     */
    private String timeStamp = null;

    /*
     * die Dateigröße in Byte
     */
    private String fileSize = null;

    /*
     * der Hash sha1-Hash des Dateiinhalts
     */
    private String hash = null;

    /*
     * ein String, wenn es ein Textinhalt ist oder eine Referenz
     */
    private Object body = null;

    /*
     * ein Dateikommentar (wird nicht genutzt)
     */
    private String comment = null;

    /*
     * der ermittelte mime-Type
     */
    private String mimeType = null;

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
        return new file(newObj);
    }

    /**
     * wandelt eine Textdarstellung in ein Objekt um
     *
     * @param content als JSON Objekt (Eingabe)
     * @return das Objekt
     */
    public static Object decode(JsonObject content) {
        return new file(content);
    }

    /**
     * der Standardkonstruktor
     */
    public file() {
        // kein Inhalt
    }

    /**
     * initialisiert das Objekt anhand eines JSON-Objekts
     *
     * @param content der zukünftige Inhalt
     */
    public file(JsonObject content) {
        fileId = handleStringEntry(content, "fileId", fileId);
        displayName = handleStringEntry(content, "displayName", displayName);
        address = handleStringEntry(content, "address", address);
        timeStamp = handleStringEntry(content, "timeStamp", timeStamp);
        fileSize = handleStringEntry(content, "fileSize", fileSize);
        hash = handleStringEntry(content, "hash", hash);
        if (content.has("body")) {
            if (content.isJsonPrimitive()) {
                body = (Object) content.get("body").getAsString();
            } else {
                JsonObject rawReference = content.get("body").getAsJsonObject();
                body = (Object) new reference(rawReference);
            }
        }
        comment = handleStringEntry(content, "comment", comment);
        mimeType = handleStringEntry(content, "mimeType", mimeType);

    }

    /**
     * wandelt das Objekt in eine Textdarstellung um (JSON)
     *
     * @return die Textdarstellung (als JSON)
     */
    @Override
    public String encode() {
        JsonObject tmp = new JsonObject();
        addIfSet(tmp, "fileId", fileId);
        addIfSet(tmp, "displayName", displayName);
        addIfSet(tmp, "address", address);
        addIfSet(tmp, "timeStamp", timeStamp);
        addIfSet(tmp, "fileSize", fileSize);
        addIfSet(tmp, "hash", hash);
        addIfSet(tmp, "body", body);
        addIfSet(tmp, "comment", comment);
        addIfSet(tmp, "mimeType", mimeType);
        tmp = super.encodeToObject(tmp);
        return tmp.toString();
    }

    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
     * @return the timeStamp
     */
    public String getTimestamp() {
        return timeStamp;
    }

    /**
     * @param timestamp the timeStamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timeStamp = timestamp;
    }

    /**
     * @return the fileSize
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return the body
     */
    public Object getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(Object body) {
        this.body = body;
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
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

}
