package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.service.ContentUserService;
import learn.qzy.searchbackend.mapper.ContentUserMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.stereotype.Service;

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
    public Result<ContentPicture> getUserList(String title) {
        LambdaQueryWrapper<ContentUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(ContentUser::getTitle, title);
        List<ContentUser> userList = this.list(wrapper);
        return ResultGenerator.genSuccessResult(userList);
    }
}




