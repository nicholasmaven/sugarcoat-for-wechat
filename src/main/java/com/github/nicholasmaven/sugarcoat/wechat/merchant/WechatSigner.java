package com.github.nicholasmaven.sugarcoat.wechat.merchant;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.Map;

/**
 * @author mawen
 * @date 2019-03-07 10:53
 */
@Component
public class WechatSigner {
    public String sign(Map<String, String> map, MerchantInfo merchantInfo) {
        return sign(map, merchantInfo, false);
    }
    public String sign(Map<String, String> map, MerchantInfo merchantInfo, boolean useDefaultCustomizedMerchantKey) {
        Assert.notEmpty(map, "map is null");
        Assert.notNull(merchantInfo, "merchantInfo is null");
        if (useDefaultCustomizedMerchantKey) {
            map.put("appid", merchantInfo.getAppId());
            map.put("mch_id", merchantInfo.getMchId());
        }
        map.put("nonce_str", RandomStringUtils.randomAlphanumeric(16));
        StringBuilder buf = map.entrySet().stream()
                .filter(e -> !StringUtils.isEmpty(e.getValue()))
                .filter(e -> !e.getKey().equals("sign"))
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(StringBuilder::new,
                        (buffer, e) -> buffer.append(e.getKey())
                                .append("=")
                                .append(e.getValue())
                                .append("&"), StringBuilder::append);
        buf.append("key=").append(merchantInfo.getKey());
        return DigestUtils.md5Hex(buf.toString()).toUpperCase();
    }

    public boolean validate(Map<String, String> map,
                            MerchantInfo merchantInfo,
                            String signature) {
        Assert.notEmpty(map, "stream is null");
        Assert.notNull(merchantInfo, "merchantInfo is null");
        Assert.hasText(signature, "signature is null or empty");
        return signature.equals(sign(map, merchantInfo));
    }
}
