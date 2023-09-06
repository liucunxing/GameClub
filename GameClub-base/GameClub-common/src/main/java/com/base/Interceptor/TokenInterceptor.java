package com.base.Interceptor;

import com.base.exception.MyLoginException;
import com.base.myInterface.Authorize;
import com.base.myInterface.NoAuthorize;
import com.base.utils.common.JwtUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws MyLoginException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 检查方法级别的 @NoAuthorize 注解
            if (handlerMethod.getMethod().isAnnotationPresent(NoAuthorize.class)) {
                // 方法上存在 @NoAuthorize 注解，允许请求继续执行
                return true;
            }

            // 检查类级别的注解
            Class<?> targetClass = handlerMethod.getBeanType();
            if (targetClass.isAnnotationPresent(Authorize.class)) {
                // 执行类级别的授权逻辑，如果有的话
                Authorize classAnnotation = targetClass.getAnnotation(Authorize.class);
                if (classAnnotation != null) {
                    // 在这里添加类级别授权逻辑
                    return checkAuth(request);
                }

                // 检查方法上是否有Authorized注解
                Method method = handlerMethod.getMethod();
                if (method.isAnnotationPresent(Authorize.class)) {
                    return checkAuth(request);
                }
            }
        }
        return true;
    }
    private boolean checkAuth(HttpServletRequest request) throws MyLoginException{
        Boolean res = JwtUtils.checkToken(request);
        if (res == false) {
            throw new MyLoginException("未授权的用户认证");
        }
        return res;
    }
}

