package com.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.base.utils.common.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 自定义数据对象处理器
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("新增操作执行");
        metaObject.setValue("createUser",getUser());
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateUser",getUser());
        metaObject.setValue("updateTime", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("修改操作执行");
        metaObject.setValue("createUser",getUser());
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateUser",getUser());
        metaObject.setValue("updateTime", new Date());
    }
    private String getUser(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null){
            HttpServletRequest request = servletRequestAttributes.getRequest();
            Claims claims = JwtUtils.parseJwt(request);
            return String.valueOf(claims.get("userId"));
        }
        return null;
    }
}
