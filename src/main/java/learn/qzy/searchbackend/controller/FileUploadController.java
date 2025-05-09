package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.StrUtil;
import learn.qzy.searchbackend.exception.ThrowUtils;
import learn.qzy.searchbackend.model.entity.ContentAudio;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.entity.ContentVideo;
import learn.qzy.searchbackend.service.ContentAudioService;
import learn.qzy.searchbackend.service.ContentPictureService;
import learn.qzy.searchbackend.service.ContentVideoService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static learn.qzy.searchbackend.constant.consist.FileUploadConstant.*;
import static learn.qzy.searchbackend.constant.enums.FailureCodeEnum.FILE_UPLOAD_FAILURE;

/**
 * @author qzy
 * @email qinzhiyun1027@163.com
 * @create 2025年4月19日 16:13 星期六
 * @title 文件上传接口
 */
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private ContentPictureService pictureService;
    @Autowired
    private ContentVideoService videoService;
    @Autowired
    private ContentAudioService audioService;

    /**
     * 文件上传
     * @param file 上传的文件（音频、视频、图片）
     * @return 上传结果
     */
    @SaCheckLogin
    @PostMapping
    public Result upload(@RequestParam("file") MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (StrUtil.isBlank(filename)) {
            return ResultGenerator.genFailResult("文件名不能为空");
        }
        
        // 获取文件扩展名并转为小写
        String fileExtension = Optional.of(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .map(String::toLowerCase)
                .orElse("");

        // 判断文件类型
        String fileType = UPLOAD_TYPE_MAP.entrySet().stream()
                .filter(entry -> entry.getValue().contains(fileExtension))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("unknown");

        if ("unknown".equals(fileType)) {
            return ResultGenerator.genFailResult("不支持的文件类型");
        }

        // 根据文件类型获取对应的存储路径,并放入数据库中
        String storagePath;
        switch (fileType) {
            case "picture":
                storagePath = PICTURE_UPLOAD_URL;
                ContentPicture picture = new ContentPicture();
//                picture.setFilePath(storagePath + "\\" + filename);
                picture.setFilePath("picture/" + filename);
                picture.setFileName(filename.substring(0, filename.lastIndexOf(".")));
                pictureService.save(picture);
                break;
            case "video":
                storagePath = VIDEO_UPLOAD_URL;
                ContentVideo video = new ContentVideo();
//                video.setFilePath(storagePath + "\\" + filename);
                video.setFilePath("video/" + filename);
                video.setFileName(filename.substring(0, filename.lastIndexOf(".")));
                videoService.save(video);
                break;
            case "audio":
                storagePath = AUDIO_UPLOAD_URL;
                ContentAudio audio = new ContentAudio();
//                audio.setFilePath(storagePath + "\\" + filename);
                audio.setFilePath("audio/" + filename);
                audio.setFileName(filename.substring(0, filename.lastIndexOf(".")));
                audioService.save(audio);
                break;
            default:
                throw new IllegalStateException("未知的文件类型");
        }

        // 保存文件到本地
        try {
            file.transferTo(new File(storagePath, filename));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResultGenerator.genSuccessResult("上传成功");
    }
}
