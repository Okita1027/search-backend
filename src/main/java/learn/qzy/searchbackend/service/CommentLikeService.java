package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.entity.CommentLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【comment_like(评论点赞表)】的数据库操作Service
* @createDate 2025-04-06 13:38:41
*/
public interface CommentLikeService extends IService<CommentLike> {

    Long getLikeCountByCommentId(Long id);
}
