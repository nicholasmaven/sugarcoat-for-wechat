package com.github.nicholasmaven.sugarcoat.wechat.mp.webauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-18 10:59
 * @see <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140837">web access token</a>
 */
@Setter
@Getter
public class WebAccessToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("openid")
    private String openId;
    private String scope;
}
