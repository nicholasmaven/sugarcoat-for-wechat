package com.github.nicholasmaven.sugarcoat.wechat.iot.device;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.nicholasmaven.sugarcoat.wechat.*;
import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.iot.DeviceApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.iot.device.AuthorizeRequest.Device;
import com.github.nicholasmaven.sugarcoat.wechat.iot.device.CreateQrCodeResponse.DeviceQrCodeTicket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Iot Api
 *
 * @author mawen
 * @date 2019-02-15 17:10
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_5.html">get device qrcode</a>
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_6.html">authorize device</a>
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_12.html">bind and unbind</a>
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_10.html">wifi status</a>
 * @see <a href="https://iot.weixin.qq.com/wiki/document-2_4.html">get bound openids</a>
 */
@Slf4j
@Service("newDeviceApi")
public class DeviceApi extends BaseJsonApi {
    private static final String CREATE_QRCODE_INTERFACE = "/create_qrcode";
    private static final String AUTH_INTERFACE = "/authorize_device";
    private static final String SEND_STATUS_INTERFACE = "/transmsg";
    private static final String COMPEL_BIND_INTERFACE = "/compel_bind";
    private static final String COMPEL_UNBIND_INTERFACE = "/compel_unbind";
    private static final String GET_OPENID_INTERFACE = "/get_openid";

    private ApiContext<CreateQrCodeResponse> createQrCtx;
    private ApiContext<List<Authorization>> authCtx;
    private ApiContext<SendConnectStatusResponse> sendStatusCtx;
    private ApiContext<GeneralJsonResponse> compelBindCtx;
    private ApiContext<GeneralJsonResponse> compelUnbindCtx;
    private ApiContext<GetOpenIdResponse> openIdCtx;

    private ResponseResolver bindResponseResolver;

    public DeviceApi() {
        createQrCtx = new DeviceApiContext<>(CREATE_QRCODE_INTERFACE, HttpMethod.POST,
                CreateQrCodeResponse.class);
        authCtx = new DeviceApiContext<>(AUTH_INTERFACE, HttpMethod.POST,
                new TypeReference<List<Authorization>>() {
                });
        sendStatusCtx = new DeviceApiContext<>(SEND_STATUS_INTERFACE, HttpMethod.POST,
                SendConnectStatusResponse.class);
        compelBindCtx = new DeviceApiContext<>(COMPEL_BIND_INTERFACE, HttpMethod.POST,
                GeneralJsonResponse.class);
        compelUnbindCtx = compelBindCtx.copyOf(COMPEL_UNBIND_INTERFACE);
        openIdCtx = new DeviceApiContext<>(GET_OPENID_INTERFACE, HttpMethod.GET,
                GetOpenIdResponse.class);
    }

    @PostConstruct
    public void init() {
        bindResponseResolver = resolverOf(DeserializeFeature.FIRST_PLAIN_ISOLATED);
    }

