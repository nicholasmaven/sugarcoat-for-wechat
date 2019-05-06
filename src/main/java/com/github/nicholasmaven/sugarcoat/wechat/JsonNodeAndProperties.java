package com.github.nicholasmaven.sugarcoat.wechat;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Result holder for json string and json node
 *
 * @author mawen
 * @date 2019-02-22 19:12
 */
public class JsonNodeAndProperties {
    private final String raw;
    private final JsonNode node;
    private final Map<String, String> map;

    public JsonNodeAndProperties(String raw, JsonNode node) {
        this.raw = raw;
        this.node = node;
        this.map = new HashMap<>();
    }

    public void withNode(String name, JsonNode node) {
        // avoid redundant double quotes around text value
        map.put(name, node.isTextual() ? node.textValue() : node.toString());
    }

    public Map<String, String> getPropertyMap() {
        return map;
    }

    public JsonNode getNode() {
        return node;
    }

    public String getRaw() {
        return raw;
    }
}
