package com.base.config;

import com.alibaba.fastjson.JSON;
import com.base.security.ResponseResult;
import com.base.utils.common.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            ResponseResult res = new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "用户未登录");
            String json = JSON.toJSONString(res);
            WebUtils.renderString(response,json);
    }
}
