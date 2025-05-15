package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.ArticleComment;
import learn.qzy.searchbackend.model.vo.ArticleCommentVO;
import learn.qzy.searchbackend.service.ArticleCommentService;
import learn.qzy.searchbackend.mapper.ArticleCommentMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【article_comment(文章评论表)】的数据库操作Service实现
* @createDate 2025-04-06 13:38:41
*/
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment>
    implements ArticleCommentService{

    @Autowired
    private ArticleCommentMapper commentMapper;

    @Override
    public Result<List<ArticleCommentVO>> getListAll() {
        return ResultGenerator.genSuccessResult(commentMapper.selectListAll());
    }
}




