package learn.qzy.searchbackend.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.mapper.UserMapper;
import learn.qzy.searchbackend.model.entity.Admin;
import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.service.AdminService;
import learn.qzy.searchbackend.service.ContentUserService;
import learn.qzy.searchbackend.util.PasswordUtil;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qzy
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-12-10 13:39:33
 */
@Service
public class AdminServiceImpl extends ServiceImpl<UserMapper, Admin>
        implements AdminService {

    @Autowired
    private ContentUserService userService;

    @Override
    public Result<SaTokenInfo> login(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, username);
        Admin realAdmin = this.getOne(wrapper);
        if (realAdmin != null) {
            if (PasswordUtil.matches(password, realAdmin.getPassword())) {
                StpUtil.login(realAdmin.getId());
                return ResultGenerator.genSuccessResult(StpUtil.getTokenInfo());
            }
        }
        return ResultGenerator.genFailResult("用户名或密码错误");
    }

    @Override
    public Result logout() {
        StpUtil.logout();
        return ResultGenerator.genSuccessResult("登出成功");
    }

    @Override
    public Result kickOut(SaTokenInfo token) {
        if (token.isLogin) {
            StpUtil.kickout(token.getTokenValue());
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult();
    }

    @Override
    public Result updateStatus(String username, Integer status) {
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<ContentUser>().eq(ContentUser::getUsername, username);
        Long userId = userService.getOne(wrapper).getId();
        if (userId != null) {
            LambdaUpdateWrapper<ContentUser> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ContentUser::getId, userId).set(ContentUser::getStatus, status);
            if (userService.update(updateWrapper)) {
                return ResultGenerator.genSuccessResult("状态更新成功");
            }
        }
        return ResultGenerator.genFailResult("用户不存在");
    }

}
