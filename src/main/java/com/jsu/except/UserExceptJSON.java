package com.jsu.except;

/**
 * @author kpkym
 * Date: 2019-03-11 20:51
 */
public class UserExceptJSON extends RuntimeException {
    public UserExceptJSON(String message) {
        super(message);
    }
}
