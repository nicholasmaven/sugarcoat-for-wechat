package com.github.nicholasmaven.sugarcoat.wechat.exception;

/**
 * represents http status which not equals 200
 *
 * @author mawen
 * @date 2019-01-31 9:30
 */
public class HttpStatusNotOkException extends Exception {
    private int httpStatusCode;
    private String msg;

    public HttpStatusNotOkException(int httpStatusCode, String msg) {
        super(msg);
        this.httpStatusCode = httpStatusCode;
        this.msg = msg;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
