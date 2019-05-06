package com.github.nicholasmaven.sugarcoat.wechat.mp.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author mawen
 * @date 2019-02-18 10:13
 * @see <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140837">batch tag</a>
 */
@Setter
@Getter
public class BatchTagRequest {
    @JsonProperty("tagid")
    private int tagId;
    @JsonProperty("openid_list")
    private Set<String> openIds;
}
