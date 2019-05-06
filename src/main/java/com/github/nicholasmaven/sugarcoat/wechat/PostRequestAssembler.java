package com.github.nicholasmaven.sugarcoat.wechat;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * <p>
 * For performance consideration, this class hold a cache of all instances
 * </p>
 *
 * @author mawen
 * @date 2019-03-05 10:18
 */
public class PostRequestAssembler implements RequestAssembler {
    private static ConcurrentHashMap<String, PostRequestAssembler> map =
            new ConcurrentHashMap<>();

    private EntityMarshaller marshaller;
    private SerializeFeature feature;

    private PostRequestAssembler(EntityMarshaller marshaller, SerializeFeature feature) {
        Assert.notNull(marshaller, "marshaller is null");
        Assert.notNull(feature, "serialize feature is null");
        this.marshaller = marshaller;
        this.feature = feature;
    }

    public static PostRequestAssembler of(EntityMarshaller marshaller,
                                          SerializeFeature feature) {
        Assert.notNull(marshaller, "jackson marshaller is null");
        Assert.notNull(feature, "serialize feature is null");
        String key = marshaller.getName() + feature.toString();
        PostRequestAssembler assembler = map.get(key);
        if (assembler != null) {
            return assembler;
        }
        assembler = new PostRequestAssembler(marshaller, feature);
        return map.putIfAbsent(key, assembler) == null ? assembler : map.get(key);
    }

    @Override
    public RequestCallback assemble(Object obj) {
        Assert.notNull(obj, "obj is null");
        Assert.isTrue(!(obj instanceof Stream), "Stream is not supported");
        byte[] data = marshaller.serialize(obj, feature);
        return (request) -> {
            request.getHeaders().put(HttpHeaders.CONTENT_LENGTH,
                    Collections.singletonList(data.length + ""));
            if (marshaller instanceof JacksonJsonMarshaller) {
                addJsonHeaders(request.getHeaders());
            } else {
                addXmlHeaders(request.getHeaders());
            }
            request.getBody().write(data);
        };
    }

    private void addJsonHeaders(HttpHeaders headers) {
        headers.put(HttpHeaders.CONTENT_TYPE,
                Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
        headers.put(HttpHeaders.ACCEPT,
                Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
    }

    private void addXmlHeaders(HttpHeaders headers) {
        headers.put(HttpHeaders.CONTENT_TYPE,
                Collections.singletonList(MediaType.APPLICATION_XML_VALUE));
        headers.put(HttpHeaders.ACCEPT,
                Collections.singletonList(MediaType.APPLICATION_XML_VALUE));
    }
}
