package com.github.nicholasmaven.sugarcoat.wechat.mp.apiticket;

import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.BaseJsonApi;
import com.github.nicholasmaven.sugarcoat.wechat.UrlParams;
import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.mp.CgiApiContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author zhangjw
 * create at 2019-03-27 17:00
 * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115" />
 * @since 1.0.0
 **/
@Service
public class ApiTicketApi extends BaseJsonApi {
    private static final String GET_TICKET = "/ticket/getticket";

    private ApiContext<ApiTicket> jsApiCtx;

    @PostConstruct
    public void init() {
        jsApiCtx = new CgiApiContext<>(GET_TICKET, HttpMethod.GET, ApiTicket.class);
    }

    public ApiTicket getJsApiTicket(String mpAccessToken)
            throws HttpStatusNotOkException, ThirdPartyBusinessException {
        UrlParams params = new UrlParams()
                .with("access_token", mpAccessToken)
                .with("type", "jsapi");
        return execute(jsApiCtx, params);
    }
}
