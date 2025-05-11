package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.StrUtil;
import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.model.vo.ContentVideoVO;
import learn.qzy.searchbackend.service.ContentVideoService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 获取视频列表(搜索页)
     * @param fileName 搜索关键词
     * @return fileName、filePath
     */
    @GetMapping
    public Result<ContentVideoVO> video(@RequestParam("text") String fileName) {
        if (StrUtil.isEmpty(fileName)) {
            return ResultGenerator.genFailResult("请输入搜索内容");
        }
        return videoService.getVideoList(fileName);
    }

    /**
     * 获取视频列表(后台管理)
     * @return 视频完整字段
     */
    @GetMapping("/list")
    public Result<List<ContentVideoVO>> videoList() {
        return videoService.getVideoListAll();
    }

    /**
     * 删除视频
     * @param fileName 视频的文件名
     * @return 删除结果
     */
    @SaCheckLogin
    @DeleteMapping
    public Result<?> deleteVideo(@RequestParam String fileName) {
        return videoService.deleteVideo(fileName);
    }
}
