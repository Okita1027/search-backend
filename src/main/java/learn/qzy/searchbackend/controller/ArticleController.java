package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.vo.ArticleDetailVO;
import learn.qzy.searchbackend.model.vo.ContentArticleVO;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qzy
 * @create 2024/12/12 15:28 星期四
 * @title 文章接口
 */
@RestController
@RequestMapping("/post")
public class ArticleController {

    @Resource
    private ContentArticleService articleService;

    /**
     * 新增文章
     * @param articleVO 文章信息
     * @return 文章id
     */
    @SaCheckLogin
    @PostMapping
    public Result<Long> addArticle(@RequestBody ContentArticleVO articleVO) {
        return articleService.addArticle(articleVO);
    }

    /**
     * 更新文章
     * @param article 文章信息
     * @return 更新结果
     */
    @SaCheckLogin
    @PutMapping
    public Result<String> updateArticle(@RequestBody ContentArticle article) {
        return articleService.updateArticle(article);
    }

    /**
     * 删除文章
     * @param id 文章id
     * @return 删除结果
     */
    @SaCheckLogin
    @DeleteMapping
    public Result<String> deleteArticle(@RequestParam Long id) {
        return articleService.deleteArticle(id);
    }

    /**
     * 获取搜索候选项
     * @param suggestText 文章标题
     */
    @GetMapping("/suggestion")
    public Result<String> getSuggestion(@RequestParam String suggestText) {
        return articleService.getSuggestion(suggestText);
    }

    /**
     * 获取文章列表（搜索页）
     * @param text 文章标题
     * @return 文章列表
     */
    @GetMapping
    public Result<ContentArticleVO> getArticleList(@RequestParam String text) {
        if (StrUtil.isEmpty(text)) {
            return ResultGenerator.genFailResult("搜索关键词不能为空");
        }
        return articleService.getArticleList(text);
    }

    /**
     * 获取文章列表（后台管理系统）
     * @return 文章列表
     */
    @GetMapping("/list")
    public Result<List<ContentArticle>> getArticleList() {
        return articleService.getArticleListAll();
    }

    /**
     * 获取文章详情
     * @param text 文章标题
     * @return ArticleDetailVO
     */
    @GetMapping("/detail")
    public Result<ArticleDetailVO> getArticleDetail(@RequestParam String text) {
        return articleService.getArticleDetail(text);
    }

}
