package com.jsu.func.meeting.controller;


import com.jsu.func.meeting.entity.Room;
import com.jsu.func.meeting.service.IRoomService;
import com.jsu.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kpkym
 * @since 2019-03-05
 */
@RestController
@RequestMapping("/meeting")
public class RoomController {
    @Autowired
    IRoomService service;

    @GetMapping(value = "/room/{id}")
    public Msg get(@PathVariable Integer id) {
        Room room = service.getById(id);
        return Msg.success(room);
    }

    @GetMapping("/room")
    public Msg list() {
        List<Room> list = service.list();
        return Msg.success(list);
    }
}
