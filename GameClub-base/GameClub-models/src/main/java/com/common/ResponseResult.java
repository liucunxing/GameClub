package com.common;

import lombok.Data;
/*
ResponseResult<T> {private T data;} 这里用于指定data类型。不指定不同data默认是object，需要转换或者写各种类型的data
 */
@Data
public class ResponseResult<T> {
    private Integer code;
    private String msg;
    private T data;
    public ResponseResult(){
        this.code = 200;
    }
    public ResponseResult(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public ResponseResult(Integer code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code,T data){
        this.code = code;
        this.data = data;
    }
    /*
这里前面的T是表示是泛型方法，不加编译报错
     */
    public static <T> ResponseResult<T> success(T data){
        return new ResponseResult(200,data);
    }
    public static <T> ResponseResult<T> success(String msg,T data){
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), msg,data);
    }
    public static <T>ResponseResult<T> error(String msg){
        return new ResponseResult(AppHttpCodeEnum.SERVER_ERROR.getCode(), msg);
    }
    public static <T> ResponseResult<T> error(Integer code,String msg,T data){
        return new ResponseResult(code,msg,data);
    }
    public static <T> ResponseResult<T> error(Integer code,String msg){
        return new ResponseResult(code,msg,null);
    }


    public static ResponseResult errorResult(AppHttpCodeEnum enums, String msg) {
        return new ResponseResult(enums.getCode(), msg);
    }
    public static ResponseResult errorResult(AppHttpCodeEnum enums) {
        return new ResponseResult(enums.getCode(),enums.getErrorMessage());
    }

    public static ResponseResult success(int value, String str) {
        return new ResponseResult(value,str);
    }
}
