package learn.qzy.searchbackend.controller;

import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.model.vo.ContentVideoVO;
import learn.qzy.searchbackend.service.ContentVideoService;
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
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private ContentVideoService videoService;

    @GetMapping()
    public Result<ContentVideoVO> video(@RequestParam("text") String fileName) {
        return videoService.getVideoList(fileName);
    }


    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        /*try {
            // 调用服务层方法处理文件上传
            fileService.uploadFile(file);
            return Result.success("文件上传成功");
        } catch (Exception e) {
            return Result.error("文件上传失败: " + e.getMessage());
        }*/
        return null;
    }

}
