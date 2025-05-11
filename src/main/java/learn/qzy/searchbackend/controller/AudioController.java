package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.StrUtil;
import learn.qzy.searchbackend.model.entity.ContentAudio;
import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.service.ContentAudioService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author qzy
 * @time 2025年3月27日 21:13 星期四
 * @title （音频）控制类
 */
@RestController
@RequestMapping("/audio")
public class AudioController {

    @Autowired
    private ContentAudioService fileService;

    /**
     * 获取音频列表(搜索页面)
     * @param fileName 搜索关键词
     * @return fileName、filePath
     */
    @GetMapping
    public Result<ContentAudioVO> audio(@RequestParam("text") String fileName) {
        if (StrUtil.isEmpty(fileName)) {
            return ResultGenerator.genFailResult("请输入搜索内容");
        }
        return fileService.getAudioList(fileName);
    }

    /**
     * 获取音频列表(管理页面)
     * @return 音频完整信息
     */
    @GetMapping("/list")
    public Result<List<ContentAudio>> audioList() {
        return fileService.getAudioListAll();
    }

    /**
     * 删除音频
     * @param fileName 音频的文件名
     * @return 删除结果
     */
    @SaCheckLogin
    @DeleteMapping
    public Result<String> deleteAudio(@RequestParam String fileName) {
        return fileService.deleteAudio(fileName);
    }

}
