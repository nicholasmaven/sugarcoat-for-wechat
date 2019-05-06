package com.github.nicholasmaven.sugarcoat.wechat.exception;

/**
 * represents http status which not equals 200
 *
 * @author mawen
 * @date 2019-01-31 9:30
 */
public class ThirdPartyBusinessException extends Exception {
    private String errCode;
    private String errMsg;

    public ThirdPartyBusinessException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
}
