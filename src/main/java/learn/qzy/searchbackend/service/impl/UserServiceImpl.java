package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.User;
import learn.qzy.searchbackend.service.UserService;
import learn.qzy.searchbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author qzy
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-12-10 13:39:33
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}
