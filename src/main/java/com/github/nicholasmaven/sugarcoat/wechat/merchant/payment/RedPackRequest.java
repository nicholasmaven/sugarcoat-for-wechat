package com.leiniao.wxtv.wechat.proxy.merchant.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RedPackRequest {

    /**
     * 商户订单号，必须唯一，组成： mch_id+yyyymmdd+10位一天内不能重复的数字
     */
    @JsonProperty("mch_billno")
    @JacksonXmlCData
    private String mchBillNo;

    /**
     * 公众账号appid
     */
    @JsonProperty("wxappid")
    @JacksonXmlCData
    private String appId;

    /**
     * 公众账号appid
     */
    @JsonProperty("mch_id")
    @JacksonXmlCData
    private String mchId;

    /**
     * 商户名称
     */
    @JsonProperty("send_name")
    @JacksonXmlCData
    private String sendName;

    /**
     * 接收红包的种子用户（首个用户）用户在wxappid下的openid
     */
    @JsonProperty("re_openid")
    @JacksonXmlCData
    private String reOpenId;

    /**
     * 红包发放总金额，即一组红包金额总和，包括分享者的红包和裂变的红包，单位分
     */
    @JsonProperty("total_amount")
    @JacksonXmlCData
    private Integer totalAmount;

    /**
     * 红包发放总人数，即总共有多少人可以领到该组红包（包括分享者）
     */
    @JsonProperty("total_num")
    @JacksonXmlCData
    private Integer totalNum;

    /**
     * 红包祝福语
     */
    @JacksonXmlCData
    private String wishing;

    /**
     * 活动名称
     */
    @JsonProperty("act_name")
    @JacksonXmlCData
    private String actName;

    /**
     * 备注
     */
    @JacksonXmlCData
    private String remark;

    /**
     * 调用接口的机器Ip地址
     */
    @JsonProperty("client_ip")
    @JacksonXmlCData
    private String clientIp;
}
