package com.github.nicholasmaven.sugarcoat.wechat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.github.nicholasmaven.sugarcoat.wechat.SerializeFeature.CompletionFeature;
import com.github.nicholasmaven.sugarcoat.wechat.SerializeFeature.ContainerizedCompletionFeature;
import com.github.nicholasmaven.sugarcoat.wechat.exception.SerializeEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Marshal and unmarshal json with property holder
 *
 * @author mawen
 * @date 2019-02-26 16:44
 */
@Slf4j
@Component
public class JacksonJsonMarshaller extends EntityMarshaller {
    private ObjectMapper mapper;

    @Autowired
    public JacksonJsonMarshaller(ObjectMapper mapper) {
        Assert.notNull(mapper, "object mapper is null");
        this.mapper = mapper;
    }

    @Override
    public ObjectMapper getMapper() {
        return mapper;
    }

    @Override
    public String getName() {
        return "JacksonJson";
    }

    @Override
    public byte[] serialize(Object obj, SerializeFeature feature) {
        Assert.notNull(obj, "obj is null");
        Assert.notNull(feature, "serialize feature is null");
        String json;
        try {
            if (SerializeFeature.NONE == feature) {
                // to avoid unicode incorrect escape
                json = getMapper().writeValueAsString(obj);
            } else if (feature instanceof CompletionFeature) {
                CompletionFeature compFeature = (CompletionFeature) feature;
                JsonNode node;
                if (feature instanceof ContainerizedCompletionFeature) {
                    node = getMapper().convertValue(obj, ContainerNode.class);
                } else {
                    node = getMapper().convertValue(obj, ValueNode.class);
                }
                ObjectNode root;
                ObjectNode propNode = new ObjectNode(getMapper().getNodeFactory(),
                        Collections.singletonMap(compFeature.getPropertyName(), node));
                if (StringUtils.isEmpty(compFeature.getBaseName())) {
                    root = propNode;
                } else {
                    root = new ObjectNode(getMapper().getNodeFactory(),
                            Collections.singletonMap(compFeature.getBaseName(), propNode));
                }
                json = getMapper().writeValueAsString(root);
            } else {
                throw new IllegalArgumentException("Invalid serialize feature!" + feature.toString());
            }
            return json.getBytes(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new SerializeEntityException("serialize error", e);
        }
    }

    @Override
    public <T> T unmarshal(JsonNode node, TypeWrapper<T> type, DeserializeFeature feature) {
        Assert.notNull(node, "json node is null");
        Assert.notNull(type, "type wrapper is null");
        Assert.notNull(feature, "deserialize feature is null");
        JsonNode newNode = node;
        if (DeserializeFeature.FIRST_PLAIN_ISOLATED == feature) {
            newNode = peelCurlyBraces(node);
        } else if (feature instanceof DeserializeFeature.ListCanBeFoldedFeature) {
            throw new UnsupportedOperationException("Not implemented, coz currently not " +
                    "required.");
        }
        return parseTypeWrapper(newNode, type);
    }

    private JsonNode peelCurlyBraces(JsonNode node) {
        Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        Map.Entry<String, JsonNode> soleNode = iterator.next();
        if (soleNode == null || soleNode.getValue() == null || soleNode.getValue().isMissingNode()) {
            throw new IllegalArgumentException("Invalid node, json body is empty!");
        }
        if (iterator.hasNext()) {
            throw new IllegalArgumentException("Invalid node, json hierarchy is not isolated");
        }
        return soleNode.getValue();
    }
}
