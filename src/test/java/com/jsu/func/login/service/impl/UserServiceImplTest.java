package com.jsu.func.login.service.impl;

import com.jsu.func.login.entity.User;
import com.jsu.func.login.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;

/**
 * @author kpkym
 * Date: 2019-03-11 20:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    IUserService service;

    @Test
    public void login() throws SQLException, IOException {
        byte[] face = {1, 2, 3, 127, 127, 127};


        File file = new File("DxHiiGQUYAA_JBI.gif");
        byte[] fileContent = Files.readAllBytes(file.toPath());

        String encodedFile = Base64.getEncoder().encodeToString(face);
        User build = User.builder()
                .bbb(fileContent)
                .build();


        service.save(build);
    }
}