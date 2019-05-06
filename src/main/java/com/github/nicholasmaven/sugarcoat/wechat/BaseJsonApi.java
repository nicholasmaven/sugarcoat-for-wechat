package com.github.nicholasmaven.sugarcoat.wechat;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author mawen
 * @date 2019-02-26 13:52
 */
public class BaseJsonApi extends AbstractApi {

    private ErrorShortCircuit errorHandler = (map) -> {
        String errCode = map.get("errcode");
        if (errCode != null && Integer.valueOf(errCode) != 0) {
            throw new ThirdPartyBusinessException(errCode, map.get("errmsg"));
        }
        return false;
    };

    protected <T> T execute(ApiContext<T> ctx, UrlParams params) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        String url = ctx.getFullUrl(params);
        return getInvoker().invoke(ctx, url, GetRequestAssembler.JSON,
                ResponseResolver.of(getMarshaller(),
                        DeserializeFeature.NONE, getErrorShortCircuit()));
    }

    protected <T> T execute(ApiContext<T> ctx,
                            UrlParams params,
                            ResponseResolver resolver) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        String url = ctx.getFullUrl(params);
        return getInvoker().invoke(ctx, url, GetRequestAssembler.JSON, resolver);
    }

    protected <T> T execute(ApiContext<T> ctx, String token) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        String url = ctx.getFullUrl(token);
        return getInvoker().invoke(ctx, url, GetRequestAssembler.JSON,
                ResponseResolver.of(getMarshaller(),
                        DeserializeFeature.NONE, getErrorShortCircuit()));
    }

    protected <T> T execute(ApiContext<T> ctx,
                            String token,
                            ResponseResolver resolver) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        String url = ctx.getFullUrl(token);
        return getInvoker().invoke(ctx, url, GetRequestAssembler.JSON, resolver);
    }

    protected <T> T execute(ApiContext<T> ctx,
                            String token,
                            Object obj) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        String url = ctx.getFullUrl(token);
        return getInvoker().invoke(ctx, url, obj,
                assemblerOf(SerializeFeature.NONE),
                resolverOf(DeserializeFeature.NONE));
    }

    protected <T> T execute(ApiContext<T> ctx,
                            String token,
                            Object obj,
                            PostRequestAssembler assembler) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        String url = ctx.getFullUrl(token);
        return getInvoker().invoke(ctx, url, obj, assembler, resolverOf(DeserializeFeature.NONE));
    }

    @Override
    protected ErrorShortCircuit getErrorShortCircuit() {
        return errorHandler;
    }

    @Autowired
    protected void setJsonMarshaller(JacksonJsonMarshaller marshaller) {
        Assert.notNull(marshaller, "marshaller is null");
        setMarshaller(marshaller);
    }
}
