package com.leiniao.wxtv.wechat.proxy.merchant.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

/**
 * description: 企业付款请求
 *
 * @author zhangjw
 * create at 2019-03-25 16:09
 * @since 1.0.0
 **/
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorporatePaymentRequest {
    /**
     * 申请商户号的appid或商户号绑定的appid
     */
    @JsonProperty("mch_appid")
    @JacksonXmlCData
    private String appId;
    /**
     * 微信支付分配的商户号
     */
    @JsonProperty("mchid")
    @JacksonXmlCData
    private String mchId;
    /**
     * 微信支付分配的终端设备号
     */
    @JsonProperty("device_info")
    @JacksonXmlCData
    private String deviceInfo;
    /**
     * 商户订单号，需保持唯一性
     * (只能是字母或者数字，不能包含有其他字符)
     */
    @JsonProperty("partner_trade_no")
    @JacksonXmlCData
    private String partnerTradeNo;
    /**
     * 商户appid下，某用户的openid
     */
    @JsonProperty("openid")
    @JacksonXmlCData
    private String openId;
    /**
     * 校验用户姓名选项，NO_CHECK：不校验真实姓名,FORCE_CHECK：强校验真实姓名
     */
    @JsonProperty("check_name")
    @JacksonXmlCData
    private String checkName;
    /**
     * 收款用户姓名，如果check_name设置为FORCE_CHECK，则必填用户真实姓名
     */
    @JsonProperty("re_user_name")
    @JacksonXmlCData
    private String reUserName;
    /**
     * 企业付款金额，单位为分
     */
    @JacksonXmlCData
    private Integer amount;
    /**
     * 企业付款备注
     */
    @JacksonXmlCData
    private String desc;
    /**
     * 客户端ip
     */
    @JsonProperty("spbill_create_ip")
    @JacksonXmlCData
    private String clientIp;
}
