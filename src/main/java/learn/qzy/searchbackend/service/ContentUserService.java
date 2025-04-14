package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.dto.ContentUserDTO;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.entity.ContentUser;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.model.vo.ContentUserVO;
import learn.qzy.searchbackend.util.Result;

/**
* @author qzy
* @description 针对表【content_user】的数据库操作Service
* @createDate 2024-12-10 13:39:33
*/
public interface ContentUserService extends IService<ContentUser> {

    Result<ContentUserVO> getUserList(String title);

    Result login(ContentUser user);

    Result register(ContentUser user);

    Result updateUserInfo(ContentUserDTO user);

    Result<ContentUserVO> getUserInfo(String username);
}
