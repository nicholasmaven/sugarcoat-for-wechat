package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-18 10:22
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_3.html">sendToOne status request</a>
 */

@Getter
public class SendConnectStatusRequest {
    @Setter
    @JsonProperty("device_type")
    private String deviceType;

    @Setter
    @JsonProperty("device_id")
    private String deviceId;

    @Setter
    @JsonProperty("open_id")
    private String openId;

    @JsonProperty("msg_type")
    private String msgType = "2";

    @Setter
    @JsonProperty("device_status")
    private String deviceStatus;
}
