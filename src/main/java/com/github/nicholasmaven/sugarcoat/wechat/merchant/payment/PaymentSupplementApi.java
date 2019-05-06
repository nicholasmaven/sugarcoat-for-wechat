package com.github.nicholasmaven.sugarcoat.wechat.merchant.payment;

import com.github.nicholasmaven.sugarcoat.wechat.MerchantApi;
import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.WechatException;
import com.github.nicholasmaven.sugarcoat.wechat.merchant.MerchantApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.merchant.MerchantInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author zhangjw
 * description: 微信支付工具Api
 * with at 2018-12-24 17:07
 * @since 1.0.0
 **/
@Slf4j
@Service
public class PaymentSupplementApi extends MerchantApi {
    private static final String SEND_RED_PACK_INTERFACE = "/mmpaymkttransfers/sendredpack";
    private static final String CORPORATE_PAYMENT_INTERFACE = "/mmpaymkttransfers/promotion" +
            "/transfers";

    /**
     * 微信红包最小金额0.3元, 公众号默认最小值是1元
     */
    private static final int MIN_PRICE_CENT = 30;
    /**
     * 微信普通红包最大金额200元
     */
    private static final int NORMAL_MAX_PRICE_CENT = 20000;

    private MerchantApiContext<com.leiniao.wxtv.wechat.proxy.merchant.payment.RedPackResponse> redPackCtx;
    private MerchantApiContext<com.leiniao.wxtv.wechat.proxy.merchant.payment.CorporatePaymentResponse> corporatePasymentCtx;


    public PaymentSupplementApi() {
        redPackCtx = new MerchantApiContext<>(SEND_RED_PACK_INTERFACE, HttpMethod.POST,
                com.leiniao.wxtv.wechat.proxy.merchant.payment.RedPackResponse.class);
        corporatePasymentCtx = new MerchantApiContext<>(CORPORATE_PAYMENT_INTERFACE,
                HttpMethod.POST,
                com.leiniao.wxtv.wechat.proxy.merchant.payment.CorporatePaymentResponse.class);
    }

    /**
     * 发送红包
     *
     * @param merchantInfo 微信商户信息
     * @param request      红包
     * @return 红包订单的微信单号
     * @see "https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_4&index=3"
     */
    public com.leiniao.wxtv.wechat.proxy.merchant.payment.RedPackResponse sendRedPack(com.leiniao.wxtv.wechat.proxy.merchant.payment.RedPackRequest request, MerchantInfo merchantInfo)
            throws HttpStatusNotOkException, ThirdPartyBusinessException {
        if (request.getTotalAmount() < MIN_PRICE_CENT) {
            throw new WechatException("红包最小金额0.3元");
        }
        if (request.getTotalAmount() > NORMAL_MAX_PRICE_CENT) {
            throw new WechatException("正常类型红包金额最大不能超过200元");
        }
        if (null == request.getTotalNum()) {
            request.setTotalNum(1);
        }
        if (StringUtils.isEmpty(request.getSendName())) {
            request.setSendName(merchantInfo.getMchName());
        }
        if (StringUtils.isEmpty(request.getAppId())) {
            request.setAppId(merchantInfo.getAppId());
        }
        if (StringUtils.isEmpty(request.getMchId())) {
            request.setMchId(merchantInfo.getMchId());
        }
        return execute(redPackCtx, merchantInfo, request, false, false);
    }

    private com.leiniao.wxtv.wechat.proxy.merchant.payment.CorporatePaymentResponse corporatePay(com.leiniao.wxtv.wechat.proxy.merchant.payment.CorporatePaymentRequest request, MerchantInfo merchantInfo)
            throws HttpStatusNotOkException, ThirdPartyBusinessException {
        if (StringUtils.isEmpty(request.getAppId())) {
            request.setAppId(merchantInfo.getAppId());
        }
        if (StringUtils.isEmpty(request.getMchId())) {
            request.setMchId(merchantInfo.getMchId());
        }
        return execute(corporatePasymentCtx, merchantInfo, request, false, false);
    }

    public com.leiniao.wxtv.wechat.proxy.merchant.payment.CorporatePaymentResponse corporatePayToTrueName(com.leiniao.wxtv.wechat.proxy.merchant.payment.CorporatePaymentRequest request, MerchantInfo merchantInfo)
            throws HttpStatusNotOkException, ThirdPartyBusinessException {
        request.setCheckName("FORCE_CHECK");
        Assert.hasText(request.getReUserName(), "receiving user name is null");
        return corporatePay(request, merchantInfo);
    }

    public com.leiniao.wxtv.wechat.proxy.merchant.payment.CorporatePaymentResponse corporatePayToAnonymous(com.leiniao.wxtv.wechat.proxy.merchant.payment.CorporatePaymentRequest request, MerchantInfo merchantInfo)
            throws HttpStatusNotOkException, ThirdPartyBusinessException {
        request.setCheckName("NO_CHECK");
        Assert.hasText(request.getReUserName(), "receiving user name is null");
        return corporatePay(request, merchantInfo);
    }
}
