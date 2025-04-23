package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.dto.ContentUserDTO;
import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.model.vo.ContentUserVO;
import learn.qzy.searchbackend.service.ContentUserService;
import learn.qzy.searchbackend.util.Result;
import org.springframework.web.bind.annotation.*;

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

    @SaCheckLogin
    @PostMapping("/logout")
    public Result logout() {
        return userService.logout();
    }

    /**
     * 获取用户列表
     * @param text 用户名
     * @return 用户列表
     */
    @GetMapping
    public Result<ContentUserVO> getUserList(@RequestParam String text) {
        return userService.getUserList(text);
    }

    /**
     * 获取某个用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<ContentUserVO> getUserInfo(@RequestParam String username) {
        return userService.getUserInfo(username);
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



}
