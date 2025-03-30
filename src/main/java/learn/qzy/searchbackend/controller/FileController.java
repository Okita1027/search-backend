package learn.qzy.searchbackend.controller;

import learn.qzy.searchbackend.model.vo.ContentFileVO;
import learn.qzy.searchbackend.service.ContentFileService;
import learn.qzy.searchbackend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qzy
 * @time 2025年3月27日 21:13 星期四
 * @title 文件控制类
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private ContentFileService fileService;

    @GetMapping("/audio")
    public Result<ContentFileVO> audio(@RequestParam("text") String fileName) {
        return fileService.getAudioList(fileName);
    }


    @GetMapping("/video")
    public Result<ContentFileVO> video(@RequestParam("text") String fileName) {
        return fileService.getVideoList(fileName);
    }
}
