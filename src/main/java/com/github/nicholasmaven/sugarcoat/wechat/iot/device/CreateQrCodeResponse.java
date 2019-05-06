package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mawen
 * @date 2019-02-15 19:16
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_5.html">create qr response</a>
 */
@Setter
@Getter
public class CreateQrCodeResponse {
    @JsonProperty("device_num")
    private Integer deviceNum;

    @JsonProperty("code_list")
    private List<DeviceQrCodeTicket> codes;

    @Setter
    @Getter
    public static class DeviceQrCodeTicket {
        @JsonProperty("device_id")
        private String deviceId;

        private String ticket;
    }
}
