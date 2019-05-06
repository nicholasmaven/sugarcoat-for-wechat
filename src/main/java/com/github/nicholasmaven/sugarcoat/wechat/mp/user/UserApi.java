package com.github.nicholasmaven.sugarcoat.wechat.mp.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nicholasmaven.sugarcoat.wechat.*;
import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.mp.CgiApiContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Get mp user info
 *
 * @author mawen
 * @date 2019-02-15 17:14
 * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839">user api</a>
 */
@Service
public class UserApi extends BaseJsonApi {
    private static final String GET_INTERFACE = "/user/info";
    private static final String GET_BATCH_INTERFACE = "/user/info/batchget";
    private static final String LIST_OPENID_INTERFACE = "/user/get";
    private static final String CHINESE = "zh_CN";

    private ApiContext<MpUser> getCtx;
    private ApiContext<List<MpUser>> getBatchCtx;
    private ApiContext<ListFansOpenIdsResponse> listOpenIdCtx;

    public UserApi() {
        getCtx = new CgiApiContext<>(GET_INTERFACE, HttpMethod.GET, MpUser.class);
        getBatchCtx = new CgiApiContext<>(GET_BATCH_INTERFACE, HttpMethod.POST,
                new TypeReference<List<MpUser>>() {});
        listOpenIdCtx = new CgiApiContext<>(LIST_OPENID_INTERFACE, HttpMethod.GET,
                ListFansOpenIdsResponse.class);
    }

    /**
     * @param openId
     * @param mpAccessToken
     * @param language zh_CN, en_US...etc.
     * @return
     * @throws HttpStatusNotOkException
     * @throws ThirdPartyBusinessException
     */
    public MpUser get(String openId, String mpAccessToken, String language) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(openId, "openId is null or empty");
        Assert.hasText(mpAccessToken, "mpAccessToken is null or empty");
        UrlParams params = new UrlParams()
                .with("access_token", mpAccessToken)
                .with("openid", openId)
                .with("lang", language);
        return execute(getCtx, params);
    }

    /**
     * The province, city, country will display in Chinese Simplified
     *
     * @param openId
     * @param mpAccessToken
     * @return
     * @throws HttpStatusNotOkException
     * @throws ThirdPartyBusinessException
     */
    public MpUser getInZhCN(String openId, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return get(openId, mpAccessToken, CHINESE);
    }

    /**
     * @param openIds
     * @param mpAccessToken
     * @param language zh_CN, en_US...etc.
     * @return
     * @throws HttpStatusNotOkException
     * @throws ThirdPartyBusinessException
     */
    public List<MpUser> bulkGet(List<String> openIds, String mpAccessToken, String language) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notEmpty(openIds, "openId list is null or empty");
        Assert.hasText(mpAccessToken, "mpAccessToken is null or empty");
        List<UserRequestItem> params = openIds.stream()
                .map(e -> new UserRequestItem(e, language))
                .collect(Collectors.toList());
        return execute(getBatchCtx, mpAccessToken, params,
                assemblerOf(SerializeFeature.containerizeCompletion(null, "user_list")),
                resolverOf(DeserializeFeature.FIRST_PLAIN_ISOLATED));
    }

    public List<MpUser> bulkGetInZhCN(List<String> openIds, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return bulkGet(openIds, mpAccessToken, CHINESE);
    }

    /**
     * List openIds of media platform
     *
     * @param nextOpenId start position, null or empty means start with the first one
     * @param mpAccessToken mp access token
     * @return
     * @throws HttpStatusNotOkException
     * @throws ThirdPartyBusinessException
     */
    public ListFansOpenIdsResponse listFansOpenIds(String nextOpenId, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(mpAccessToken, "mpAccessToken is null or empty");
        UrlParams params = new UrlParams().with("access_token", mpAccessToken);
        if (!StringUtils.isEmpty(nextOpenId)) {
            params.with("next_openid", nextOpenId);
        }
        return execute(listOpenIdCtx, params);
    }
}
