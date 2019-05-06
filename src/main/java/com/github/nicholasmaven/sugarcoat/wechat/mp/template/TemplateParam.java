package com.github.nicholasmaven.sugarcoat.wechat.mp.template;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * All about template info
 *
 * @author mawen
 * @create 2019-03-11 16:36
 */
@Setter
@Getter
public class TemplateParam {
    private String templateId;

    private String url;

    private Map<String, ContentItem> structure;

}
