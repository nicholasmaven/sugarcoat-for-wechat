package com.github.nicholasmaven.sugarcoat.wechat.mp.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mawen
 * @date 2019-02-15 15:30
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140547#7">pic and article message</a>
 */
public class PicArticleMessage {
    @Setter
    @Getter
    @JsonProperty("touser")
    private String toUser;

    @Getter
    @JsonProperty("msgtype")
    private String msgType = "news";

    @Setter
    @Getter
    private ArticleContent news;

    @Setter
    @Getter
    public static class ArticleContent {
        List<Article> articles;
    }

    @Setter
    @Getter
    public static class Article {
        String title;
        String description;
        String url;
        @JsonProperty("picurl")
        String picUrl;
    }
}
