package com.jsu.interval;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * @author kpkym
 * Date: 2019-03-23 12:19
 */
@Slf4j
@Component
public class Dispatcher {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
    //     log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
