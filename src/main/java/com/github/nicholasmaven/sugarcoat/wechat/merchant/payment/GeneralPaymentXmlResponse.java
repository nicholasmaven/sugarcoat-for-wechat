package com.github.nicholasmaven.sugarcoat.wechat.merchant.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @See https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * @date 2019-02-19 13:48
 */
@Setter
@Getter
@JsonRootName("xml")
@JsonInclude(Include.NON_NULL)
public class GeneralPaymentXmlResponse {
    @JsonProperty("return_code")
    @JacksonXmlCData
    private String returnCode;

    @JsonProperty("return_msg")
    @JacksonXmlCData
    private String returnMsg;

    @JsonProperty("result_code")
    @JacksonXmlCData
    private String resultCode;

    @JsonProperty("err_code")
    @JacksonXmlCData
    private String errCode;

    @JsonProperty("err_code_des")
    @JacksonXmlCData
    private String errCodeDes;

    @JsonProperty("appid")
    @JacksonXmlCData
    private String appId;

    @JsonProperty("mch_id")
    @JacksonXmlCData
    private String mchId;

    @JacksonXmlCData
    private String sign;

    @JsonProperty("nonce_str")
    @JacksonXmlCData
    private String nonceStr;
}
