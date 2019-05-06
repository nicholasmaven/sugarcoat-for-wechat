package com.github.nicholasmaven.sugarcoat.wechat;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.merchant.MerchantApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.merchant.MerchantInfo;
import com.github.nicholasmaven.sugarcoat.wechat.merchant.WechatSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Stream;

/**
 * <p>
 * Merchant-based api, including pay, applyRefund, account transfer etc.
 * <br/>
 * There are two kinds of response errors, the first at merchant interface level which is indicated
 * by return_code, the second at business level indicated by err_code
 * </p>
 *
 * @author mawen
 * @date 2019-02-18 11:38
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">JSAPI pay</a>
 */
public class MerchantApi extends AbstractApi {
    private ErrorShortCircuit errorHandler = (map) -> {
        String errCode = map.get("err_code");
        if (errCode != null && !errCode.equals("SUCCESS")) {
            throw new ThirdPartyBusinessException(errCode, map.get("err_code_des"));
        } else if (map.get("return_code").equals("FAIL")) {
            throw new ThirdPartyBusinessException("Failed at Merchant interface level",
                    map.get("return_msg"));
        }
        return true;
    };
    private WechatSigner signer;
    private RestTemplate merchantRest;

    @SuppressWarnings("unchecked")
    protected <T> T execute(MerchantApiContext<T> merCtx,
                            MerchantInfo merchantInfo,
                            Object obj,
                            ResponseResolver resolver,
                            boolean useDefaultMerchantKey,
                            boolean needCheckSign) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.notNull(merCtx, "merchant context is null");
        Assert.notNull(merchantInfo, "payment context is null");
        Assert.notNull(obj, "obj is null");
        Assert.notNull(resolver, "resolver is null");
        Assert.isTrue(!(obj instanceof Stream), "Invalid obj!, Stream type not supported");

        Map<String, String> map;
        if (obj instanceof Map) {
            map = (Map<String, String>) obj;
        } else {
            map = getMarshaller().extractPropertyMap(obj);
        }
        String signature = signer.sign(map, merchantInfo, useDefaultMerchantKey);
        map.put("sign", signature);
        JsonNodeAndProperties result = getInvoker().invoke(merchantRest,
                merCtx,
                merCtx.getFullUrl(),
                map,
                TypeWrapper.of(JsonNodeAndProperties.class),
                assemblerOf(SerializeFeature.NONE),
                resolverOf(DeserializeFeature.NONE));
        map = result.getPropertyMap();
        if (needCheckSign) {
            signature = map.get("sign");
            signer.validate(map, merchantInfo, signature);
        }
        return getMarshaller().unmarshal(result.getNode(), merCtx.getResultType(),
                resolver.getFeature());
    }

    protected <T> T execute(MerchantApiContext<T> merCtx,
                            MerchantInfo merchantInfo,
                            Object obj) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return execute(merCtx, merchantInfo, obj, resolverOf(DeserializeFeature.NONE), true, true);
    }

    protected <T> T execute(MerchantApiContext<T> merCtx,
                            MerchantInfo merchantInfo,
                            Object obj,
                            boolean useDefaultMerchantKey) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return execute(merCtx, merchantInfo, obj, resolverOf(DeserializeFeature.NONE),
                useDefaultMerchantKey, true);
    }

    protected <T> T execute(MerchantApiContext<T> merCtx,
                            MerchantInfo merchantInfo,
                            Object obj,
                            boolean useDefaultMerchantKey,
                            boolean needCheckSign) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return execute(merCtx, merchantInfo, obj, resolverOf(DeserializeFeature.NONE),
                useDefaultMerchantKey, needCheckSign);
    }

    @Autowired
    public void setXmlMarshaller(JacksonXmlMarshaller marshaller) {
        Assert.notNull(marshaller, "marshaller is null");
        super.setMarshaller(marshaller);
    }

    @Override
    protected ErrorShortCircuit getErrorShortCircuit() {
        return errorHandler;
    }

    protected PostRequestAssembler assemblerOf(SerializeFeature feature) {
        return PostRequestAssembler.of(getMarshaller(), feature);
    }

    protected ResponseResolver resolverOf(DeserializeFeature feature) {
        return ResponseResolver.of(getMarshaller(), feature, errorHandler);
    }

    @Autowired
    public void setWechatSigner(WechatSigner signer) {
        this.signer = signer;
    }

    @Autowired
    public void setMerchantRestFactory(@Qualifier("merchantRestTemplate")
                                               RestTemplate merchantRest) {
        this.merchantRest = merchantRest;
    }
}
