package com.github.nicholasmaven.sugarcoat.wechat.merchant.payment;

import com.github.nicholasmaven.sugarcoat.wechat.exception.HttpStatusNotOkException;
import com.github.nicholasmaven.sugarcoat.wechat.exception.ThirdPartyBusinessException;
import com.github.nicholasmaven.sugarcoat.wechat.merchant.MerchantApiContext;
import com.github.nicholasmaven.sugarcoat.wechat.merchant.MerchantInfo;
import com.github.nicholasmaven.sugarcoat.wechat.DeserializeFeature;
import com.github.nicholasmaven.sugarcoat.wechat.MerchantApi;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mawen
 * @date 2019-02-19 13:45
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1">unify order</a>
 * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2">query order</a>
 */
@Service
public class PaymentApi extends MerchantApi {
    private static final String PLACE_ORDER_INTERFACE = "/pay/unifiedorder";
    private static final String QUERY_ORDER_INTERFACE = "/pay/orderquery";
    private static final String REFUND_INTERFACE = "/secapi/pay/refund";
    private static final String QUERY_REFUND_INTERFACE = "/pay/refundquery";

    private MerchantApiContext<PlacedOrder> placeOrderCtx;
    private MerchantApiContext<QueryOrderResponse> queryOrderCtx;
    private MerchantApiContext<RefundResponse> refundCtx;
    private MerchantApiContext<QueryRefundResponse> queryRefundCtx;

    public PaymentApi() {
        placeOrderCtx = new MerchantApiContext<>(PLACE_ORDER_INTERFACE, HttpMethod.POST,
                PlacedOrder.class);
        queryOrderCtx = new MerchantApiContext<>(QUERY_ORDER_INTERFACE, HttpMethod.POST,
                QueryOrderResponse.class);
        refundCtx = new MerchantApiContext<>(REFUND_INTERFACE, HttpMethod.POST,
                RefundResponse.class);
        queryRefundCtx = new MerchantApiContext<>(QUERY_REFUND_INTERFACE, HttpMethod.POST,
                QueryRefundResponse.class);
    }

    public PlacedOrder placeOrder(PlaceOrderRequest orderRequest, MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return execute(placeOrderCtx, merchantInfo, orderRequest);
    }

    public QueryOrderResponse queryOrderbyTransactionId(String txId, MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return queryOrder(txId, IdType.TRANSACTION_ID, merchantInfo);
    }

    public QueryOrderResponse queryOrderByOutTradeNo(String outTradeNo,
                                                     MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return queryOrder(outTradeNo, IdType.OUT_TRADE_NO, merchantInfo);
    }

    private QueryOrderResponse queryOrder(String id,
                                          IdType type,
                                          MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        if (type == IdType.OUT_REFUND_NO || type == IdType.REFUND_ID) {
            throw new IllegalArgumentException("Id type invalid! Only allow out_trade_no and " +
                    "transaction_id");
        }
        Map<String, String> map = new HashMap<>();
        map.put(type.getName(), id);
        return execute(queryOrderCtx, merchantInfo, map,
                resolverOf(DeserializeFeature.listCanBeFolded("coupons")), true, true);
    }

    public RefundResponse applyRefund(RefundRequest refundRequest,
                                      MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return execute(refundCtx, merchantInfo, refundRequest);
    }

    /**
     * Partial applyRefund offset is not supported, and also coupon applyRefund
     */
    public QueryRefundResponse queryRefundByRefundId(String refundId,
                                                     MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return queryRefund(refundId, IdType.REFUND_ID, merchantInfo);
    }

    /**
     * Partial applyRefund offset is not supported, and also coupon applyRefund
     */
    public QueryRefundResponse queryRefundByOutRefundNo(String outRefundNo,
                                                        MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return queryRefund(outRefundNo, IdType.OUT_REFUND_NO, merchantInfo);
    }

    /**
     * Partial applyRefund offset is not supported, and also coupon applyRefund
     */
    public QueryRefundResponse queryRefundByTransactionId(String txId,
                                                          MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return queryRefund(txId, IdType.TRANSACTION_ID, merchantInfo);
    }

    /**
     * Partial applyRefund offset is not supported, and also coupon applyRefund
     */
    public QueryRefundResponse queryRefundByOutTradeNo(String txId,
                                                       MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        return queryRefund(txId, IdType.OUT_TRADE_NO, merchantInfo);
    }

    private QueryRefundResponse queryRefund(String id,
                                            IdType type,
                                            MerchantInfo merchantInfo) throws
            HttpStatusNotOkException, ThirdPartyBusinessException {
        Assert.hasText(id, "id is null or empty");
        Assert.notNull(merchantInfo, "merchantInfo is null or empty");
        Map<String, String> map = new HashMap<>();
        map.put(type.getName(), id);
        return execute(queryRefundCtx, merchantInfo, map,
                resolverOf(DeserializeFeature.listCanBeFolded("details")), true, true);
    }

    private enum IdType {
        REFUND_ID("refund_id"),
        TRANSACTION_ID("transaction_id"),
        OUT_TRADE_NO("out_trade_no"),
        OUT_REFUND_NO("out_refund_no");

        private String name;

        IdType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
