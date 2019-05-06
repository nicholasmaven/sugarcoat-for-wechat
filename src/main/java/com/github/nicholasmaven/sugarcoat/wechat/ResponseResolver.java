package com.github.nicholasmaven.sugarcoat.wechat;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseExtractor;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * For performance consideration, this class hold a cache of all instances
 * </p>
 *
 * @author mawen
 * @date 2019-03-05 19:14
 */
@Slf4j
public class ResponseResolver {
    private static ConcurrentHashMap<String, ResponseResolver> map = new ConcurrentHashMap<>();
    private EntityMarshaller marshaller;
    private DeserializeFeature feature;
    private ErrorShortCircuit errorHandler;

    private ResponseResolver(EntityMarshaller marshaller,
                             DeserializeFeature feature,
                             ErrorShortCircuit errorHandler) {
        Assert.notNull(marshaller, "marshaller is null");
        Assert.notNull(feature, "deserialize feature is null");
        Assert.notNull(errorHandler, "errorHandler feature is null");
        this.marshaller = marshaller;
        this.feature = feature;
        this.errorHandler = errorHandler;
    }

    public static ResponseResolver of(EntityMarshaller marshaller,
                                      DeserializeFeature feature,
                                      ErrorShortCircuit errorHandler) {
        Assert.notNull(marshaller, "marshaller is null");
        Assert.notNull(feature, "deserialize feature is null");
        Assert.notNull(errorHandler, "errorHandler is null");
        String key = marshaller.getName() + feature.toString() + errorHandler.hashCode();
        ResponseResolver resolver = map.get(key);
        if (resolver != null) {
            return resolver;
        }
        resolver = new ResponseResolver(marshaller, feature, errorHandler);
        return map.putIfAbsent(key, resolver) == null ? resolver : map.get(key);
    }

    public ResponseExtractor<ResponseEntity<String>> stringExtractor() {
        return (response) -> {
            if (log.isDebugEnabled()) {
                log.debug("Http Headers: ");
                response.getHeaders().forEach((key, values) -> {
                    log.debug("key->value: {}:{}", key, String.join(",", values));
                });
            }
            String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
            return new ResponseEntity<>(body, response.getHeaders(), response.getStatusCode());
        };
    }

    public <T> T resolve(ResponseEntity<String> response, TypeWrapper<T> type) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notNull(response, "response is null or empty");
        Assert.notNull(type, "type wrapper is null");
        throwsExceptionIfHttpStatusNotOk(response);
        String data = response.getBody();
        Assert.hasText(data, "response body is null or empty");
        return resolveEntity(data, type);
    }

    private void throwsExceptionIfHttpStatusNotOk(ResponseEntity<?> result) throws
            HttpStatusNotOkException {
        if (HttpStatus.OK.value() != result.getStatusCode().value()) {
            Object body = result.getBody();
            throw new HttpStatusNotOkException(result.getStatusCodeValue(),
                    body == null ? null : body.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T resolveEntity(String data, TypeWrapper<T> type) throws
            ThirdPartyBusinessException {
        JsonNodeAndProperties holder = marshaller.unmarshal(data);
        // If response error detected and no exception thrown, also result type is String, then
        // use raw string if response
        if (errorHandler.detect(holder.getPropertyMap()) && String.class == type.getClazz()) {
            return (T) holder.getRaw();
        }
        if (JsonNodeAndProperties.class == type.getClazz()) {
            // It's a middle entity, we postpone the error-handling and further unmarshalling
            return (T) holder;
        } else {
            return marshaller.unmarshal(holder.getNode(), type, feature);
        }
    }

    public DeserializeFeature getFeature() {
        return feature;
    }
}
