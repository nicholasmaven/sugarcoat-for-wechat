package com.github.nicholasmaven.sugarcoat.wechat.merchant;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

/**
 * @author mawen
 * @date 2019-03-07 11:21
 */
@Slf4j
@ConfigurationProperties(prefix = "merchant")
@Setter
@Getter
public class MerchantInfo {
    private String appId;
    private String mchId;
    private String mchName;
    private String key;
    private KeyStore keyStore;
    private String cert;

    @PostConstruct
    public void initKeyStore() {
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(cert)) {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(stream, mchId.toCharArray());
        } catch (IOException | GeneralSecurityException e) {
            log.error("failed to load cert file", e);
        }
    }
}
