package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.dto.ContentUserDTO;
import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.model.vo.AdminContentUserVO;
import learn.qzy.searchbackend.model.vo.ContentUserDetailVO;
import learn.qzy.searchbackend.model.vo.ContentUserVO;
import learn.qzy.searchbackend.service.ContentUserService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qzy
 * @create 2024/12/12 15:28 星期四
 * @title 普通用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ContentUserService userService;

    /**
     * 普通用户注册
     * @param user 用户名、密码
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody ContentUser user) {
        return userService.register(user);
    }

    /**
     * 普通用户登陆
     * @param user 用户名、密码
     * @return （搭载了SaToken的）登陆结果
     */
    @PostMapping("/login")
    public Result<SaTokenInfo> login(@RequestBody ContentUser user) {
        return userService.login(user);
    }

    /**
     * 普通用户退出登陆
     * @return 退出结果
     */
    @SaCheckLogin
    @PostMapping("/logout")
    public Result logout() {
        return userService.logout();
    }

    /**
     * 获取当前登陆的用户信息
     * @return 当前登陆的用户信息
     */
    @GetMapping("/current")
    public Result<ContentUser> getCurrentUser() {
        return userService.getCurrentLoginUser();
    }

    /**
     * 获取用户列表（搜索页面）
     * @param text 用户名
     * @return 用户列表
     */
    @GetMapping
    public Result<ContentUserVO> getUserList(@RequestParam String text) {
        if (StrUtil.isEmpty(text)) {
            return ResultGenerator.genFailResult("请输入搜索内容");
        }
        return userService.getUserList(text);
    }

    /**
     * 获取所有用户列表（管理员页面）
     * @return 所有用户列表
     */
    @GetMapping("/list")
    public Result<List<AdminContentUserVO>> getUserList() {
        return userService.getUserListAll();
    }

    /**
     * 获取某个用户信息（首页搜索页面）
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<ContentUserVO> getUserInfo(@RequestParam String username) {
        return userService.getUserInfo(username);
    }

    /**
     * 获取用户详细信息（个人中心页面）
     * @param username 用户名
     * @return 用户详细信息
     */
    @GetMapping("/detail")
    public Result<ContentUserDetailVO> getUserDetail(@RequestParam String username) {
        return userService.getUserDetail(username);
    }

    /**
     * 更新用户信息
     * @param user 用户信息（DTO类增加了一个rawPassword用于匹配用户是否正确输入了原来的密码）
     * @return 更新结果
     */
    @SaCheckLogin
    @PutMapping("/info")
    public Result updateUserInfo(@RequestBody ContentUserDTO user) {
        return userService.updateUserInfo(user);
    }


    /**
     * 给评论点赞
     * @param commentId 评论ID
     * @return 点赞结果
     */
    @SaCheckLogin
    @PostMapping("/favor")
    public Result favor(@RequestParam Long commentId) {
        return userService.favorComment(commentId);
    }


    /**
     * 发布评论
     * @param articleTitle 文章标题
     * @param commentId 父评论ID（如果是回复其它评论，则不为null）
     * @param commentContent 评论内容
     * @return 评论结果
     */
    @SaCheckLogin
    @PostMapping("/comment")
    public Result comment(@RequestParam String articleTitle,
                          @RequestParam(required = false) Long commentId,
                          @RequestParam String commentContent) {
        return userService.comment(articleTitle, commentId, commentContent);
    }


}
