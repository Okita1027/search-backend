package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.entity.ContentVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.model.vo.ContentVideoVO;
import learn.qzy.searchbackend.util.Result;

import java.util.List;

/**
* @author Administrator
* @description 针对表【content_video(视频表)】的数据库操作Service
* @createDate 2025-04-05 23:28:38
*/
public interface ContentVideoService extends IService<ContentVideo> {

    Result<ContentVideoVO> getVideoList(String fileName);

    Result deleteVideo(String fileName);

    Result<List<ContentVideoVO>> getVideoListAll();

}
