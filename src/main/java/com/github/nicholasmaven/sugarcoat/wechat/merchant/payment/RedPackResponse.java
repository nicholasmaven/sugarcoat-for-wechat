package com.leiniao.wxtv.wechat.proxy.merchant.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangjw
 * description: 商户红包
 * with at 2018-12-21 19:55
 * @since 1.0.0
 **/
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedPackResponse {
    /**
     * 商户订单号，必须唯一，组成： mch_id+yyyymmdd+10位一天内不能重复的数字
     */
    @JsonSetter("mch_billno")
    @JacksonXmlCData
    private String mchBillNo;

    /**
     * 接受收红包的用户
     */
    @JsonSetter("re_openid")
    @JacksonXmlCData
    private String reOpenId;

    /**
     * 付款金额，单位分
     */
    @JsonSetter("total_amount")
    @JacksonXmlCData
    private Integer totalAmount;

    /**
     * 红包订单的微信单号
     */
    @JsonSetter("send_listid")
    @JacksonXmlCData
    private String sendListId;
}
