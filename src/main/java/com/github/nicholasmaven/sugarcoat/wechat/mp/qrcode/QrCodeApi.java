package com.github.nicholasmaven.sugarcoat.wechat.mp.qrcode;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.ApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.BaseJsonApi;
import com.github.nicholasmaven.sugarcoat.wechat.mp.CgiApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.mp.qrcode.QrCodeRequest.ActionInfo;
import com.github.nicholasmaven.sugarcoat.wechat.mp.qrcode.QrCodeRequest.Scene;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * create temporary qr code
 *
 * @author mawen
 * @date 2019-02-15 16:24
 * @see <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542">qrcode api</a>
 */
@Service
public class QrCodeApi extends BaseJsonApi {
    private static final String CREATE_INTERFACE = "/qrcode/create";
    private static final int DEFAULT_EXPIRE_SECONDS = 30;
    private ApiContext<QrCodeTicket> createCtx;

    protected QrCodeApi() {
        createCtx = new CgiApiContext<>(CREATE_INTERFACE, HttpMethod.POST, QrCodeTicket.class);
    }

    public QrCodeTicket createTempQr(int sceneId,
                                     int expireSeconds,
                                     String mpAccessToken) throws HttpStatusNotOkException,
            ThirdPartyBusinessException {
        Assert.isTrue(sceneId > 0, "sceneId exceed [1, " + Integer.MAX_VALUE + "]");
        Assert.isTrue(expireSeconds > 0 && expireSeconds < 2592000, "expireSeconds exceed (0, " +
                "2592000(30d)]");
        Assert.hasText(mpAccessToken, "accessToken is null or empty");

        Scene scene = new Scene();
        scene.setSceneId(sceneId);
        ActionInfo action = new ActionInfo();
        action.setScene(scene);
        QrCodeRequest param = new QrCodeRequest();
        param.setActionName("QR_SCENE");
        param.setExpireSeconds(expireSeconds);
        param.setActionInfo(action);
        return execute(createCtx, mpAccessToken, param);
    }

    public QrCodeTicket createTempQr(String sceneStr, String mpAccessToken) throws HttpStatusNotOkException,
            ThirdPartyBusinessException {
        return createTempQr(sceneStr, DEFAULT_EXPIRE_SECONDS, mpAccessToken);
    }

    public QrCodeTicket createTempQr(String sceneStr,
                                     int expireSeconds,
                                     String mpAccessToken) throws HttpStatusNotOkException,
            ThirdPartyBusinessException {
        Assert.isTrue(sceneStr.length() > 0 && sceneStr.length() <= 64, "sceneStr length exceed [1, 64]");
        Assert.isTrue(expireSeconds > 0 && expireSeconds < 2592000, "expireSeconds exceed (0, " +
                "2592000(30d)]");
        Assert.hasText(mpAccessToken, "accessToken is null or empty");

        Scene scene = new Scene();
        scene.setSceneStr(sceneStr);
        ActionInfo action = new ActionInfo();
        action.setScene(scene);
        QrCodeRequest param = new QrCodeRequest();
        param.setActionName("QR_STR_SCENE");
        param.setExpireSeconds(expireSeconds);
        param.setActionInfo(action);
        return execute(createCtx, mpAccessToken, param);
    }

    public QrCodeTicket createTempQr(int sceneId, String mpAccessToken) throws HttpStatusNotOkException,
            ThirdPartyBusinessException {
        return createTempQr(sceneId, DEFAULT_EXPIRE_SECONDS, mpAccessToken);
    }

    public QrCodeTicket createLimitQr(String sceneStr, String mpAccessToken) throws HttpStatusNotOkException,
            ThirdPartyBusinessException {
        Assert.isTrue(sceneStr.length() > 0 && sceneStr.length() <= 64, "sceneStr length exceed [1, 64]");
        Assert.hasText(mpAccessToken, "accessToken is null or empty");

        Scene scene = new Scene();
        scene.setSceneStr(sceneStr);
        ActionInfo action = new ActionInfo();
        action.setScene(scene);
        QrCodeRequest param = new QrCodeRequest();
        param.setActionName("QR_LIMIT_STR_SCENE");
        param.setActionInfo(action);
        return execute(createCtx, mpAccessToken, param);
    }

    public QrCodeTicket createLimitQr(int sceneId, String mpAccessToken) throws HttpStatusNotOkException,
            ThirdPartyBusinessException {
        Assert.isTrue(sceneId > 0 && sceneId <= 100000, "sceneId exceed [1, 100000]");
        Assert.hasText(mpAccessToken, "accessToken is null or empty");

        Scene scene = new Scene();
        scene.setSceneId(sceneId);
        ActionInfo action = new ActionInfo();
        action.setScene(scene);
        QrCodeRequest param = new QrCodeRequest();
        param.setActionName("QR_LIMIT_SCENE");
        param.setActionInfo(action);
        return execute(createCtx, mpAccessToken, param);
    }
}
