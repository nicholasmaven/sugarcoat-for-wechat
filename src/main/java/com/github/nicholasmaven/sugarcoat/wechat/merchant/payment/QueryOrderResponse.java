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
 * @date 2019-02-22 17:30
 * @see
 * <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2">query order response</a>
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryOrderResponse {
    @JsonProperty("openid")
    @JacksonXmlCData
    private String openId;

    @JsonProperty("is_subscribe")
    @JacksonXmlCData
    private String isSubscribe;

    @JsonProperty("trade_type")
    @JacksonXmlCData
    private String tradeType;

    @JsonProperty("trade_state")
    @JacksonXmlCData
    private String tradeState;

    @JsonProperty("trade_state_desc")
    @JacksonXmlCData
    private String tradeStateDesc;

    @JsonProperty("bank_type")
    @JacksonXmlCData
    private String bankType;

    @JsonProperty("total_fee")
    private Integer totalFee;

    @JsonProperty("cash_fee")
    private Integer cashFee;

    @JsonProperty("transaction_id")
    @JacksonXmlCData
    private String transactionId;

    @JsonProperty("out_trade_no")
    @JacksonXmlCData
    private String outTradeNo;

    @JsonProperty("time_end")
    @JacksonXmlCData
    private String timeEnd;

    // Below fields are optional
    @JsonProperty("device_info")
    @JacksonXmlCData
    private String deviceInfo;

    @JsonProperty("settlement_total_fee")
    private Integer settlementTotalFee;

    @JsonProperty("fee_type")
    @JacksonXmlCData
    private String feeType;

    @JsonProperty("cash_fee_type")
    @JacksonXmlCData
    private String cashFeeType;

    @JacksonXmlCData
    private String attach;

    @JsonProperty("coupon_count")
    private Integer couponCount;

    @JsonProperty("coupon_fee")
    private Integer couponFee;

    @JsonProperty("coupons")
    private List<Coupon> coupons;

    @Setter
    @Getter
    public static class Coupon {
        @JsonProperty("coupon_id")
        @JacksonXmlCData
        private String couponId;

        @JsonProperty("coupon_fee")
        @JacksonXmlCData
        private Integer couponFee;

        @JsonProperty("coupon_type")
        @JacksonXmlCData
        private String couponType;
    }
}
