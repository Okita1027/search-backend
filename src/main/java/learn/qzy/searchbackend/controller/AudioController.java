package learn.qzy.searchbackend.controller;

import cn.hutool.core.util.StrUtil;
import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.service.ContentAudioService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping()
    public Result<ContentAudioVO> audio(@RequestParam("text") String fileName) {
        if (StrUtil.isEmpty(fileName)) {
            return ResultGenerator.genFailResult("请输入搜索内容");
        }
        return fileService.getAudioList(fileName);
    }

}
