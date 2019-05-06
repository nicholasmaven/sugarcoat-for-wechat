package com.github.nicholasmaven.sugarcoat.wechat.mp.message;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.BaseJsonApi;
import com.github.nicholasmaven.sugarcoat.wechat.mp.CgiApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.GeneralJsonResponse;
import com.github.nicholasmaven.sugarcoat.wechat.mp.message.PicArticleMessage.Article;
import com.github.nicholasmaven.sugarcoat.wechat.mp.message.PicArticleMessage.ArticleContent;
import com.github.nicholasmaven.sugarcoat.wechat.mp.message.TextMessage.Text;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * Send custom message
 *
 * @author mawen
 * @date 2019-01-31 16:43
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547">customer message</a>
 */
@Service
public class CustomerMessageApi extends BaseJsonApi {
    private static final String SEND_INTERFACE = "/message/custom/send";
    private ApiContext<GeneralJsonResponse> msgCtx;

    @PostConstruct
    protected void init() {
        msgCtx = new CgiApiContext<>(SEND_INTERFACE, HttpMethod.POST, GeneralJsonResponse.class);
    }

    public void sendText(String toOpenId, String text, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(toOpenId, "toOpenId is null or empty");
        Assert.hasText(text, "text is null or empty");
        Assert.hasText(mpAccessToken, "token is null or empty");
        Text subTxt = new Text();
        subTxt.setContent(text);
        TextMessage msg = new TextMessage();
        msg.setToUser(toOpenId);
        msg.setText(subTxt);
        execute(msgCtx, mpAccessToken, msg);
    }

    public void sendPicArticle(String toOpenId, Article article, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(toOpenId, "toOpenId is null or empty");
        Assert.notNull(article, "article is null");
        Assert.hasText(mpAccessToken, "token is null or empty");
        ArticleContent content = new ArticleContent();
        content.setArticles(Collections.singletonList(article));
        PicArticleMessage msg = new PicArticleMessage();
        msg.setNews(content);
        msg.setToUser(toOpenId);
        execute(msgCtx, mpAccessToken, msg);
    }

}
