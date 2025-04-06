package learn.qzy.searchbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.ContentAudio;
import learn.qzy.searchbackend.model.entity.ContentVideo;
import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.model.vo.ContentVideoVO;
import learn.qzy.searchbackend.service.ContentVideoService;
import learn.qzy.searchbackend.mapper.ContentVideoMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【content_video(视频表)】的数据库操作Service实现
* @createDate 2025-04-05 23:28:38
*/
@Service
public class ContentVideoServiceImpl extends ServiceImpl<ContentVideoMapper, ContentVideo>
    implements ContentVideoService{

    @Override
    public Result<ContentVideoVO> getVideoList(String fileName) {
        LambdaQueryWrapper<ContentVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ContentVideo::getFileName, fileName);
        List<ContentVideo> videoList = list(wrapper);
        List<ContentVideoVO> videoVOList = BeanUtil.copyToList(videoList, ContentVideoVO.class);
        return ResultGenerator.genSuccessResult(videoVOList);
    }
}




