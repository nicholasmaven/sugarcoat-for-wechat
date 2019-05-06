package com.github.nicholasmaven.sugarcoat.wechat.mp.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mawen
 * @date 2019-03-06 11:24
 * @see <a href="">list fans' openid</a>
 */
@Setter
@Getter
public class ListFansOpenIdsResponse {
    private long total;
    private int count;
    private OpenIdBatch data;
    @JsonProperty("next_openid")
    private String nextOpenId;

    @Getter
    @Setter
    public static class OpenIdBatch {
        @JsonProperty("openid")
        private List<String> openIds;
    }
}
