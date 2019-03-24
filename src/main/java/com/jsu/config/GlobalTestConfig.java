package com.jsu.config;

import com.jsu.util.SessionUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

/**
 * @author kpkym
 * Date: 2019-03-07 10:20
 */
@ControllerAdvice
public class GlobalTestConfig {

    @ModelAttribute
    public void initSession(HttpSession session) {
        SessionUtil.setUid(1, session);

        session.setAttribute("uid", 1);
    }

}
