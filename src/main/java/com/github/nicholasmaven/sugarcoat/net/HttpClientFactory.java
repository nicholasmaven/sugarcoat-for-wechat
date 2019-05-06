package com.github.nicholasmaven.sugarcoat.net;

import com.github.nicholasmaven.sugarcoat.wechat.merchant.MerchantInfo;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author mawen
 * @create 2019-04-26 16:02
 */
public class HttpClientFactory {

    public static CloseableHttpClient defaultHttpClient(HttpClientConfig config) throws
            IOException, GeneralSecurityException {
        SSLConnectionSocketFactory sslSockFactory = new SSLConnectionSocketFactory(
                SSLContextBuilder.create()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build());
        return createHttpClient(config, sslSockFactory);
    }

    public static CloseableHttpClient merchantHttpClient(HttpClientConfig config,
                                                         MerchantInfo merchantInfo) throws
            IOException, GeneralSecurityException {
        Assert.notNull(merchantInfo, "merchantInfo is null");
        SSLConnectionSocketFactory sslSockFactory = new SSLConnectionSocketFactory(
                SSLContextBuilder.create()
                        .loadKeyMaterial(merchantInfo.getKeyStore(),
                                merchantInfo.getMchId().toCharArray())
                        .build());
        return createHttpClient(config, sslSockFactory);
    }

    private static CloseableHttpClient createHttpClient(HttpClientConfig config,
                                                        SSLConnectionSocketFactory sslSockFactory) throws
            UnknownHostException {
        Registry<ConnectionSocketFactory> registry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslSockFactory)
                        .build();
        PoolingHttpClientConnectionManager connMgr =
                new PoolingHttpClientConnectionManager(registry, null, null, null,
                        config.getMinsToLive() == null ? 15 : config.getMinsToLive(),
                        TimeUnit.MINUTES);
        InetAddress addr = StringUtils.isEmpty(config.getLocalIp()) ? Inet4Address.getLocalHost() :
                Inet4Address.getByName(config.getLocalIp());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(config.getConnRequestTimeoutMillSeconds() == null ?
                        3000 :
                        config.getConnRequestTimeoutMillSeconds())
                .setConnectTimeout(config.getConnTimeoutMillSeconds() == null ? 10000 :
                        config.getConnTimeoutMillSeconds())
                .setContentCompressionEnabled(true)
                .setSocketTimeout(config.getSocketTimeoutMillSeconds() == null ? 5000 :
                        config.getSocketTimeoutMillSeconds())
                .setLocalAddress(addr)
                .build();
        List<Header> headers = new LinkedList<>();
        headers.add(new BasicHeader(HTTP.CONN_KEEP_ALIVE, "0"));
        return HttpClients.custom()
                .setConnectionManager(connMgr)
                .setDefaultHeaders(headers)
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(config.getMaxPerRoute() == null ? 10 :
                        config.getMaxPerRoute())
                .setMaxConnTotal(config.getMaxTotal() == null ? 200 : config.getMaxTotal())
                .build();
    }

    public static ClientHttpRequestFactory httpClientFactory(HttpClientConfig config,
                                                             MerchantInfo merchantInfo) throws
            IOException, GeneralSecurityException {
        HttpClient client = null;
        if (merchantInfo == null) {
            client = defaultHttpClient(config);
        } else {
            client = merchantHttpClient(config, merchantInfo);
        }
        ClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
        return new BufferingClientHttpRequestFactory(factory);
    }
}
