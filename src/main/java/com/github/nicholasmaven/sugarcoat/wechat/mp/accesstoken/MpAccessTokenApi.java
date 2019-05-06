package com.github.nicholasmaven.sugarcoat.wechat.mp.accesstoken;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.BaseJsonApi;
import com.github.nicholasmaven.sugarcoat.wechat.mp.CgiApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.UrlParams;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Mp access token
 *
 * @author mawen
 * @date 2019-02-26 13:52
 * @see
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013">mp accesstoken</a>
 */
@Service
public class MpAccessTokenApi extends BaseJsonApi {
    private static final String GET_INTERFACE = "/token";
    private ApiContext<MpAccessToken> getCtx;

    @PostConstruct
    public void init() {
        getCtx = new CgiApiContext<>(GET_INTERFACE, HttpMethod.GET, MpAccessToken.class);
    }

    public MpAccessToken create(String mpAppId, String secret) throws HttpStatusNotOkException,
            ThirdPartyBusinessException {
        UrlParams params = new UrlParams()
                .with("grant_type", "client_credential")
                .with("appid", mpAppId)
                .with("secret", secret);
        return execute(getCtx, params);
    }
}
