package com.jsu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpSession;

// @Component
@Slf4j
@Aspect
public class RootPermissionAOP {
    @Pointcut("execution(* com.jsu.judge.controller.root.*.*(..))")
    public void insertOrUpdate() {
    }


    @Around(value = "insertOrUpdate()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object o : args) {
            if (o instanceof HttpSession) {
            }
        }

        Object retVal = pjp.proceed();
        return retVal;
    }
}
