package com.common;


public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),
    SERVER_ERROR(500,"服务器内部错误");
    int code;
    String errorMessage;
    AppHttpCodeEnum(int code,String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }
    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
