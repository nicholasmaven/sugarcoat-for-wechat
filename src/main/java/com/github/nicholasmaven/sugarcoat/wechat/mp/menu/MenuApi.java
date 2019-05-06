package com.github.nicholasmaven.sugarcoat.wechat.mp.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nicholasmaven.sugarcoat.wechat.*;
import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.mp.CgiApiContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Customize mp menu
 *
 * @author mawen
 * @date 2019-02-26 14:52
 * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014">menu query</a>
 * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013">menu create</a>
 **/
@Service
public class MenuApi extends BaseJsonApi {
    private static final String GET_INTERFACE = "/menu/get";
    private static final String CREATE_INTERFACE = "/menu/create";
    private static final int LEVEL_ONE_LIMIT = 3;
    private static final int LEVEL_DEPTHS = 3;
    private static final int SUB_LEVEL_LIMIT = 5;

    private ApiContext<List<Button>> getCtx;
    private ApiContext<GeneralJsonResponse> createCtx;

    @PostConstruct
    public void init() {
        getCtx = new CgiApiContext<>(GET_INTERFACE, HttpMethod.GET,
                new TypeReference<List<Button>>() {});
        createCtx = new CgiApiContext<>(CREATE_INTERFACE, HttpMethod.POST,
                GeneralJsonResponse.class);
    }

    /**
     * To avoid extra json deserialize-serialize, we trust response completely and do no
     * check on the entity structure
     *
     * @return
     * @throws Exception
     */
    public List<Button> get(String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return execute(getCtx, mpAccessToken, resolverOf(DeserializeFeature.FIRST_PLAIN_ISOLATED));
    }

    public void create(List<Button> buttons, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notEmpty(buttons, "buttons are null or empty");
        Assert.hasText(mpAccessToken, "mpAccessToken are null or empty");
        Assert.isTrue(buttons.size() <= LEVEL_ONE_LIMIT,
                "the number of level one buttons exceed " + LEVEL_ONE_LIMIT);
        checkMenuDepths(buttons);
        execute(createCtx, mpAccessToken, buttons,
                assemblerOf(SerializeFeature.containerizeCompletion(null, "button")));
    }

    private void checkMenuDepths(List<Button> buttons) {
        buttons.forEach(e -> {
            recursive(e, 1);
        });
    }

    private void recursive(Button button, int depth) {
        Assert.isTrue(depth <= LEVEL_DEPTHS, "the level depth exceed "
                + LEVEL_DEPTHS);
        List<Button> descendants = button.getSubButtons();
        Assert.isTrue(null == descendants || descendants.size() <= SUB_LEVEL_LIMIT,
                "the number of sub buttons exceed " + SUB_LEVEL_LIMIT);
        if (null == descendants) return;
        descendants.forEach(e -> recursive(e, depth));
    }

}
