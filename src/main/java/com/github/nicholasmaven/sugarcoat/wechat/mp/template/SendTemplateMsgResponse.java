package com.github.nicholasmaven.sugarcoat.wechat.mp.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nicholasmaven.sugarcoat.wechat.GeneralJsonResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-03-04 15:25
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">sendToOne template message</a>
 */
@Setter
@Getter
public class SendTemplateMsgResponse extends GeneralJsonResponse {
    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    @JsonProperty("msgid")
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
