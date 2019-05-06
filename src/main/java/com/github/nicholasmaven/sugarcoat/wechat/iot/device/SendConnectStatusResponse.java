package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-18 10:22
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_3.html">sendToOne status response</a>
 */
@Setter
@Getter
public class SendConnectStatusResponse {
    private Integer ret;
    @JsonProperty("ret_info")
    private String retInfo;
}
