package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.util.Result;

/**
* @author qzy
* @description 针对表【user】的数据库操作Service
* @createDate 2024-12-10 13:39:33
*/
public interface AdminService extends IService<Admin> {

    Result login(Admin admin);

    Result logout();

    Result kickOut(String username);

    Result ban(String username);
}
