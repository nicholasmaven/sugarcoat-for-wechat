package com.github.nicholasmaven.sugarcoat.wechat.mp;

import com.github.nicholasmaven.sugarcoat.wechat.UrlParams;

/**
 * Non-real http call
 *
 * @author mawen
 * @date 2019-03-11 10:57
 */
public class DummyApiContext {
    private final String interfaceUri;
    private final String baseUrl;

    public DummyApiContext(String interfaceUri, String baseUrl) {
        this.interfaceUri = interfaceUri;
        this.baseUrl = baseUrl;
    }

    public String getFullUrl(UrlParams params) {
        return baseUrl + interfaceUri + "?" + params;
    }
}
