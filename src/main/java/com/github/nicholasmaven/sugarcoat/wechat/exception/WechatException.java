package com.github.nicholasmaven.sugarcoat.wechat.exception;

/**
 * @author mawen
 * @date 2019-02-18 15:54
 */
public class WechatException extends RuntimeException {
    private String msg;

    public WechatException(Exception e) {
        super(e);
    }

    public WechatException(String msg) {
        super();
        this.msg = msg;
    }
}
