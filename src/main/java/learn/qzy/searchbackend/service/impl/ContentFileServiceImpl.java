package learn.qzy.searchbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.ContentFile;
import learn.qzy.searchbackend.model.vo.ContentFileVO;
import learn.qzy.searchbackend.service.ContentFileService;
import learn.qzy.searchbackend.mapper.ContentFileMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【content_file】的数据库操作Service实现
 * @createDate 2025-03-27 21:02:55
 */
@Service
public class ContentFileServiceImpl extends ServiceImpl<ContentFileMapper, ContentFile>
        implements ContentFileService {

    @Override
    public Result<ContentFileVO> getAudioList(String fileName) {
        LambdaQueryWrapper<ContentFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ContentFile::getFileName, fileName);
        wrapper.eq(ContentFile::getFileType, 0);
        List<ContentFile> audioList = list(wrapper);
        List<ContentFileVO> audioVOList = BeanUtil.copyToList(audioList, ContentFileVO.class);
        return ResultGenerator.genSuccessResult(audioVOList);
    }

    @Override
    public Result<ContentFileVO> getVideoList(String fileName) {
        LambdaQueryWrapper<ContentFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ContentFile::getFileName, fileName);
        wrapper.eq(ContentFile::getFileType, 1);
        List<ContentFile> videoList = list(wrapper);
        List<ContentFileVO> videoVOList = BeanUtil.copyToList(videoList, ContentFileVO.class);
        return ResultGenerator.genSuccessResult(videoVOList);
    }
}
