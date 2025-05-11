package learn.qzy.searchbackend.mapper;

import learn.qzy.searchbackend.model.entity.ContentPicture;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author qzy
* @description 针对表【content_picture】的数据库操作Mapper
* @createDate 2024-12-10 13:39:33
* @Entity generator.ContentPicture
*/
public interface ContentPictureMapper extends BaseMapper<ContentPicture> {
    @Update("${sql}")
    void executeResetID(String sql);

    List<ContentPicture> selectListAll();

}




