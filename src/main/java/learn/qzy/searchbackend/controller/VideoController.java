package learn.qzy.searchbackend.controller;

import cn.hutool.core.util.StrUtil;
import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.model.vo.ContentVideoVO;
import learn.qzy.searchbackend.service.ContentVideoService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qzy
 * @time 2025年3月27日 21:13 星期四
 * @title （视频）控制类
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private ContentVideoService videoService;

    @GetMapping()
    public Result<ContentVideoVO> video(@RequestParam("text") String fileName) {
        if (StrUtil.isEmpty(fileName)) {
            return ResultGenerator.genFailResult("请输入搜索内容");
        }
        return videoService.getVideoList(fileName);
    }
}
