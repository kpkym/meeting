package com.jsu.func.meeting.controller;


import com.jsu.except.UserExceptJSON;
import com.jsu.func.login.entity.User;
import com.jsu.func.login.service.IUserService;
import com.jsu.func.meeting.entity.Meeting;
import com.jsu.func.meeting.service.IMeetingService;
import com.jsu.func.meeting.service.IRoomService;
import com.jsu.util.MeetingUtil;
import com.jsu.util.Msg;
import com.jsu.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
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
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    IMeetingService service;
    @Autowired
    IUserService userService;
    @Autowired
    IRoomService roomService;
    @Autowired
    MeetingUtil meetingUtil;

    @PostMapping("/meeting")
    public Msg create(Meeting meeting, HttpSession session) {
        meeting.setHost((Integer) SessionUtil.getUid(session));

        if (!service.save(meeting)) {
            throw new UserExceptJSON("该时间段存在会议安排");
        }

        return Msg.success();
    }

    @GetMapping("/meeting")
    public Msg list(HttpSession session) {
        Object uid = SessionUtil.getUid(session);

        List<Meeting> collect = service.list().stream().filter(e -> e.getHost().equals(uid) || Arrays.asList(e.getUids().split("_")).contains(uid.toString()))
                .peek(e -> {
                    e.setHoster(userService.getById(e.getHost()));
                    e.setRoom(roomService.getById(e.getRid()));

                    Collection<User> users = userService.listByIds(Arrays.stream(e.getUids().split("_")).map(Integer::valueOf).collect(Collectors.toList()));
                    e.setParticipants(new ArrayList<>(users));
                })
                .collect(Collectors.toList());
        return Msg.success(collect);
    }

    @GetMapping(value = "/meeting/{id}")
    public Msg get(@PathVariable Integer id) {
        Meeting meeting = service.getById(id);
        meeting.setHoster(userService.getById(meeting.getHost()));
        meeting.setRoom(roomService.getById(meeting.getRid()));

        Collection<User> users = userService.listByIds(Arrays.stream(meeting.getUids().split("_")).map(Integer::valueOf).collect(Collectors.toList()));
        meeting.setParticipants(new ArrayList<>(users));
        return Msg.success(meeting);
    }

    @GetMapping("/meetingTable")
    public Msg meetingTable(@DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        Map map = meetingUtil.meetingTable(date);
        return Msg.success(map);
    }

    @GetMapping("/ddd")
    public Msg meetinTable(Date date) {
        log.info(date.toString());
        return Msg.success();
    }

}
