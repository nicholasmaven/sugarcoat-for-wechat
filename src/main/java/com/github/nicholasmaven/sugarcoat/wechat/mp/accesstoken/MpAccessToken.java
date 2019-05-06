package com.github.nicholasmaven.sugarcoat.wechat.mp.accesstoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author mawen
 * @date 2019-01-31 16:56
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013">mp accesstoken</a>
 */
@Data
public class MpAccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private String expiresIn;
}
