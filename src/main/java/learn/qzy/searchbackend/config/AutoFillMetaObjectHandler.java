package learn.qzy.searchbackend.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author qzy
 * @create 2024/12/10 14:02 星期二
 * @title 自定义元数据对象处理器
 */
@Component
public class AutoFillMetaObjectHandler implements MetaObjectHandler {

    Logger logger = LoggerFactory.getLogger(AutoFillMetaObjectHandler.class);

    /**
     * @param metaObject 元数据对象
     * @description 在完成插入操作自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        logger.info("公共字段自动填充【insert】...");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("isDeleted", 0);
        if (metaObject.hasGetter("createBy")) {
            if (StpUtil.isLogin()) {
                metaObject.setValue("createBy", StpUtil.getLoginIdAsString());
            } else {
                metaObject.setValue("createBy", "1");
            }
        }
    }

    /**
     * @param metaObject 元数据对象
     * @description 在完成更新操作自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        logger.info("公共字段自动填充【update】...");
        metaObject.setValue("updateTime", LocalDateTime.now());
        if (metaObject.hasGetter("editTime")) {
            metaObject.setValue("editTime", LocalDateTime.now());
        }
        if (metaObject.hasGetter("updateBy")) {
            metaObject.setValue("updateBy", StpUtil.getLoginIdAsString());
        }
    }
}