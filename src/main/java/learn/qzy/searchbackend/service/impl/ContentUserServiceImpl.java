package learn.qzy.searchbackend.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.dto.ContentUserDTO;
import learn.qzy.searchbackend.model.entity.Admin;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.model.vo.ContentUserVO;
import learn.qzy.searchbackend.service.ContentUserService;
import learn.qzy.searchbackend.mapper.ContentUserMapper;
import learn.qzy.searchbackend.util.PasswordUtil;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzy
 * @description 针对表【content_user】的数据库操作Service实现
 * @createDate 2024-12-10 13:39:33
 */
@Service
public class ContentUserServiceImpl extends ServiceImpl<ContentUserMapper, ContentUser>
        implements ContentUserService {

    @Override
    public Result<ContentUserVO> getUserList(String title) {
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ContentUser::getNickname, title);
        List<ContentUser> userList = this.list(wrapper);
        List<ContentUserVO> userVOList = BeanUtil.copyToList(userList, ContentUserVO.class);
        return ResultGenerator.genSuccessResult(userVOList);
    }

    @Override
    public Result<SaTokenInfo> login(ContentUser user) {
        String username = user.getUsername();
        String password = user.getPassword();
        ContentUser realUser = this.getOne(new LambdaQueryWrapper<ContentUser>().eq(ContentUser::getUsername, username));
        if (realUser != null) {
            if (PasswordUtil.matches(password, realUser.getPassword())) {
                StpUtil.login(realUser.getId());
                return ResultGenerator.genSuccessResult(StpUtil.getTokenInfo());
            }
        }
        return ResultGenerator.genFailResult("用户名或密码错误");
    }

    @Override
    public Result register(ContentUser user) {
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentUser::getUsername, user.getUsername());
        if (this.getOne(wrapper) != null) {
            return ResultGenerator.genFailResult("用户名已存在");
        }
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        user.setNickname("用户" + UUID.fastUUID());
        if (this.save(user)) {
            return ResultGenerator.genSuccessResult("注册成功");
        }
        return ResultGenerator.genFailResult("注册失败");
    }

    @Override
    public Result<ContentUserVO> getUserInfo(String username) {
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentUser::getUsername, username);
        ContentUser user = this.getOne(wrapper);
        if (user!= null) {
            ContentUserVO userVO = BeanUtil.copyProperties(user, ContentUserVO.class);
            return ResultGenerator.genSuccessResult(userVO);
        }
        return ResultGenerator.genFailResult("用户不存在");
    }

    @Override
    public Result updateUserInfo(ContentUserDTO user) {
        String rawPasswordEncrypt = PasswordUtil.encrypt(user.getRawPassword());
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentUser::getPassword, rawPasswordEncrypt)
                .eq(ContentUser::getUsername, user.getUsername());
        ContentUser newUser = this.getOne(wrapper);
        BeanUtil.copyProperties(user, newUser, "password");
        newUser.setPassword(PasswordUtil.encrypt(user.getPassword()));
        return this.saveOrUpdate(newUser) ? ResultGenerator.genSuccessResult() : ResultGenerator.genFailResult();
    }

}
