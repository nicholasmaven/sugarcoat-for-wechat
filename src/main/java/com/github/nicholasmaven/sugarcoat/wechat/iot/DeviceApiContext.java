package com.github.nicholasmaven.sugarcoat.wechat.iot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.TypeWrapper;
import org.springframework.http.HttpMethod;

/**
 * Cgi api url starts with https://api.weixin.qq.com/cgi-bin
 *
 * @author mawen
 * @date 2019-02-27 9:55
 */
public class DeviceApiContext<T> extends ApiContext<T> {
    private static final String BASE_URL = "https://api.weixin.qq.com/device";

    public DeviceApiContext(String interfaceUri, HttpMethod method, Class<T> resultClazz) {
        super(BASE_URL, interfaceUri, method, resultClazz);
    }

    public DeviceApiContext(String interfaceUri, HttpMethod method, TypeReference<T> resultType) {
        super(BASE_URL, interfaceUri, method, resultType);
    }

    public DeviceApiContext(String interfaceUri, HttpMethod method, TypeWrapper<T> resultType) {
        super(BASE_URL, interfaceUri, method, resultType);
    }

    @Override
    public DeviceApiContext<T> copyOf(String interfaceUri) {
        return new DeviceApiContext<>(interfaceUri, this.getMethod(), this.getResultType());
    }
}
