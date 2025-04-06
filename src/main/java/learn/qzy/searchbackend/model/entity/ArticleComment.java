package learn.qzy.searchbackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
    /**
     * ID主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级评论用户ID（content_user）
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 文章ID
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 当前评论用户ID（content_user）
     */
    @TableField(value = "user_id")
    private Long userId;

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
     * 创建人—评论用户昵称
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 修改人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 逻辑删除（0否；1是）
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}