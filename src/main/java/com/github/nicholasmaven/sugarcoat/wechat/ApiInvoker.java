package com.github.nicholasmaven.sugarcoat.wechat;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Stream;

/**
 * Invoke http or https url
 *
 * @author mawen
 * @date 2019-02-26 13:44
 */
@Component
public class ApiInvoker {

    @Autowired
    @Qualifier("defaultRestTemplate")
    private RestTemplate regularTemplate;

    public <T> T invoke(ApiContext<T> ctx,
                        String url,
                        RequestAssembler assembler,
                        ResponseResolver resolver) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return invoke(ctx, url, null, assembler, resolver);
    }

    public <T> T invoke(ApiContext<T> ctx,
                        String url,
                        Object obj,
                        RequestAssembler assembler,
                        ResponseResolver resolver) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return invoke(regularTemplate, ctx, url, obj, ctx.getResultType(), assembler, resolver);
    }

    public <T, R> R invoke(RestTemplate restTemplate,
                        ApiContext<T> ctx,
                        String url,
                        Object obj,
                        TypeWrapper<R> resultType,
                        RequestAssembler assembler,
                        ResponseResolver resolver) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
		Assert.notNull(restTemplate, "restTemplate is null");
        Assert.notNull(ctx, "apiContext is null");
        Assert.hasText(url, "url is null or empty");
        Assert.notNull(resultType, "resultType is null");
        Assert.notNull(assembler, "request assembler is null");
        Assert.notNull(resolver, "response resolver is null");
        Assert.isTrue(!(obj instanceof Stream), "Stream is not supported");

        RequestCallback callback = assembler.assemble(obj);
        ResponseExtractor<ResponseEntity<String>> extractor =
                resolver.stringExtractor();
        ResponseEntity<String> result = restTemplate.execute(url, ctx.getMethod(),
                callback,
                extractor);
        return resolver.resolve(result, resultType);
    }

}
