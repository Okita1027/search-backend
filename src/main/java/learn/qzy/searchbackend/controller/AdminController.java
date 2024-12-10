package learn.qzy.searchbackend.controller;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.User;
import learn.qzy.searchbackend.service.UserService;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qzy
 * @create 2024/12/10 14:20 星期二
 * @title
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private UserService userService;

    @GetMapping("/list")
    Result list() {
        List<User> list = userService.list();
        return ResultGenerator.genSuccessResult(list);
    }
}
