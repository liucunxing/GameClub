package com.base.config;

import com.alibaba.fastjson.JSON;
import com.base.security.ResponseResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String message = e.getMessage();
        if(e instanceof BadCredentialsException){
            message = "用户名或密码错误";
        }
        outputStream.write(JSON.toJSONString(ResponseResult.error(message)).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
