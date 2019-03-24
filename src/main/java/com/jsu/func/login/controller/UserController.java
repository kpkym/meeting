package com.jsu.func.login.controller;


import com.jsu.except.UserExceptJSON;
import com.jsu.func.login.entity.User;
import com.jsu.func.login.service.IUserService;
import com.jsu.util.Msg;
import com.jsu.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kpkym
 * @since 2019-03-05
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    IUserService service;

    @GetMapping("/getId")
    public Msg getId(HttpSession session) {
        return Msg.success(SessionUtil.getUid(session));
    }

    @GetMapping("/users/{id}")
    public Msg get(@PathVariable Integer id) {
        return Msg.success(service.getById(id));
    }

    @GetMapping("/users")
    public Msg list(HttpSession session) {
        List<User> list = service.list().stream().filter(e -> !e.getId().equals(session.getAttribute("uid"))).collect(Collectors.toList());
        return Msg.success(list);
    }

    @PostMapping("/login")
    public Msg login(User user, HttpSession session) {
        log.debug(session.getId());
        User login = service.login(user);

        if (login == null){
            throw new UserExceptJSON("没有此用户");
        }
        SessionUtil.setUid(login.getId(), session);
        return Msg.success();
    }

    @PostMapping("/register")
    public void register(User user) {
        service.register(user);
    }

    @GetMapping("/logout")
    public Msg logout(HttpSession session) {
        session.invalidate();
        return Msg.success();
    }
}
