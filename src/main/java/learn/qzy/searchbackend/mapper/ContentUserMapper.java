package learn.qzy.searchbackend.mapper;

import learn.qzy.searchbackend.model.dto.BaseFileDTO;
import learn.qzy.searchbackend.model.entity.ContentUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author qzy
* @description 针对表【content_user】的数据库操作Mapper
* @createDate 2024-12-10 13:39:33
* @Entity generator.ContentUser
*/
public interface ContentUserMapper extends BaseMapper<ContentUser> {

    List<BaseFileDTO> selectFileListByUserId(Long id);

    String selectUserNameById(String createBy);

    List<String> selectArticleTitleList(@Param("favorCommentList") List<Long> favorCommentList);
}
