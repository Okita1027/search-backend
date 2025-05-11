package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.vo.ContentPictureVO;
import learn.qzy.searchbackend.service.ContentPictureService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 获取图片列表
     * @param text 搜索关键词
     * @return 图片的URL
     */
    @GetMapping
    public Result<ContentPictureVO> getPictureList(@RequestParam String text) {
        if (StrUtil.isEmpty(text)) {
            return ResultGenerator.genFailResult("搜索关键词不能为空");
        }
        return pictureService.getPictureList(text);
    }

    /**
     * 获取所有图片列表(后台管理)
     * @return 图片全部字段
     */
    @GetMapping("/list")
    public Result<List<ContentPicture>> getPictureList() {
        return pictureService.getPictureListAll();
    }

    /**
     * 删除图片
     * @param fileName 图片的文件名
     * @return 删除结果
     */
    @SaCheckLogin
    @DeleteMapping
    public Result<String> deletePicture(@RequestParam String fileName) {
        int result = pictureService.deleteExistsPicture(fileName);
        if (result == 0) {
            return ResultGenerator.genFailResult("删除失败");
        }
        return ResultGenerator.genSuccessResult("删除成功");
    }

}
