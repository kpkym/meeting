package com.jsu.resolver;

import com.jsu.except.UserExceptJSON;
import com.jsu.util.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(UserExceptJSON.class)
    public ResponseEntity handle(Exception e) {
        return new ResponseEntity<>(Msg.failure(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}