package com.github.nicholasmaven.sugarcoat.wechat.mp.webauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mawen
 * @date 2019-02-18 11:19
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140842&t=0.4622491167403"> web user</a>
 * <p>
 * <Strong>Note</Strong>: The offical api document is not correct, missing language field
 * </p>
 */
@Setter
@Getter
public class WebUser {
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("nickname")
    private String nickName;
    private String sex;
    private String language;
    private String province;
    private String city;
    private String country;
    @JsonProperty("headimgurl")
    private String headImgUrl;
    private List<String> privilege;
    @JsonProperty("unionid")
    private String unionId;
}
