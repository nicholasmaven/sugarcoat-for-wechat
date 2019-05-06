package com.github.nicholasmaven.sugarcoat.wechat;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author mawen
 * @date 2019-03-11 10:45
 */
public abstract class AbstractApi {
    protected abstract ErrorShortCircuit getErrorShortCircuit();

    private ApiInvoker invoker;
    private EntityMarshaller entityMarshaller;

    protected <T> T execute(ApiContext<T> ctx,
                            String token,
                            Object obj,
                            ResponseResolver resolver) throws 
			HttpStatusNotOkException, ThirdPartyBusinessException {
        String url = ctx.getFullUrl(token);
        return invoker.invoke(ctx, url, obj,
                assemblerOf(SerializeFeature.NONE), resolver);
    }

    protected <T> T execute(ApiContext<T> ctx,
                            String token,
                            Object obj,
                            RequestAssembler assembler,
                            ResponseResolver resolver) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        String url = ctx.getFullUrl(token);
        return invoker.invoke(ctx, url, obj, assembler, resolver);
    }

    @Autowired
    public void setInvoker(ApiInvoker invoker) {
        Assert.notNull(invoker, "invoker is null");
        this.invoker = invoker;
    }

    protected ApiInvoker getInvoker() {
        return invoker;
    }

    protected void setMarshaller(EntityMarshaller entityMarshaller) {
        Assert.notNull(entityMarshaller, "entityMarshaller is null");
        this.entityMarshaller = entityMarshaller;
    }

    protected EntityMarshaller getMarshaller() {
        return entityMarshaller;
    }

    protected PostRequestAssembler assemblerOf(SerializeFeature feature) {
        return PostRequestAssembler.of(entityMarshaller, feature);
    }

    protected ResponseResolver resolverOf(DeserializeFeature feature) {
        return ResponseResolver.of(entityMarshaller, feature, getErrorShortCircuit());
    }

    protected ResponseResolver resolverOf(DeserializeFeature feature,
                                          ErrorShortCircuit errorHandler) {
        return ResponseResolver.of(entityMarshaller, feature, errorHandler);
    }
}
