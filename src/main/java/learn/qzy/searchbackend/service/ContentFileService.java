package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.entity.ContentFile;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.model.vo.ContentFileVO;
import learn.qzy.searchbackend.util.Result;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Administrator
* @description 针对表【content_file】的数据库操作Service
* @createDate 2025-03-27 21:02:55
*/
public interface ContentFileService extends IService<ContentFile> {

    Result<ContentFileVO> getAudioList(String fileName);

    Result<ContentFileVO> getVideoList(String fileName);

    void uploadFile(MultipartFile file) throws Exception;
}
