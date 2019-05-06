package com.github.nicholasmaven.sugarcoat.wechat;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RequestCallback;

import java.util.Collections;

/**
 * @author mawen
 * @date 2019-03-05 10:54
 */
public enum GetRequestAssembler implements RequestAssembler {
    JSON {
        @Override
        public RequestCallback assemble(Object obj) {
            return jsonCallback;
        }
    },
    XML {
        @Override
        public RequestCallback assemble(Object obj) {
            return xmlCallback;
        }
    };

    private static final RequestCallback jsonCallback =
            (request) -> request.getHeaders().put(HttpHeaders.ACCEPT,
                    Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
    private static final RequestCallback xmlCallback =
            (request) -> request.getHeaders().put(HttpHeaders.ACCEPT,
                    Collections.singletonList(MediaType.APPLICATION_XML_VALUE));
}
