package com.github.nicholasmaven.sugarcoat.net;

import com.github.nicholasmaven.sugarcoat.wechat.merchant.MerchantInfo;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * @author mawen
 * @create 2019-03-21 15:44
 */
@Component
@EnableConfigurationProperties({HttpClientConfig.class, MerchantInfo.class})
@Configuration
public class RestTemplateFactory {

    @Bean("defaultRestTemplate")
    public RestTemplate defaultRestTemplate(HttpClientConfig config,
                                            List<ClientHttpRequestInterceptor> interceptors) throws
            IOException, GeneralSecurityException {
        return createRestTemplate(config, null, interceptors);
    }

    @Bean("merchantRestTemplate")
    public RestTemplate merchantRestTemplate(HttpClientConfig config,
                                             MerchantInfo merchantInfo,
                                             List<ClientHttpRequestInterceptor> interceptors) throws
            IOException, GeneralSecurityException {
        return createRestTemplate(config, merchantInfo, interceptors);
    }

    private RestTemplate createRestTemplate(HttpClientConfig config,
                                            MerchantInfo merchantInfo,
                                            List<ClientHttpRequestInterceptor> interceptors) throws
            IOException, GeneralSecurityException {
        ClientHttpRequestFactory clientFactory = HttpClientFactory.httpClientFactory(config,
                merchantInfo);
        RestTemplate restTemplate = new RestTemplate(clientFactory);
        if (!CollectionUtils.isEmpty(interceptors)) {
            restTemplate.setInterceptors(interceptors);
        }
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.forEach(convert -> {
            if (convert.getClass() == StringHttpMessageConverter.class) {
                StringHttpMessageConverter stringHttpMessageConverter =
                        (StringHttpMessageConverter) convert;
                stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
            }
        });
        return restTemplate;
    }
}