    public List<DeviceQrCodeTicket> createQrCode(List<String> deviceIds, String token) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notEmpty(deviceIds, "deviceIds are null or empty");
        Assert.hasText(token, "accessToken is null or empty");
        CreateQrCodeRequest request = new CreateQrCodeRequest(deviceIds.size(), deviceIds);
        CreateQrCodeResponse response = execute(createQrCtx, token, request);
        List<DeviceQrCodeTicket> codes = response.getCodes();
        int size = codes.size();
        if (response.getDeviceNum() != size) {
            throw new IllegalStateException("response invalid! deviceNum != actualSize" +
                    "deviceNum=" + response.getDeviceNum() + ", codeSize=" + size);
        }
        return codes;
    }

    public List<Authorization> authorize(List<Device> devices,
                                         String productId,
                                         String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return authorize(devices, OpType.AUTHORIZE, productId, mpAccessToken);
    }

    public List<Authorization> updateAuthInfo(List<Device> devices, String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return authorize(devices, OpType.UPDATE, null, mpAccessToken);
    }

    private List<Authorization> authorize(List<Device> devices,
                                          OpType op,
                                          String productId,
                                          String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notEmpty(devices, "devices are null or empty");
        Assert.hasText(mpAccessToken, "mpAccessToken is null or empty");
        AuthorizeRequest request = new AuthorizeRequest();
        if (OpType.UPDATE == op) {
            // opType is required when authorize interface worked in update mode
            request.setOpType(String.valueOf(op.getValue()));
        }
        request.setProductId(productId);
        request.setDevices(devices);
        request.setNum(String.valueOf(devices.size()));
        return execute(authCtx, mpAccessToken, request,
                resolverOf(DeserializeFeature.FIRST_PLAIN_ISOLATED));
    }

    public void notifyConnected(String deviceId,
                                String deviceType,
                                String openId,
                                String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        notifyConnectStatus(deviceId, deviceType, openId, ConnectStatus.CONNECTED, mpAccessToken);
    }

    public void notifyDisconnected(String deviceId,
                                   String deviceType,
                                   String openId,
                                   String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        notifyConnectStatus(deviceId, deviceType, openId, ConnectStatus.DISCONNECTED,
                mpAccessToken);
    }

    /**
     * Must run after establishing binding relationship, or else response will report openid invalid
     */
    private void notifyConnectStatus(String deviceId,
                                     String type,
                                     String openId,
                                     ConnectStatus status,
                                     String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(deviceId, "deviceId is null or empty");
        Assert.hasText(type, "type is null or empty");
        Assert.hasText(openId, "openId is null or empty");
        Assert.notNull(status, "connect status is null");
        Assert.hasText(mpAccessToken, "mpAccessToken is null or empty");

        SendConnectStatusRequest request = new SendConnectStatusRequest();
        request.setDeviceId(deviceId);
        request.setOpenId(openId);
        request.setDeviceStatus(String.valueOf(status.getValue()));
        request.setDeviceType(type);

        SendConnectStatusResponse response = execute(sendStatusCtx, mpAccessToken, request);
        if (response.getRet() != 0) {
            throw new ThirdPartyBusinessException(String.valueOf(response.getRet()),
                    response.getRetInfo());
        }
    }


    public void compelBind(String deviceId,
                           String openId,
                           String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        compelBind(deviceId, openId, mpAccessToken, true);
    }

    /**
     * Take care of the frequency of invocation, it may not really unbind if invoked interval
     * less than 15 seconds
     */
    public void compelUnbind(String deviceId,
                             String openId,
                             String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        compelBind(deviceId, openId, mpAccessToken, false);
    }

    private void compelBind(String deviceId,
                            String openId,
                            String mpAccessToken,
                            boolean bind) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(deviceId, "deviceId is null or empty");
        Assert.hasText(openId, "openId is null or empty");
        Assert.hasText(mpAccessToken, "accessToken is null or empty");
        CompelBindRequest request = new CompelBindRequest();
        request.setDeviceId(deviceId);
        request.setOpenId(openId);
        execute(bind ? compelBindCtx : compelUnbindCtx, mpAccessToken, request,
                bindResponseResolver);
    }

    public List<String> getBoundOpenIds(String deviceId,
                                        String deviceType,
                                        String mpAccessToken) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        UrlParams params = new UrlParams()
                .with("access_token", mpAccessToken)
                .with("device_type", deviceType)
                .with("device_id", deviceId);
        GetOpenIdResponse response = execute(openIdCtx, params);
        if (response.getMsg().getErrCode() != 0) {
            throw new ThirdPartyBusinessException(response.getMsg().getErrMsg(),
                    response.getMsg().getErrMsg());
        }
        return response.getOpenIds();
    }

    public enum OpType {
        AUTHORIZE(0), UPDATE(1);
        private int value;

        OpType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ConnectStatus {
        DISCONNECTED(0), CONNECTED(1);
        private int value;

        ConnectStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
