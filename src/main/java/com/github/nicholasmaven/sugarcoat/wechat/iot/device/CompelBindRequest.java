package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-18 10:36
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_12.html">compel bind request</a>
 */
@Setter
@Getter
public class CompelBindRequest {
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("openid")
    private String openId;
}
