package com.github.nicholasmaven.sugarcoat.wechat.mp.qrcode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-15 16:43
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542">qrcode request</>
 */

@JsonInclude(Include.NON_NULL)
public class QrCodeRequest {
    @Setter
    @Getter
    @JsonProperty("expire_seconds")
    private int expireSeconds;

    @Getter
    @JsonProperty("action_name")
    private String actionName;

    @Setter
    @Getter
    @JsonProperty("action_info")
    private ActionInfo actionInfo;

    void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Setter
    @Getter
    public static class ActionInfo {
        private Scene scene;

    }

    @Setter
    @Getter
    public static class Scene {
        @JsonProperty("scene_id")
        private Integer sceneId;
        @JsonProperty("scene_str")
        private String sceneStr;
    }
}
