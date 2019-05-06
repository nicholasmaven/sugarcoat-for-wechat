package com.github.nicholasmaven.sugarcoat.wechat.mp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.TypeWrapper;
import org.springframework.http.HttpMethod;

/**
 * @author mawen
 * @date 2019-02-27 9:55
 */
public class SnsApiContext<T> extends ApiContext<T> {
    private static final String BASE_URL = "https://api.weixin.qq.com/sns";

    public SnsApiContext(String interfaceUri, HttpMethod method, Class<T> resultClazz) {
        super(BASE_URL, interfaceUri, method, resultClazz);
    }

    public SnsApiContext(String interfaceUri, HttpMethod method, TypeReference<T> resultType) {
        super(BASE_URL, interfaceUri, method, resultType);
    }

    public SnsApiContext(String interfaceUri, HttpMethod method, TypeWrapper<T> resultType) {
        super(BASE_URL, interfaceUri, method, resultType);
    }

    @Override
    public ApiContext<T> copyOf(String interfaceUri) {
        return new SnsApiContext<>(interfaceUri, this.getMethod(), this.getResultType());
    }
}
