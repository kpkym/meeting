package com.jsu.util;

import lombok.Data;


@Data
public class Msg {

    /**
     * 0 表示成功 1 表示失败
     */
    private int code;
    private String message;
    private Object content;

    public static Msg success() {
        Msg msg = new Msg();
        msg.setCode(0);
        return msg;
    }

    public static Msg success(Object v) {
        Msg msg = new Msg();
        msg.setCode(0);
        msg.setContent(v);
        return msg;
    }

    public static Msg failure(String message) {
        Msg msg = new Msg();
        msg.setCode(1);
        msg.setMessage(message);
        return msg;
    }
}
