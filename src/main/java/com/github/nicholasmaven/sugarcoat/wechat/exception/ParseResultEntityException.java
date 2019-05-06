package com.github.nicholasmaven.sugarcoat.wechat.exception;

/**
 * @author mawen
 * @date 2019-02-18 15:54
 */
public class ParseResultEntityException extends RuntimeException{
    private String msg;

    public ParseResultEntityException(String msg, Exception e) {
        super(e);
        this.msg = msg;
    }

    public ParseResultEntityException(Exception e) {
        super(e);
    }
}
