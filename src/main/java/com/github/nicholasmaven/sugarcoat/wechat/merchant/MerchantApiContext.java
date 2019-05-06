package com.github.nicholasmaven.sugarcoat.wechat.merchant;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.TypeWrapper;
import org.springframework.http.HttpMethod;

/**
 * @author mawen
 * @date 2019-02-27 9:55
 */
public class MerchantApiContext<T> extends ApiContext<T> {
    private static final String BASE_URL = "https://api.mch.weixin.qq.com";

    public MerchantApiContext(String interfaceUri, HttpMethod method, Class<T> resultClazz) {
        super(BASE_URL, interfaceUri, method, resultClazz);
    }

    public MerchantApiContext(String interfaceUri, HttpMethod method, TypeReference<T> type) {
        super(BASE_URL, interfaceUri, method, type);
    }

    public MerchantApiContext(String interfaceUri, HttpMethod method, TypeWrapper<T> type) {
        super(BASE_URL, interfaceUri, method, type);
    }

    @Override
    public ApiContext<T> copyOf(String interfaceUri) {
        return new MerchantApiContext<>(interfaceUri, this.getMethod(), this.getResultType());
    }
}
