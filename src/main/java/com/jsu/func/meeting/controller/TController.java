package com.jsu.func.meeting.controller;

import com.jsu.util.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kpkym
 * Date: 2019-03-20 18:01
 */
@Slf4j
@RestController
public class TController {
    @PostMapping("/test")
    public Msg m(String face) {
        log.info("face:" + face);
        return Msg.success();
    }
}
