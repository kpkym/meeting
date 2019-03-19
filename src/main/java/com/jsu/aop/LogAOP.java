package com.jsu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class LogAOP {
    @Pointcut("execution(* com.jsu..*(..))")
    public void aspect() {
    }

    @Before(value = "aspect()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("开始执行：" + signature.toString());
    }

    @After(value = "aspect()")
    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("执行结束：" + signature.toString());
    }
}