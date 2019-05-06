package com.github.nicholasmaven.sugarcoat.wechat.mp.template;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.BaseJsonApi;
import com.github.nicholasmaven.sugarcoat.wechat.DeserializeFeature;
import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.mp.CgiApiContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mawen
 * @date 2019-02-15 15:48
 * @see <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1433751277#5">template message</a>
 */
@Slf4j
@Service
public class TemplateMessageApi extends BaseJsonApi {
    private static final String SEND_INTERFACE = "/message/template/send";
    private static final String GET_ALL_TEMPLATES_INTERFACE = "/template/get_all_private_template";

    private final ApiContext<SendTemplateMsgResponse> sendCtx;
    private final ApiContext<List<TemplateData>> getAllCtx;

    public TemplateMessageApi() {
        sendCtx = new CgiApiContext<>(SEND_INTERFACE, HttpMethod.POST,
                SendTemplateMsgResponse.class);
        getAllCtx = new CgiApiContext<>(GET_ALL_TEMPLATES_INTERFACE, HttpMethod.GET,
                new TypeReference<List<TemplateData>>() {
                });
    }

    /**
     * @param template
     * @param toUsers
     * @param token
     * @return
     * @throws HttpStatusNotOkException
     * @throws ThirdPartyBusinessException
     */
    public BatchSendResult sendToMany(TemplateParam template, List<String> toUsers, String token)
            throws HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notNull(template, "template info is null");
        Assert.notEmpty(toUsers, "to users are null or empty");
        Assert.hasText(token, "token is null or empty");
        List<String> responses = new LinkedList<>();
        int successCount = 0;
        for (String openId : toUsers) {
            try {
                SendTemplateMsgResponse result = execute(sendCtx, token,
                        SendTemplateMsgRequest.construct(template, openId));
                if (result.getErrCode() == 0) {
                    responses.add(result.getMsgId());
                    successCount++;
                } else {
                    responses.add("errcode=" + result.getErrCode() + ", errmsg="
                            + result.getErrMsg());
                }
            } catch (Exception e) {
                log.error("send template msg error, templateId={}, toUser={}",
                        template.getTemplateId(), openId);
                responses.add(e.getMessage());
            }
        }
        return new BatchSendResult(toUsers.size(), successCount,
                Collections.unmodifiableList(responses));
    }

    public SendTemplateMsgResponse sendToOne(TemplateParam template, String openId, String token) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notNull(template, "template is null");
        Assert.hasText(openId, "openId is null or empty");
        Assert.hasText(token, "token is null or empty");
        return execute(sendCtx, token, SendTemplateMsgRequest.construct(template, openId));
    }

    public List<TemplateData> getAllTemplates(String token) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(token, "token is null or empty");
        return execute(getAllCtx, token, resolverOf(DeserializeFeature.FIRST_PLAIN_ISOLATED));
    }
}
