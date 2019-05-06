package com.github.nicholasmaven.sugarcoat.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ParseResultEntityException;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Marshall and unmarshal between pojo and text(xml or json)
 *
 * @author mawen
 * @date 2019-03-01 12:22
 */
public abstract class EntityMarshaller {
    public abstract String getName();

    public abstract byte[] serialize(Object obj, SerializeFeature feature);

    protected abstract ObjectMapper getMapper();

    public abstract <T> T unmarshal(JsonNode data,
                                    TypeWrapper<T> type,
                                    DeserializeFeature feature);

    public JsonNodeAndProperties unmarshal(String data) {
        try {
            JsonNode node = getMapper().readTree(data);
            JsonNodeAndProperties holder = new JsonNodeAndProperties(data, node);
            node.fields().forEachRemaining((s) -> holder.withNode(s.getKey(),
                    s.getValue()));
            return holder;
        } catch (IOException e) {
            throw new ParseResultEntityException("parse json node error", e);
        }
    }

    public Map<String, String> extractPropertyMap(Object obj) {
        Assert.notNull(obj, "obj is null");
        Map<String, String> map = new HashMap<>();
        ObjectNode node = getMapper().valueToTree(obj);
        node.fields().forEachRemaining((s) -> map.put(s.getKey(),
                s.getValue().isTextual() ? s.getValue().textValue() : s.getValue().toString()));
        return map;
    }

    protected <T> T parseTypeWrapper(JsonNode node, TypeWrapper<T> type) {
        Assert.notNull(node, "json node is null");
        Assert.notNull(type, "type wrapper is null");
        if (type.isRegular()) {
            try {
                return getMapper().treeToValue(node, type.getClazz());
            } catch (JsonProcessingException e) {
                throw new ParseResultEntityException(e);
            }
        } else {
            JsonNode dstNode = node;
            if (node.isObject()) {
                Iterator<JsonNode> iterator = node.elements();
                while(iterator.hasNext()) {
                    dstNode = iterator.next();
                    if (!dstNode.isArray()) {
                        throw new UnsupportedOperationException("Incorrect node");
                    }
                }
            }
            return getMapper().convertValue(dstNode, type.getGenericType());
        }
    }
}
