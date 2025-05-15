package learn.qzy.searchbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文章评论表
 * @TableName article_comment
 */
@TableName(value ="article_comment")
@Data
public class ArticleComment implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章ID
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 楼层号
     */
    @TableField(value = "serial_number")
    private Integer serialNumber;

    /**
     * 父级评论用户名
     */
    @TableField(value = "parent_username")
    private String parentUsername;

    /**
     * 父级评论用户昵称
     */
    @TableField(value = "parent_nickname")
    private String parentNickname;

    /**
     * 当前评论用户名
     */
    @TableField(value = "current_username")
    private String currentUsername;

    /**
     * 当前评论用户昵称
     */
    @TableField(value = "current_nickname")
    private String currentNickname;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间—评论时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 修改人
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 逻辑删除（0否；1是）
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

}