package learn.qzy.searchbackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 评论点赞表
 * @TableName comment_like
 */
@TableName(value ="comment_like")
@Data
public class CommentLike implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论ID
     */
    @TableField(value = "comment_id")
    private Long commentId;

    /**
     * 点赞数量
     */
    @TableField(value = "like_count")
    private Long likeCount;

    /**
     * 创建时间
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
     * 逻辑删除（0-未删，1-已删）
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

}