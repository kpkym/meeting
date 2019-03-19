package com.jsu.util;


import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static void setUid(Object uid, HttpSession session) {
        session.setAttribute("uid", uid);
    }

    public static Object getUid(HttpSession session) {
        return session.getAttribute("uid");
    }
}
