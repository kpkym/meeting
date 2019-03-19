package com.jsu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Slf4j
@Aspect
public class CandidatePermissionAOP {

    @Pointcut("execution(* com.jsu.judge.controller.candidate.*.*(..))")
    public void candidate() {
    }

    @Around(value = "candidate()")
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
