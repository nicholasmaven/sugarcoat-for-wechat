package com.github.nicholasmaven.sugarcoat.wechat.mp.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-03-11 11:13
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277">get all templates</a>
 */

@Setter
@Getter
public class TemplateData {
    private String title;
    @JsonProperty("template_id")
    private String id;
    @JsonProperty("primary_industry")
    private String primaryIndustry;
    @JsonProperty("deputy_industry")
    private String deputyIndustry;
    @JsonProperty("content")
    private String jsonContent;
    private String example;
}