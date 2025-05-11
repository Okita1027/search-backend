package learn.qzy.searchbackend.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import learn.qzy.searchbackend.model.dto.ContentUserDTO;
import learn.qzy.searchbackend.model.entity.ContentUser;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.model.vo.AdminContentUserVO;
import learn.qzy.searchbackend.model.vo.ContentUserDetailVO;
import learn.qzy.searchbackend.model.vo.ContentUserVO;
import learn.qzy.searchbackend.util.Result;

import java.util.List;

/**
* @author qzy
* @description 针对表【content_user】的数据库操作Service
* @createDate 2024-12-10 13:39:33
*/
public interface ContentUserService extends IService<ContentUser> {

    Result<ContentUserVO> getUserList(String title);

    Result<SaTokenInfo> login(ContentUser user);

    Result register(ContentUser user);

    Result updateUserInfo(ContentUserDTO user);

    Result<ContentUserVO> getUserInfo(String username);

    Result logout();

    /**
     * 点赞评论
     * @param commentId 评论的ID
     * @return 点赞结果
     */
    Result favorComment(Long commentId);

    /**
     * 评论
     * @param articleTitle 文章标题
     * @param commentId 评论的ID
     * @param commentContent 评论内容
     * @return 评论结果
     */
    Result comment(String articleTitle, Long commentId, String commentContent);

    Result<ContentUserDetailVO> getUserDetail(String username);

    Result<List<AdminContentUserVO>> getUserListAll();

    Result<ContentUser> getCurrentLoginUser();

}
