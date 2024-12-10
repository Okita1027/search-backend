package learn.qzy.searchbackend.controller;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.exception.ThrowUtils;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.service.ContentPictureService;
import learn.qzy.searchbackend.service.ContentUserService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private ContentPictureService pictureService;
    @Resource
    private ContentArticleService articleService;
    @Resource
    private ContentUserService userService;
/*    @PostMapping
    public Result search(@RequestParam String type, @RequestParam String text) {
        if ("post".equals(type)) {
            Result<ContentPicture> articleList = articleService.getArticleList(text);
            return ResultGenerator.genSuccessResult(articleList);
        } else if ("user".equals(type)) {
            Result<ContentPicture> userList = userService.getUserList(text);
            return ResultGenerator.genSuccessResult(userList);
        } else if ("picture".equals(type)) {
            Result<ContentPicture> pictureList = pictureService.getPictureList(text);
            return ResultGenerator.genSuccessResult(pictureList);
        }
        throw new RuntimeException("参数错误");
    }*/
}
