package com.base.exception;


import com.common.AppHttpCodeEnum;
import com.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice  //控制器增强类
@Slf4j
public class ExceptionCatch {
    /**
     * 处理不可控异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e){
        e.printStackTrace();
        log.error("catch exception:{}",e.getMessage());
        Date date = new Date();
        return ResponseResult.error("服务器内部发生错误" + date);
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseResult exception(AccessDeniedException e){
        e.printStackTrace();
        log.error("catch exception:{}",e.getMessage());
        return ResponseResult.success(HttpStatus.FORBIDDEN.value(),"您的权限不足");
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseResult exception(BadCredentialsException e){
        e.printStackTrace();
        log.error("catch exception:{}",e.getMessage());
        return ResponseResult.success(HttpStatus.UNAUTHORIZED.value(),"用户信息验证失败，请重新登录");
    }
}
