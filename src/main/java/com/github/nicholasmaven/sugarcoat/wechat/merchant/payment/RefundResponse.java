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
 * @date 2019-02-25 10:52
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4">applyRefund response</a>
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundResponse {
    @JsonProperty("transaction_id")
    @JacksonXmlCData
    private String transactionId;

    @JsonProperty("out_trade_no")
    @JacksonXmlCData
    private String outTradeNo;

    @JsonProperty("out_refund_no")
    @JacksonXmlCData
    private String outRefundNo;

    @JsonProperty("refund_id")
    @JacksonXmlCData
    private String refundId;

    @JsonProperty("total_fee")
    @JacksonXmlCData
    private Integer totalFee;

    @JsonProperty("refund_fee")
    @JacksonXmlCData
    private Integer refundFee;

    @JsonProperty("cash_fee")
    @JacksonXmlCData
    private Integer cashFee;
}
