package com.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

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
        metaObject.setValue("createUser","liu");
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateUser","liu");
        metaObject.setValue("updateTime", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("修改操作执行");
        metaObject.setValue("createUser","liu");
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateUser","liu");
        metaObject.setValue("updateTime", new Date());
    }
}
