package learn.qzy.searchbackend.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.mapper.ArticleCommentMapper;
import learn.qzy.searchbackend.mapper.ContentUserMapper;
import learn.qzy.searchbackend.model.dto.BaseFileDTO;
import learn.qzy.searchbackend.model.dto.ContentUserDTO;
import learn.qzy.searchbackend.model.entity.ArticleComment;
import learn.qzy.searchbackend.model.entity.CommentLike;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.model.vo.AdminContentUserVO;
import learn.qzy.searchbackend.model.vo.ContentUserDetailVO;
import learn.qzy.searchbackend.model.vo.ContentUserVO;
import learn.qzy.searchbackend.service.ArticleCommentService;
import learn.qzy.searchbackend.service.CommentLikeService;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.service.ContentUserService;
import learn.qzy.searchbackend.util.PasswordUtil;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qzy
 * @description 针对表【content_user】的数据库操作Service实现
 * @createDate 2024-12-10 13:39:33
 */
@Service
public class ContentUserServiceImpl extends ServiceImpl<ContentUserMapper, ContentUser>
        implements ContentUserService {

    @Autowired
    private CommentLikeService commentLikeService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Autowired
    private ContentArticleService articleService;
    @Autowired
    private ContentUserMapper contentUserMapper;

    @Override
    public Result<ContentUserVO> getUserList(String title) {
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ContentUser::getNickname, title)
                .eq(ContentUser::getIsDeleted, 0);
        List<ContentUser> userList = this.list(wrapper);
        List<ContentUserVO> userVOList = BeanUtil.copyToList(userList, ContentUserVO.class);
        return ResultGenerator.genSuccessResult(userVOList);
    }

    @Override
    public Result<SaTokenInfo> login(ContentUser user) {
        String username = user.getUsername();
        String password = user.getPassword();
        ContentUser realUser = this.getOne(new LambdaQueryWrapper<ContentUser>()
                .eq(ContentUser::getUsername, username)
                // 0禁用；1正常
                .eq(ContentUser::getStatus, 1));
        if (realUser != null) {
            if (PasswordUtil.matches(password, realUser.getPassword())) {
                StpUtil.login(realUser.getId());
                return ResultGenerator.genSuccessResult(StpUtil.getTokenInfo());
            }
        }
        return ResultGenerator.genFailResult("用户名或密码错误");
    }

    @Override
    public Result register(ContentUser user) {
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentUser::getUsername, user.getUsername());
        if (this.getOne(wrapper) != null) {
            return ResultGenerator.genFailResult("用户名已存在");
        }
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        user.setNickname("用户" + UUID.fastUUID());
        if (this.save(user)) {
            return ResultGenerator.genSuccessResult("注册成功");
        }
        return ResultGenerator.genFailResult("注册失败");
    }

    @Override
    public Result<ContentUserVO> getUserInfo(String username) {
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentUser::getUsername, username)
                .eq(ContentUser::getIsDeleted, 0);
        ContentUser user = this.getOne(wrapper);
        if (user != null) {
            ContentUserVO userVO = BeanUtil.copyProperties(user, ContentUserVO.class);
            return ResultGenerator.genSuccessResult(userVO);
        }
        return ResultGenerator.genFailResult("用户不存在");
    }

    @Override
    public Result logout() {
        StpUtil.logout();
        return ResultGenerator.genSuccessResult("登出成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result favorComment(Long commentId) {
        long userId = StpUtil.getLoginIdAsLong();
        ContentUser user = this.getById(userId);
        List<Long> favorCommentList = user.getFavorComment();
        // 取消点赞
        for (Long favorComment : favorCommentList) {
            if (favorComment.equals(commentId)) {
                favorCommentList.remove(commentId);
                user.setFavorComment(favorCommentList);
                this.updateById(user);
                commentLikeService.update().setSql("like_count = like_count - 1").eq("comment_id", commentId).update();
                return ResultGenerator.genFailResult("已取消点赞");
            }
        }
        // 点赞
        favorCommentList.add(commentId);
        user.setFavorComment(favorCommentList);
        this.updateById(user);
        commentLikeService.update().setSql("like_count = like_count + 1").eq("comment_id", commentId).update();
        return ResultGenerator.genSuccessResult("已点赞");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result comment(String articleTitle, Long commentId, String commentContent) {
        LambdaQueryWrapper<ContentArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentArticle::getTitle, articleTitle);
        Long articleId = articleService.getOne(wrapper).getId();
        ArticleComment articleComment = new ArticleComment();
        articleComment.setContent(commentContent);
        articleComment.setArticleId(articleId);

        long userId = StpUtil.getLoginIdAsLong();
        ContentUser currentUser = this.getById(userId);
        articleComment.setCurrentUsername(currentUser.getUsername());
        articleComment.setCurrentNickname(currentUser.getNickname());

        ArticleComment parentComment = articleCommentService.getById(commentId);
        if (parentComment != null) {
            // 回复其它评论(楼中楼)
            articleComment.setSerialNumber(parentComment.getSerialNumber());
            articleComment.setParentUsername(parentComment.getCurrentUsername());
            articleComment.setParentNickname(parentComment.getCurrentNickname());
        } else {
            // 发布评论（单开一楼）
            Integer serialNumber = articleCommentMapper.selectMaxSerialNumberByArticleId(articleId);
            if (serialNumber == null) {
                serialNumber = 0;
            }
            articleComment.setSerialNumber(serialNumber + 1);
        }

        articleCommentService.save(articleComment);

        // 发布评论后，增加一条评论点赞数量的记录
        CommentLike commentLike = new CommentLike();
        commentLike.setCommentId(articleComment.getId());
        commentLike.setLikeCount(0L);
        commentLikeService.save(commentLike);

        return articleComment.getId() != null ? ResultGenerator.genSuccessResult("评论成功") : ResultGenerator.genFailResult("评论失败");
    }

    @Override
    public Result<ContentUserDetailVO> getUserDetail(String username) {
        // 查询用户基本信息
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentUser::getUsername, username);
        ContentUser user = this.getOne(wrapper);
        if (user != null) {
            ContentUserDetailVO detailVO = BeanUtil.copyProperties(user, ContentUserDetailVO.class);
            Map<String, List<BaseFileDTO>> fileListMap = new HashMap<>();
            // 查询用户上传了哪些文件
            List<BaseFileDTO> fileList = contentUserMapper.selectFileListByUserId(user.getId());
            List<BaseFileDTO> pictureList = new ArrayList<>();
            List<BaseFileDTO> audioList = new ArrayList<>();
            List<BaseFileDTO> videoList = new ArrayList<>();
            for (BaseFileDTO file : fileList) {
                String filePath = file.getFilePath();
                // 根据filePath后缀判断是 图片、音频、视频
                if (filePath.endsWith(".jpg") || filePath.endsWith(".png")) {
                    pictureList.add(file);
                } else if (filePath.endsWith(".mp3") || filePath.endsWith(".flac")) {
                    audioList.add(file);
                } else if (filePath.endsWith(".mp4") || filePath.endsWith(".avi")) {
                    videoList.add(file);
                }
            }
            fileListMap.put("picture", pictureList);
            fileListMap.put("audio", audioList);
            fileListMap.put("video", videoList);
            detailVO.setFileListMap(fileListMap);
            // 查询用户点过赞的评论所属的文章名称
            detailVO.setPostTitleList(contentUserMapper.selectArticleTitleList(user.getFavorComment()));
            return ResultGenerator.genSuccessResult(detailVO);
        }
        return ResultGenerator.genFailResult("没有这个用户");
    }

    @Override
    public Result<List<AdminContentUserVO>> getUserListAll() {
        List<ContentUser> userList = this.list();
        List<AdminContentUserVO> result = BeanUtil.copyToList(userList, AdminContentUserVO.class);
        result.forEach(vo -> {
            vo.setLogin(StpUtil.isLogin(vo.getId()));
        });
        return ResultGenerator.genSuccessResult(result);
    }

    @Override
    public Result<ContentUser> getCurrentLoginUser() {
        long userId = StpUtil.getLoginIdAsLong();
        ContentUser user = this.getById(userId);
        if (user != null) {
            return ResultGenerator.genSuccessResult(user);
        }
        return ResultGenerator.genFailResult(null);
    }

    @Override
    public Result updateUserInfo(ContentUserDTO user) {
        if (StrUtil.isNotEmpty(user.getPassword()) && user.getPassword().equals(user.getRawPassword())) {
            return ResultGenerator.genFailResult("新密码不能与旧密码相同");
        }
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentUser::getUsername, user.getUsername());
        ContentUser realUser = this.getOne(wrapper);
        if (realUser != null) {
            // 改密码
            if (StrUtil.isNotEmpty(user.getPassword()) && StrUtil.isNotEmpty(user.getRawPassword())
                    && PasswordUtil.matches(user.getRawPassword(), realUser.getPassword())) {
                realUser.setPassword(PasswordUtil.encrypt(user.getPassword()));
            }
            // 改普通信息
            realUser.setGender(user.getGender());
            realUser.setNickname(user.getNickname());
            realUser.setEmail(user.getEmail());
            realUser.setPhone(user.getPhone());
            realUser.setProfile(user.getProfile());
            realUser.setAvatarUrl(user.getAvatarUrl());
            realUser.setUpdateTime(LocalDateTime.now());
            if (this.updateById(realUser)) {
                return ResultGenerator.genSuccessResult("更新成功");
            }
        }

        return ResultGenerator.genFailResult("更新失败");
    }

}
