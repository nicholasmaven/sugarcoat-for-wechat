package com.github.nicholasmaven.sugarcoat.wechat.mp.webauth;

import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.BaseJsonApi;
import com.github.nicholasmaven.sugarcoat.wechat.GeneralJsonResponse;
import com.github.nicholasmaven.sugarcoat.wechat.UrlParams;
import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.mp.DummyApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.mp.SnsApiContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Wechat authorization for web page(H5)
 *
 * @author mawen
 * @date 2019-02-18 10:48
 * @see <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140842&t=0.4622491167403">
 * web authorize api</a>
 */
@Service
public class WebAuthorizeApi extends BaseJsonApi {
    public static final String CHINESE = "zh_CN";
    private static final String AUTHORIZED_PAGE_INTERFACE = "/connect/oauth2/authorize";
    private static final String GET_TOKEN_INTERFACE = "/oauth2/access_token";
    private static final String REFRESH_TOKEN_INTERFACE = "/oauth2/refresh_token";
    private static final String CHECK_TOKEN_INTERFACE = "/auth";
    private static final String GET_USER_INTERFACE = "/userinfo";
    private DummyApiContext authCtx;
    private ApiContext<WebAccessToken> getTokenCtx;
    private ApiContext<WebAccessToken> refreshTokenCtx;
    private ApiContext<GeneralJsonResponse> checkTokenCtx;
    private ApiContext<WebUser> getWebUserCtx;

    public WebAuthorizeApi() {
        // The resultClazz is unnecessary, as the real http invocation will not be performed on this
        authCtx = new DummyApiContext(AUTHORIZED_PAGE_INTERFACE, "https://open.weixin.qq.com");
        getTokenCtx = new SnsApiContext<>(GET_TOKEN_INTERFACE, HttpMethod.GET,
                WebAccessToken.class);
        refreshTokenCtx = getTokenCtx.copyOf(REFRESH_TOKEN_INTERFACE);
        checkTokenCtx = new SnsApiContext<>(CHECK_TOKEN_INTERFACE, HttpMethod.GET,
                GeneralJsonResponse.class);
        getWebUserCtx = new SnsApiContext<>(GET_USER_INTERFACE, HttpMethod.GET,
                WebUser.class);
    }

    public String authorizedPage(String mpAppId, String redirectUri, SnsApiScope scope) {
        return authorizedPage(mpAppId, redirectUri, scope, null);
    }

    public String authorizedPage(String mpAppId, String redirectUri, SnsApiScope scope,
                                 String state) {
        Assert.hasText(mpAppId, "appid is null or empty");
        Assert.hasText(redirectUri, "redirectUri is null or empty");
        Assert.notNull(scope, "sns api scope is null or empty");
        String encodedUri;
        try {
            encodedUri = URLEncoder.encode(redirectUri,
                    StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // The sequence of params has to be 'appid, redirect_uri, response_type, scope, state'
        UrlParams params = new UrlParams()
                .with("appid", mpAppId)
                .with("redirect_uri", encodedUri)
                .with("response_type", "code")
                .with("scope", scope.getValue());
        if (StringUtils.hasText(state)) {
            params.with("state", state);
        }
        return authCtx.getFullUrl(params) + "#wechat_redirect";
    }

    public WebAccessToken getToken(String appId, String secret, String code) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(appId, "appid is null or empty");
        Assert.hasText(secret, "secret is null or empty");
        Assert.hasText(code, "code is null or empty");
        UrlParams params = new UrlParams()
                .with("appid", appId)
                .with("secret", secret)
                .with("code", code)
                .with("grant_type", "authorization_code");
        return execute(getTokenCtx, params);
    }

    public WebAccessToken refreshToken(String appId, String refreshToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(appId, "appid is null or empty");
        Assert.hasText(refreshToken, "refreshToken is null or empty");
        UrlParams params = new UrlParams()
                .with("appid", appId)
                .with("refresh_token", refreshToken)
                .with("grant_type", "refresh_token");
        return execute(refreshTokenCtx, params);
    }

    public void checkToken(String openId, String webAccessToken) throws HttpStatusNotOkException,
            ThirdPartyBusinessException {
        Assert.hasText(openId, "openid is null or empty");
        Assert.hasText(webAccessToken, "accessToken is null or empty");
        UrlParams params = new UrlParams()
                .with("openid", openId)
                .with("access_token", webAccessToken);
        execute(checkTokenCtx, params);
    }

    public WebUser getWebUser(String openId, String webAccessToken, String language) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(openId, "openid is null or empty");
        Assert.hasText(webAccessToken, "webAccessToken is null or empty");
        UrlParams params = new UrlParams()
                .with("openid", openId)
                .with("access_token", webAccessToken)
                .with("lang", language);
        return execute(getWebUserCtx, params);
    }

    public WebUser getWebUserInZhCN(String openId, String webAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(openId, "openid is null or empty");
        Assert.hasText(webAccessToken, "webAccessToken is null or empty");
        UrlParams params = new UrlParams()
                .with("openid", openId)
                .with("access_token", webAccessToken)
                .with("lang", CHINESE);
        return execute(getWebUserCtx, params);
    }

    public enum SnsApiScope {
        BASE("snsapi_base"), USER_INFO("snsapi_userinfo");
        private String value;

        SnsApiScope(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
