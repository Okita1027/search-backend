package learn.qzy.searchbackend.controller;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.vo.ContentPictureVO;
import learn.qzy.searchbackend.service.ContentPictureService;
import learn.qzy.searchbackend.util.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author qzy
 * @create 2024/12/12 15:28 星期四
 * @title
 */
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private ContentPictureService pictureService;

    @GetMapping
    public Result<ContentPictureVO> getPictureList(@RequestParam String text) {
        return pictureService.getPictureList(text);
    }

}
