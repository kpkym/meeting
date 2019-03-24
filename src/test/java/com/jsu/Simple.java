package com.jsu;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kpkym
 * Date: 2019-03-16 15:54
 */
@Slf4j
public class Simple {
    @Test
    public void a() throws ParseException {
        Date date = new Date();
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2019-03-17 23:12:12");

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

        boolean equals = fmt.format(date).equals(fmt.format(date1));
        System.out.println(equals);

        System.out.println(new Date());

    }

    @Test
    public void base() throws IOException {
        // File file = new File("_012.jpg");

        log.info("啊");
    }

}
