package com.github.nicholasmaven.sugarcoat.wechat.mp.qrcode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-15 16:50
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542">qrcode</a>
 */
@Setter
@Getter
public class QrCodeTicket {
    private String ticket;
    @JsonProperty("expire_seconds")
    private int expireSeconds;
    private String url;
}
