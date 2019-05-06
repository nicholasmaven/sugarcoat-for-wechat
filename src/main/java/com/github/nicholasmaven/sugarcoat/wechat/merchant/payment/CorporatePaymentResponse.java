package com.leiniao.wxtv.wechat.proxy.merchant.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

/**
 * description: 企业付款结果
 *
 * @author zhangjw
 * create at 2019-03-25 16:16
 * @since 1.0.0
 **/
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CorporatePaymentResponse {
    @JsonSetter("partner_trade_no")
    @JacksonXmlCData
    private String partnerTradeNo;
    /**
     * 企业付款成功，返回的微信付款单号
     */
    @JsonSetter("payment_no")
    @JacksonXmlCData
    private String paymentNo;
    /**
     * 企业付款成功时间，格式为 YYYY-MM-dd HH:mm:ss
     */
    @JsonSetter("payment_time")
    @JacksonXmlCData
    private String paymentTime;
}
