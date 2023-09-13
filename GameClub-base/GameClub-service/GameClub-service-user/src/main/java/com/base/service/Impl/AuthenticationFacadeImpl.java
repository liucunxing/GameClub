package com.base.service.Impl;

import com.base.service.AuthenticationFacade;
import com.base.utils.common.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    private final JwtUtils jwtUtils;

    public AuthenticationFacadeImpl(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Claims getUserClaims() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Claims claims = JwtUtils.parseJwt(request);
        return claims;
    }

}
