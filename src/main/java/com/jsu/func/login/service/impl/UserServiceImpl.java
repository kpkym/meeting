package com.jsu.func.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsu.func.login.entity.User;
import com.jsu.func.login.mapper.UserMapper;
import com.jsu.func.login.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kpkym
 * @since 2019-03-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    public User login(User user) {
        Map<SFunction<User, ?>, Object> map = new HashMap<>();
        map.put(User::getUsername, user.getUsername());
        map.put(User::getPassword, user.getPassword());

        return getOne(new LambdaQueryWrapper<User>().allEq(map));
    }

    public void register(User user) {
        save(user);
    }
}
