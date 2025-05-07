package learn.qzy.searchbackend.model.entity;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import learn.qzy.searchbackend.model.dto.BaseUserDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @TableName content_user
 */
@TableName(value = "content_user")
@Getter
@Setter
public class ContentUser extends BaseUserDTO implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别 （0保密；1男性；2女性）
     */
    private Integer gender;

    /**
     * 个性签名
     */
    private String profile;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 点赞过的评论ID
     */
    @TableField(value = "favor_comment", typeHandler = JacksonTypeHandler.class)
    private String favorComment;

    /**
     * 状态(0停用；1启用)
     */
    private Integer status;

    /**
     * 用户编辑时间
     */
    @TableField(value = "edit_time", fill = FieldFill.UPDATE)
    private LocalDateTime editTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

}