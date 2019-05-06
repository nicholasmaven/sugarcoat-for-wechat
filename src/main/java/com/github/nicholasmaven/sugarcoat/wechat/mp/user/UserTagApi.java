package com.github.nicholasmaven.sugarcoat.wechat.mp.user;

import com.github.nicholasmaven.sugarcoat.wechat.*;
import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.mp.CgiApiContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * @author mawen
 * @date 2019-02-15 17:15
 * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837">user tag api</a>
 */
@Service
public class UserTagApi extends BaseJsonApi {
    private static final String CREATE_INTERFACE = "/tags/create";
    private static final String DELETE_INTERFACE = "/tags/delete";
    private static final String BATCH_TAG_INTERFACE = "/tags/members/batchtagging";

    private ApiContext<Tag> createCtx;
    private ApiContext<GeneralJsonResponse> delCtx;
    private ApiContext<GeneralJsonResponse> batchTagCtx;


    public UserTagApi() {
        createCtx = new CgiApiContext<>(CREATE_INTERFACE, HttpMethod.POST,
                Tag.class);
        delCtx = new CgiApiContext<>(DELETE_INTERFACE, HttpMethod.POST,
                GeneralJsonResponse.class);
        batchTagCtx = delCtx.copyOf(BATCH_TAG_INTERFACE);
    }

    public Tag create(String tagName, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(tagName, "tagName is null or empty");
        Assert.hasText(mpAccessToken, "mpToken is null or empty");
        return execute(createCtx, mpAccessToken, tagName,
                assemblerOf(SerializeFeature.valueCompletion("tag", "name")),
                resolverOf(DeserializeFeature.FIRST_PLAIN_ISOLATED));
    }

    public void delete(int id, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(mpAccessToken, "token is null or empty");
        execute(delCtx, mpAccessToken, id,
                assemblerOf(SerializeFeature.valueCompletion("tag", "id")));
    }

    /**
     * Tagging multiple users
     *
     * @param tagId
     * @param openIds
     * @param mpAccessToken
     * @throws HttpStatusNotOkException
     * @throws ThirdPartyBusinessException
     */
    public void tag(int tagId, Set<String> openIds, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notEmpty(openIds, "openid set are null or empty");
        Assert.hasText(mpAccessToken, "accessToken is null or empty");
        BatchTagRequest request = new BatchTagRequest();
        request.setTagId(tagId);
        request.setOpenIds(openIds);
        execute(batchTagCtx, mpAccessToken, request);
    }
}
