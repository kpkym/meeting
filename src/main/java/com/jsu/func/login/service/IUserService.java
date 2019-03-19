package com.jsu.func.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsu.func.login.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kpkym
 * @since 2019-03-05
 */
public interface IUserService extends IService<User> {
    User login(User user);

    void register(User user);
}
