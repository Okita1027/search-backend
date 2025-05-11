package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.entity.ContentAudio;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.model.vo.ContentAudioVO;
import learn.qzy.searchbackend.util.Result;

import java.util.List;

/**
* @author Administrator
* @description 针对表【content_file】的数据库操作Service
* @createDate 2025-03-27 21:02:55
*/
public interface ContentAudioService extends IService<ContentAudio> {

    Result<ContentAudioVO> getAudioList(String fileName);

    Result<String> deleteAudio(String fileName);

    Result<List<ContentAudio>> getAudioListAll();

}
