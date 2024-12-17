package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.entity.ContentPicture;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.model.vo.ContentPictureVO;
import learn.qzy.searchbackend.util.Result;

/**
* @author qzy
* @description 针对表【content_picture】的数据库操作Service
* @createDate 2024-12-10 13:39:33
*/
public interface ContentPictureService extends IService<ContentPicture> {

    boolean isExistsPicture(String title);

    void deleteExistsPicture(String title);

    Result<ContentPictureVO> getPictureList(String title);
}

