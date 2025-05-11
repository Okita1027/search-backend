package learn.qzy.searchbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.Admin;
import learn.qzy.searchbackend.service.AdminService;
import learn.qzy.searchbackend.util.Result;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qzy
 * @create 2024/12/10 14:20 星期二
 * @title 管理员用户接口
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
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
     * @return 登出结果
     */
    @SaCheckLogin
    @PostMapping("/logout")
    public Result<String> logout() {
        return adminService.logout();
    }

    /**
     * 强制下线在线的用户
     * @param id 用户id
     * @return 踢出结果
     */
    @SaCheckLogin
    @PostMapping("/kickout")
    public Result<String> kickOut(@RequestParam Long id) {
        return adminService.kickOut(id);
    }

    /**
     * 启用/封禁用户状态
     * @param username 普通用户的用户名
     * @return 启用/封禁结果
     */
    @SaCheckLogin
    @PutMapping("/status")
    public Result<String> status(@RequestParam String username, @RequestParam Integer status) {
        return adminService.updateStatus(username, status);
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 删除结果
     */
    @SaCheckLogin
    @DeleteMapping("/user")
    public Result<String> delete(@RequestParam Long id) {
        return adminService.deleteUser(id);
    }

}
