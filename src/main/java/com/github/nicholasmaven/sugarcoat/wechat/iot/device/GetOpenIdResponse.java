package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mawen
 * @date 2019-03-04 14:28
 */
@Setter
@Getter
public class GetOpenIdResponse {
    @JsonProperty("open_id")
    private List<String> openIds;
    @JsonProperty("resp_msg")
    private RespMsg msg;

    @Setter
    @Getter
    public static class RespMsg {
        @JsonProperty("ret_code")
        private Integer errCode;
        // It's weird that the field's name is 'error_info', ideally should be 'ret_code'
        @JsonProperty("error_info")
        private String errMsg;
    }
}
