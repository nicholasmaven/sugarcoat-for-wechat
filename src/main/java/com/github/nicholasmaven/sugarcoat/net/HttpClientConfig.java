package com.github.nicholasmaven.sugarcoat.net;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HTTP Client initializr
 *
 * @author mawen
 * @date 2018-12-18 19:18
 */

@ConditionalOnClass(CloseableHttpClient.class)
@ConfigurationProperties(prefix = "httpclient")
@Getter
@Setter
public class HttpClientConfig {
    private Integer connTimeoutMillSeconds;
    private Integer socketTimeoutMillSeconds;
    private Integer connRequestTimeoutMillSeconds;
    private Integer maxTotal;
    private Integer maxPerRoute;
    private Integer minsToLive;
    private String localIp;

}
