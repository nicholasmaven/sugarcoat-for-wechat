package com.github.nicholasmaven.sugarcoat.wechat.merchant.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Extending of GeneralPaymentXmlResponse is not needed, as this response ignores unknown properties
 *
 * @author mawen
 * @date 2019-02-25 11:02
 * @see
 * <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5">query applyRefund response</a>
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryRefundResponse {
    @JsonProperty("transaction_id")
    @JacksonXmlCData
    private String transactionId;

    @JsonProperty("out_trade_no")
    @JacksonXmlCData
    private String outTradeNo;

    @JsonProperty("refund_id")
    @JacksonXmlCData
    private String refundId;

    @JsonProperty("total_fee")
    private Integer totalFee;

    @JsonProperty("cash_fee")
    private Integer cashFee;

    @JsonProperty("refund_count")
    private Integer refundCount;

    @JsonProperty("details")
    private List<RefundResultDetail> detail;

    //================================================
    // Below fields are optional
    //================================================

    @JsonProperty("settlement_total_fee")
    private Integer settlementTotalFee;

    @JsonProperty("fee_type")
    @JacksonXmlCData
    private String feeType;

    @JsonProperty("total_refund_count")
    private Integer totalRefundCount;

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RefundResultDetail {
        @JsonProperty("out_refund_no")
        @JacksonXmlCData
        private String outRefundNo;

        @JsonProperty("refund_id")
        @JacksonXmlCData
        private String refundId;

        @JsonProperty("refund_status")
        @JacksonXmlCData
        private String refundStatus;

        @JsonProperty("refund_fee")
        private Integer refundFee;

        @JsonProperty("refund_recv_accout")
        @JacksonXmlCData
        private String refundRecvAccout;

        //================================================
        // Below fields are optional
        //================================================

        @JsonProperty("refund_channel")
        @JacksonXmlCData
        private String refundChannel;

        @JsonProperty("settlement_refund_fee")
        private Integer settlementRefundFee;

        @JsonProperty("refund_success_time")
        @JacksonXmlCData
        private String refundSuccessTime;

        @JsonProperty("refund_account")
        @JacksonXmlCData
        private String refundAccount;
    }
}
