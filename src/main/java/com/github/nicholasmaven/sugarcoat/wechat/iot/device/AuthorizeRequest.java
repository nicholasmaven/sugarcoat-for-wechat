package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author mawen
 * @date 2019-02-15 19:36
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_6.html">authorize request</a>
 */
@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class AuthorizeRequest {
    @JsonProperty("device_num")
    private String num;

    @JsonProperty("device_list")
    private List<Device> devices;

    @JsonProperty("op_type")
    private String opType;

    @JsonProperty("product_id")
    private String productId;

    @Setter
    @Getter
    @JsonInclude(Include.NON_NULL)
    public static class Device {
        @JsonProperty("manu_mac_pos")
        String manuMacPos;
        @JsonProperty("ser_mac_pos")
        String serMacPos;
        @JsonProperty("ble_simple_protocol")
        String bleSimpleProtocol;
        private String id;
        private String mac;
        @JsonProperty("connect_protocol")
        private String connectProtocol;
        @JsonProperty("auth_key")
        private String authKey;
        @JsonProperty("close_strategy")
        private String closeStrategy;
        @JsonProperty("conn_strategy")
        private String connStrategy;
        @JsonProperty("crypt_method")
        private String cryptMethod;
        @JsonProperty("auth_ver")
        private String authVer;
    }
}
