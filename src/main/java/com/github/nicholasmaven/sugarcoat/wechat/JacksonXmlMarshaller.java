package com.github.nicholasmaven.sugarcoat.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.nicholasmaven.sugarcoat.wechat.exception.SerializeEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Marshall xml to pojo, and vice
 *
 * @author mawen
 * @date 2019-02-22 16:34
 */
@Slf4j
@Component
public class JacksonXmlMarshaller extends EntityMarshaller {
    public Pattern pattern = Pattern.compile("(\\w+)_(\\d+)$");

    private XmlMapperWrapper mapperWrapper;

    private ObjectWriter xmlWriter;

    @Autowired
    public JacksonXmlMarshaller(XmlMapperWrapper mapperWrapper, ObjectWriter xmlWriter) {
        this.mapperWrapper = mapperWrapper;
        this.xmlWriter = xmlWriter;
    }

    @Override
    public String getName() {
        return "JacksonXml";
    }

    @Override
    public ObjectMapper getMapper() {
        return mapperWrapper.getMapper();
    }

    @Override
    public byte[] serialize(Object obj, SerializeFeature feature) {
        Assert.notNull(obj, "obj is null");
        Assert.notNull(feature, "serialize feature is null");
        if (SerializeFeature.NONE != feature) {
            throw new UnsupportedOperationException("Not implemented! coz not required");
        }
        String result;
        try {
            if (obj instanceof Map) {
                JsonNode node = getMapper().convertValue(obj, JsonNode.class);
                result = xmlWriter.writeValueAsString(node);
            } else {
                result = getMapper().writeValueAsString(obj);
            }
        } catch (JsonProcessingException e) {
            throw new SerializeEntityException("serialize error", e);
        }
        return result.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T unmarshal(JsonNode node, TypeWrapper<T> type, DeserializeFeature feature) {
        Assert.notNull(node, "json node is null");
        Assert.notNull(type, "type is null");
        Assert.notNull(feature, "deserialize feature is null");

        if (feature instanceof DeserializeFeature.ListCanBeFoldedFeature && node instanceof ObjectNode) {
            DeserializeFeature.ListCanBeFoldedFeature listFolding = (DeserializeFeature.ListCanBeFoldedFeature) feature;
            foldList((ObjectNode) node, listFolding.getArrayName());
        } else if (DeserializeFeature.FIRST_PLAIN_ISOLATED == feature) {
            throw new UnsupportedOperationException("Not implemented, coz currently not required.");
        }
        return parseTypeWrapper(node, type);
    }

    private ObjectNode foldList(ObjectNode node, String arrayName) {
        Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        LinkedHashMap<Integer, ObjectNode> map = new LinkedHashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            String name = entry.getKey();
            Matcher matcher = pattern.matcher(name);

            Integer index;
            if (matcher.matches()) {
                String newName = matcher.group(1);
                index = Integer.valueOf(matcher.group(2));
                ObjectNode newNode;
                if (!map.containsKey(index)) {
                    newNode = new ObjectNode(getMapper().getNodeFactory());
                    newNode.set(newName, entry.getValue());
                    map.put(index, new ObjectNode(getMapper().getNodeFactory()));
                } else {
                    map.get(index).set(newName, entry.getValue());
                }
                iterator.remove();
            }
        }
        if (map.size() > 0) {
            ArrayNode array = new ArrayNode(getMapper().getNodeFactory());
            array.addAll(map.values());
            node.set(arrayName, array);
        }
        return node;
    }
}
