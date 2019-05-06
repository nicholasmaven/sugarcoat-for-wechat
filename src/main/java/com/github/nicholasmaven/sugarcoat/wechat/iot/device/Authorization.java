package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-15 19:36
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_6.html">authorize response</a>
 */

@Setter
@Getter
public class Authorization {
    @JsonProperty("errcode")
    private String errCode;
    @JsonProperty("errmsg")
    private String errMsg;
    @JsonProperty("base_info")
    private DeviceType type;

    @Setter
    @Getter
    public static class DeviceType {
        // mp's original id
        @JsonProperty("device_type")
        private String deviceType;

        @JsonProperty("device_id")
        private String deviceId;
    }
}
