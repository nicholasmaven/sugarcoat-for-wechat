package com.github.nicholasmaven.sugarcoat.wechat.mp.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-15 15:25
 * @see <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140547#7">text message</a>
 */
public class TextMessage {
    @Setter
    @Getter
    @JsonProperty("touser")
    private String toUser;

    @Getter
    @JsonProperty("msgtype")
    private String msgType = "text";

    @Setter
    @Getter
    private Text text;

    @Setter
    @Getter
    public static class Text {
        String content;
    }
}
