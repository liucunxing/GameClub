package com.base.config;

import com.alibaba.fastjson.JSON;
import com.base.security.ResponseResult;
import com.base.utils.common.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String username = "user";
        Map<String,String> claims = new HashMap<>();
        claims.put("username",username);
        String token = JwtUtils.createJwt(claims);
        Map<String, String> resMap = new HashMap<>();
        resMap.put("token", token);
        outputStream.write(JSON.toJSONString(ResponseResult.success("登录成功",resMap)).getBytes());
        outputStream.flush();
        outputStream.close();

    }
}
