package learn.qzy.searchbackend.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author qzy
 * @create 2024/12/10 14:02 星期二
 * @title 自定义元数据对象处理器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * @description 在完成插入操作自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("公共字段自动填充【insert】...");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }

    /**
     * @description 在完成更新操作自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("公共字段自动填充【update】...");
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}