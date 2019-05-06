package com.github.nicholasmaven.sugarcoat.wechat.mp.apiticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * description: 公众号用于调用微信JS接口的临时票据
 *
 * @author zhangjw
 * create at 2019-03-27 16:58
 * @since 1.0.0
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ApiTicket {
    @JsonProperty("ticket")
    private String ticket;

    @JsonProperty("expires_in")
    private Integer expiresIn;
}
