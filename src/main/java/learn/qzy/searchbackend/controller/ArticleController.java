package learn.qzy.searchbackend.controller;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.vo.ArticleDetailVO;
import learn.qzy.searchbackend.model.vo.ContentArticleVO;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.util.Result;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public Result<Long> addArticle(@RequestBody ContentArticleVO articleVO) {
        return articleService.addArticle(articleVO);
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
     * 获取文章列表
     * @param text 文章标题
     */
    @GetMapping
    public Result<ContentArticleVO> getArticleList(@RequestParam String text) {
        return articleService.getArticleList(text);
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
