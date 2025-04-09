package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.Admin;
import learn.qzy.searchbackend.service.AdminService;
import learn.qzy.searchbackend.mapper.UserMapper;
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
    public boolean login(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();

        return false;
    }
}
