package com.github.nicholasmaven.sugarcoat.wechat.merchant.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-25 10:52
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4">applyRefund request</a>
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundRequest {
    // Only one of transaction_id, out_trade_no is required
    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("out_refund_no")
    private String outRefundNo;

    @JsonProperty("total_fee")
    private Integer totalFee;

    @JsonProperty("refund_fee")
    private Integer refundFee;

    //================================================
    // Below fields are optional
    //================================================

    @JsonProperty("refund_desc")
    @JacksonXmlCData
    private String refundDesc;

    @JsonProperty("refund_fee_type")
    @JacksonXmlCData
    private String refundFeeType;

    @JsonProperty("refund_account")
    @JacksonXmlCData
    private String refundAccount;

    @JsonProperty("notify_url")
    @JacksonXmlCData
    private String notifyUrl;
}
