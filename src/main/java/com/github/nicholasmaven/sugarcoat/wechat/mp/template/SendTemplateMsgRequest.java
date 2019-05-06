package com.github.nicholasmaven.sugarcoat.wechat.mp.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author mawen
 * @date 2019-02-15 15:50
 * @see <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1433751277#5">template message</a>
 */
@Setter
@Getter
public class SendTemplateMsgRequest {
    @JsonProperty("touser")
    private String toUser;

    @JsonProperty("template_id")
    private String templateId;

    private String url;

    private Map<String, ContentItem> data;

    public static SendTemplateMsgRequest construct(TemplateParam param, String openId) {
        SendTemplateMsgRequest request = new SendTemplateMsgRequest();
        request.setTemplateId(param.getTemplateId());
        request.setToUser(openId);
        request.setUrl(param.getUrl());
        request.setData(param.getStructure());
        return request;
    }

}
