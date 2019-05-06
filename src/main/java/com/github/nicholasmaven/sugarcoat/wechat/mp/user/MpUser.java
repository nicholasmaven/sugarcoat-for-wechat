package com.github.nicholasmaven.sugarcoat.wechat.mp.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mawen
 * @date 2019-02-15 17:11
 * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839">mp user</a>
 */
@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class MpUser {
    private int subscribe;
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("nickname")
    private String nickName;
    private int sex;
    private String city;
    private String country;
    private String province;
    private String language;
    @JsonProperty("headimgurl")
    private String headImgUrl;

    @JsonProperty(value = "subscribe_time")
    private long subscribeTime;

    private String unionid;

    private String remark;

    private int groupid;

    @JsonProperty(value = "tagid_list")
    private List<String> tagidList;

    @JsonProperty(value = "subscribe_scene")
    private String subscribeScene;

    @JsonProperty(value = "qr_scene")
    private String qrScene;

    @JsonProperty(value = "qr_scene_str")
    private String qrSceneStr;
}