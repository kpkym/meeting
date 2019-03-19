package com.jsu;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @author kpkym
 * Date: 2019-03-16 15:54
 */
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
        File file = new File("_012.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String encodedFile = Base64.getEncoder().encodeToString(bytes);

        encodedFile = "data:" + URLConnection.guessContentTypeFromName(file.getName()) + ";base64," + encodedFile;
        System.out.println(encodedFile);

    }

}
