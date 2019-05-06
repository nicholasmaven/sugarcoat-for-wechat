package com.github.nicholasmaven.sugarcoat.net;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author mawen
 * @date 2019/1/25
 **/
@Slf4j
@Component
public class RestLogInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        String data = body.length > 0 ?
                new String(body, StandardCharsets.UTF_8.displayName()) : null;
        log.debug(">>> request {} uri={}, body={}", request.getMethod(), request.getURI(), data);
        ClientHttpResponse response = execution.execute(request, body);
        data = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        log.debug("<<< response {} uri={}, body={}", request.getMethod(), request.getURI(), data);
        return response;
    }

}
