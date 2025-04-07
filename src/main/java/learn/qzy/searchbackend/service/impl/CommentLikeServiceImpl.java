package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.CommentLike;
import learn.qzy.searchbackend.service.CommentLikeService;
import learn.qzy.searchbackend.mapper.CommentLikeMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【comment_like(评论点赞表)】的数据库操作Service实现
* @createDate 2025-04-06 13:38:41
*/
@Service
public class CommentLikeServiceImpl extends ServiceImpl<CommentLikeMapper, CommentLike>
    implements CommentLikeService{

    @Override
    public Long getLikeCountByCommentId(Long id) {
        LambdaQueryWrapper<CommentLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentLike::getCommentId, id);
        wrapper.select(CommentLike::getLikeCount);
        CommentLike commentLike = this.getOne(wrapper);
        return commentLike != null ? commentLike.getLikeCount() : 0L;
    }
}




