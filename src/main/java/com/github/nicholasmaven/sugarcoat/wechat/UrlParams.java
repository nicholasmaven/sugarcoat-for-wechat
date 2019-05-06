package com.github.nicholasmaven.sugarcoat.wechat;

import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Url params that contains key-value pairs
 *
 * @author mawen
 * @date 2019-02-15 13:49
 */
public class UrlParams {
    private final LinkedHashMap<String, String> map = new LinkedHashMap<>();

    public UrlParams with(String name, String value) {
        Assert.hasText(name, "name is null or empty");
        Assert.hasText(value, "value is null or empty");
        map.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        if (map.isEmpty()) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            buffer.append("&");
            buffer.append(entry.getKey());
            buffer.append("=");
            buffer.append(entry.getValue());
        }
        return buffer.delete(0, 1).toString();
    }
}
