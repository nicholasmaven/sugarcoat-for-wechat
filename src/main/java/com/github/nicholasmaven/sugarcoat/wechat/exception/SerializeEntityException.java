package com.github.nicholasmaven.sugarcoat.wechat.exception;

/**
 * @author mawen
 * @date 2019-02-18 15:54
 */
public class SerializeEntityException extends RuntimeException{
    private String msg;

    public SerializeEntityException(Exception e) {
        super(e);
    }

    public SerializeEntityException(String msg, Exception e) {
        super(e);
        this.msg = msg;
    }
}
