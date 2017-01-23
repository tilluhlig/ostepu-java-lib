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
public class link extends structure {

    private String id = null;
    private String name = null;
    private String address = null;
    private String target = null;
    private String prefix = null;
    private String priority = null;
    private String owner = null;
    private String relevanz = null;
    private String targetName = null;

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
        return new link(newObj);
    }

    /**
     *
     * @param content
     * @return
     */
    public static Object decode(JsonObject content) {
        return new link(content);
    }

    /**
     *
     */
    public link() {

    }

    /**
     *
     * @param content
     */
    public link(JsonObject content) {
        id = handleStringEntry(content, "id", id);
        name = handleStringEntry(content, "name", name);
        address = handleStringEntry(content, "address", address);
        target = handleStringEntry(content, "target", target);
        prefix = handleStringEntry(content, "prefix", prefix);
        priority = handleStringEntry(content, "priority", priority);
        owner = handleStringEntry(content, "owner", owner);
        relevanz = handleStringEntry(content, "relevanz", relevanz);
        targetName = handleStringEntry(content, "targetName", targetName);
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
        addIfSet(tmp, "target", target);
        addIfSet(tmp, "prefix", prefix);
        addIfSet(tmp, "priority", priority);
        addIfSet(tmp, "owner", owner);
        addIfSet(tmp, "relevanz", relevanz);
        addIfSet(tmp, "targetName", targetName);
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
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the relevanz
     */
    public String getRelevanz() {
        return relevanz;
    }

    /**
     * @param relevanz the relevanz to set
     */
    public void setRelevanz(String relevanz) {
        this.relevanz = relevanz;
    }

    /**
     * @return the targetName
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * @param targetName the targetName to set
     */
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

}
