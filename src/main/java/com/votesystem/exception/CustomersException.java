package com.votesystem.exception;

/**
 * @author Ricardo
 * 自定义异常处理机制
 */

public class CustomersException extends RuntimeException {
    private Integer code;
    private String msg;

    public CustomersException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
