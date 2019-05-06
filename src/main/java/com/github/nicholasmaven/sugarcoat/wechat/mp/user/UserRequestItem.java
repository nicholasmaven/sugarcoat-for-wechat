package com.github.nicholasmaven.sugarcoat.wechat.mp.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author mawen
 * @date 2019-03-07 18:59
 * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839">get batch</a>
 */

@Data
public class UserRequestItem {
    @JsonProperty("openid")
    private final String openId;
    private final String lang;
}
