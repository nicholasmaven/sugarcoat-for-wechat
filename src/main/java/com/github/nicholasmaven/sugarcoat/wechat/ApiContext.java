package com.github.nicholasmaven.sugarcoat.wechat;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

/**
 * Contains fixed part of an api
 *
 * @author mawen
 * @date 2019-02-26 13:41
 */

public abstract class ApiContext<T> {
    @Getter
    private final HttpMethod method;
    private final String interfaceUri;
    @Getter
    private final TypeWrapper<T> resultType;
    private final String baseUrl;

    public ApiContext(String baseUrl,
                      String interfaceUri,
                      HttpMethod method,
                      Class<T> resultClazz) {
        Assert.hasText(interfaceUri, "interfaceUri is null or empty");
        Assert.notNull(method, "http method is null");
        Assert.notNull(resultClazz, "resultType is null");
        this.baseUrl = baseUrl;
        this.interfaceUri = interfaceUri;
        this.method = method;
        this.resultType = TypeWrapper.of(resultClazz);
    }

    public ApiContext(String baseUrl,
                      String interfaceUri,
                      HttpMethod method,
                      TypeReference<T> resultType) {
        Assert.hasText(interfaceUri, "interfaceUri is null or empty");
        Assert.notNull(method, "http method is null");
        Assert.notNull(resultType, "resultType is null");
        this.baseUrl = baseUrl;
        this.interfaceUri = interfaceUri;
        this.method = method;
        this.resultType = TypeWrapper.of(resultType);
    }

    public ApiContext(String baseUrl,
                      String interfaceUri,
                      HttpMethod method,
                      TypeWrapper<T> resultType) {
        Assert.hasText(interfaceUri, "interfaceUri is null or empty");
        Assert.notNull(method, "http method is null");
        Assert.notNull(resultType, "resultType is null");
        this.baseUrl = baseUrl;
        this.interfaceUri = interfaceUri;
        this.method = method;
        this.resultType = resultType;
    }

    public String getFullUrl(UrlParams params) {
        return baseUrl + interfaceUri + "?" + params;
    }

    public String getFullUrl(String token) {
        return baseUrl + interfaceUri + "?access_token=" + token;
    }

    public String getFullUrl() {
        return baseUrl + interfaceUri;
    }

    public abstract ApiContext<T> copyOf(String interfaceUri);
}
