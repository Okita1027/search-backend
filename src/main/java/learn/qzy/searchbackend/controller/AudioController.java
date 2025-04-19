package learn.qzy.searchbackend.controller;

import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.service.ContentAudioService;
import learn.qzy.searchbackend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qzy
 * @time 2025年3月27日 21:13 星期四
 * @title 文件（音频、视频）控制类
 */
@RestController
@RequestMapping("/audio")
public class AudioController {

    @Autowired
    private ContentAudioService fileService;

    @GetMapping()
    public Result<ContentAudioVO> audio(@RequestParam("text") String fileName) {
        return fileService.getAudioList(fileName);
    }

}
