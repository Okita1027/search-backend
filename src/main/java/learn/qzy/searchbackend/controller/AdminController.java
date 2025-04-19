package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.Admin;
import learn.qzy.searchbackend.service.AdminService;
import learn.qzy.searchbackend.util.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author qzy
 * @create 2024/12/10 14:20 星期二
 * @title 管理员用户接口
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 管理员登陆
     * @param admin 管理员账号、密码
     * @return （搭载了SaToken的）登陆结果
     */
    @PostMapping("/login")
    public Result<SaTokenInfo> login(@RequestBody Admin admin) {
        return adminService.login(admin);
    }

    /**
     * 管理员登出
     * @return
     */
    @PostMapping("/logout")
    public Result logout() {
        return adminService.logout();
    }

    /**
     * 踢出在线的用户
     * @param token 登陆的token信息
     * @return 踢出结果
     */
    @PostMapping("/kickout")
    public Result kickOut(@RequestParam SaTokenInfo token) {
        return adminService.kickOut(token);
    }

    /**
     * 启用/封禁用户状态
     * @param username 普通用户的用户名
     * @return 启用/封禁结果
     */
    @PutMapping("/status")
    public Result status(@RequestParam String username, @RequestParam Integer status) {
        return adminService.updateStatus(username, status);
    }
}
