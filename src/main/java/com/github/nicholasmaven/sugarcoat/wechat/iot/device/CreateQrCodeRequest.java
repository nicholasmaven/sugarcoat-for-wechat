package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * @author mawen
 * @date 2019-02-15 19:20
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_5.html">create qr request</a>
 */

@Getter
public class CreateQrCodeRequest {
    @JsonProperty("device_num")
    private String deviceNum;

    @JsonProperty("device_id_list")
    private List<String> deviceIds;

    public CreateQrCodeRequest(int deviceNum, List<String> deviceIds) {
        this.deviceNum = String.valueOf(deviceNum);
        this.deviceIds = deviceIds;
    }
}
