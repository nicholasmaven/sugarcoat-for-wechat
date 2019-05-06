package com.github.nicholasmaven.sugarcoat.wechat.merchant.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mawen
 * @date 2019-02-19 13:48
 * @see
 * <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">unified order request</a>
 */
@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class PlaceOrderRequest {
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("total_fee")
    private Integer totalFee;

    private String body;

    @JsonProperty("spbill_create_ip")
    private String sourceIp;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("trade_type")
    private String tradeType;

    // Below fields are not always necessary, reserved for future or conditional scenarios
    private String detail;

    // required when in JSAPI mode
    @JsonProperty("openid")
    private String openId;

    private String attach;

    @JsonProperty("product_id")
    private String productId;

    private String receipt;

    @JsonProperty("sign_type")
    private String signType;

    @JsonProperty("device_info")
    private String deviceInfo;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("time_start")
    private String timeStart;

    @JsonProperty("time_expire")
    private String timeExpire;

    @JsonProperty("goods_tag")
    private String goodsTag;

    @JsonProperty("limit_pay")
    private String limitPay;

    @JsonProperty("scene_info")
    private String sceneInfo;
}
