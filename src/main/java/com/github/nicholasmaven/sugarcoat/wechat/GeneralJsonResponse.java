package com.github.nicholasmaven.sugarcoat.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Error response at JSON interface level, Not suitable for all wechat response
 *
 * @author mawen
 * @date 2019-02-26 15:45
 */
@Setter
@Getter
public class GeneralJsonResponse {
    @JsonProperty("errcode")
    private Integer errCode;
    @JsonProperty("errmsg")
    private String errMsg;
}
