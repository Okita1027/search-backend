package learn.qzy.searchbackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.Admin;
import learn.qzy.searchbackend.service.AdminService;
import learn.qzy.searchbackend.mapper.UserMapper;
import learn.qzy.searchbackend.util.PasswordUtil;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.stereotype.Service;

/**
 * @author qzy
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-12-10 13:39:33
 */
@Service
public class AdminServiceImpl extends ServiceImpl<UserMapper, Admin>
        implements AdminService {

    @Override
    public Result login(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        String encryptPassword = PasswordUtil.encrypt(password);
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username).eq(Admin::getPassword, encryptPassword);
        admin = this.getOne(wrapper);
        if (admin != null) {
            StpUtil.login(admin.getId());
            return ResultGenerator.genSuccessResult("登录成功");
        }
        return ResultGenerator.genFailResult("用户名或密码错误");
    }

    @Override
    public Result logout() {
        StpUtil.logout();
        return ResultGenerator.genSuccessResult("登出成功");
    }

    @Override
    public Result kickOut(String username) {
        return null;
    }

    @Override
    public Result ban(String username) {
        return null;
    }

}
