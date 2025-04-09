package learn.qzy.searchbackend.controller;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.Admin;
import learn.qzy.searchbackend.service.AdminService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if (adminService.login(admin)) {
            return ResultGenerator.genSuccessResult("登陆成功");
        } else {
            return ResultGenerator.genFailResult("用户名或密码错误");
        }
    }
}
