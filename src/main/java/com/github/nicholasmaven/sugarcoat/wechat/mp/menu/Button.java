package com.github.nicholasmaven.sugarcoat.wechat.mp.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mawen
 * @date @date 2019-02-18 17:54
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013">wechat menu doc</a>
 **/
@Data
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Button {
    private String name;
    private String type;
    private String url;
    private String key;

    // not necessary
    @JsonProperty("sub_button")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Button> subButtons;

    // below properties only need for miniprogram
    @JsonProperty("media_id")
    private String mediaId;
    @JsonProperty("appid")
    private String appId;
    @JsonProperty("pagepath")
    private String pagePath;

}
