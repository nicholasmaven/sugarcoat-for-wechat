package com.github.nicholasmaven.sugarcoat.wechat.mp.template;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * For example
 *
 * <pre>
 * {
 *     "first": {
 *         "value": "value-first",
 *         "color": "#173177"
 *     },
 *     "remark": {
 *         "value": "value-remark",
 *         "color": "#173177"
 *     },
 *     "keyword1": {
 *         "value": "value-keyword1",
 *         "color": "#173177"
 *     },
 *     "keyword2": {
 *         "value": "value-keyword2",
 *         "color": "#173177"
 *     },
 *     ...
 * }
 * </pre>
 */
@Setter
@Getter
public class ContentItem {
    private String value;
    private String color = "#173177";

    public ContentItem(String value) {
        this.value = value;
    }

    public ContentItem(String value, String color) {
        this.value = value;
        this.color = StringUtils.isEmpty(color) ? "#173177" : color;
    }
}