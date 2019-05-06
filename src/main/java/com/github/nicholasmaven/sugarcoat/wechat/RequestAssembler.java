package com.github.nicholasmaven.sugarcoat.wechat;

import org.springframework.web.client.RequestCallback;

@FunctionalInterface
public interface RequestAssembler {
    RequestCallback assemble(Object obj);
}
