package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import learn.qzy.searchbackend.model.entity.ArticleComment;
import learn.qzy.searchbackend.model.vo.ArticleCommentVO;
import learn.qzy.searchbackend.service.ArticleCommentService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zhiyun Qin
 * @email qinzhiyun1027@163.com
 * @create 2025/05/15  7:54  星期四
 * @title 文章评论管理接口
 */
@RestController
@RequestMapping("/comment")
public class ArticleCommentController {
    @Autowired
    private ArticleCommentService commentService;

    /**
     * 查询所有评论
     * @return 评论列表
     */
    @GetMapping
    public Result<List<ArticleCommentVO>> list() {
        return ResultGenerator.genSuccessResult(commentService.getListAll());
    }

    /**
     * 删除评论
     * @param id 评论id
     * @return 删除结果
     */
    @SaCheckLogin
    @DeleteMapping
    public Result<String> deleteComment(@RequestParam Long id) {
        return commentService.removeById(id) ? ResultGenerator.genSuccessResult("删除成功") : ResultGenerator.genFailResult("删除失败");
    }
}
