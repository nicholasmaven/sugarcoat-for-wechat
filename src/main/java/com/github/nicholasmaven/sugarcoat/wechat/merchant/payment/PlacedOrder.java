package com.github.nicholasmaven.sugarcoat.wechat.merchant.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

/**
 * Extending of GeneralPaymentXmlResponse is not needed, as this response ignores unknown properties
 *
 * @author mawen
 * @date 2019-02-19 13:48
 * @see
 * <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">unify order response</a>
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacedOrder {
    @JsonProperty("trade_type")
    @JacksonXmlCData
    private String tradeType;

    @JsonProperty("prepay_id")
    @JacksonXmlCData
    private String prepayId;

    // Only have value when NATIVE pay
    @JsonProperty("code_url")
    @JacksonXmlCData
    private String codeUrl;
}
