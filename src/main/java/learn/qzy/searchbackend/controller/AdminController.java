package learn.qzy.searchbackend.controller;

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

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        return adminService.login(admin);
    }

    @PostMapping("/logout")
    public Result logout() {
        return adminService.logout();
    }

    /**
     * 踢出在线用户
     * @param username 普通用户名
     * @return 踢出结果
     */
    @PostMapping("/kickout")
    public Result kickout(@RequestParam String username) {
        return adminService.kickOut(username);
    }

    /**
     * 封禁用户状态
     * @param username 普通用户名
     * @return 封禁结果
     */
    @PostMapping("/ban")
    public Result ban(@RequestParam String username) {
        return adminService.ban(username);
    }
}
