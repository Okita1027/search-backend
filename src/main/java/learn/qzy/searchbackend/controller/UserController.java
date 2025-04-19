package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.dto.ContentUserDTO;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.model.vo.ContentUserVO;
import learn.qzy.searchbackend.service.ContentPictureService;
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
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/info")
    public Result updateUserInfo(@RequestBody ContentUserDTO user) {
        return userService.updateUserInfo(user);
    }

}
