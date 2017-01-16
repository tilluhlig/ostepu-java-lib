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
public class file extends structure {

    private String fileId = null;
    private String displayName = null;
    private String address = null;
    private String timestamp = null;
    private String fileSize = null;
    private String hash = null;
    private String body = null;
    private String comment = null;
    private String mimeType = null;
    
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
        return new file(newObj);
    }

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(JsonObject content) {
        return new file(content);
    }

    /**
     *
     */
    public file() {

    }

    /**
     *
     * @param content
     */
    public file(JsonObject content) {
        fileId = handleStringEntry(content, "fileId", fileId);
        displayName = handleStringEntry(content, "displayName", displayName);
        address = handleStringEntry(content, "address", address);
        timestamp = handleStringEntry(content, "timestamp", timestamp);
        fileSize = handleStringEntry(content, "fileSize", fileSize);
        hash = handleStringEntry(content, "hash", hash);
        body = handleStringEntry(content, "body", body);
        comment = handleStringEntry(content, "comment", comment);
        mimeType = handleStringEntry(content, "mimeType", mimeType);

    }

    /**
     *
     * @return
     */
    @Override
    public String encode() {
        JsonObject tmp = new JsonObject();
        addIfSet(tmp, "fileId", fileId);
        addIfSet(tmp, "displayName", displayName);
        addIfSet(tmp, "address", address);
        addIfSet(tmp, "timestamp", timestamp);
        addIfSet(tmp, "fileSize", fileSize);
        addIfSet(tmp, "hash", hash);
        addIfSet(tmp, "body", body);
        addIfSet(tmp, "comment", comment);
        addIfSet(tmp, "mimeType", mimeType);
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
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
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
