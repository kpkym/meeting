package com.jsu;

import com.jsu.func.login.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<User> users = new ArrayList<>();
        users.add(User.builder().age(10).build());

        List<User> clone = (List<User>) ((ArrayList<User>) users).clone();

        users.get(0).setAge(20);

        System.out.println(users);


        System.out.println(clone);
    }

}
