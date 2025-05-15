package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.entity.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.model.vo.ArticleCommentVO;
import learn.qzy.searchbackend.util.Result;

import java.util.List;

/**
* @author Administrator
* @description 针对表【article_comment(文章评论表)】的数据库操作Service
* @createDate 2025-04-06 13:38:41
*/
public interface ArticleCommentService extends IService<ArticleComment> {

    Result<List<ArticleCommentVO>> getListAll();
}
