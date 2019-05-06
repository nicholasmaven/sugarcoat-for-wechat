package com.github.nicholasmaven.sugarcoat.wechat;

import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;

import java.util.Map;

/**
 * Detect wechat returns' business error
 *
 * @author mawen
 * @date 2019-02-18 16:17
 */
@FunctionalInterface
public interface ErrorShortCircuit {
    boolean detect(Map<String, String> map) throws ThirdPartyBusinessException;
}
