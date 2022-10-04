package com.hspro.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hspro.enity.BaseContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * @author: devhspro@gmail.com
 * @Date: 2022/9/30
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createUser", BaseContext.getId());
        metaObject.setValue("updateUser", BaseContext.getId());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateUser", BaseContext.getId());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
