package com.jsu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.servlet.http.HttpSession;

// @Component
@Slf4j
@Aspect
public class AdminPermissionAOP {
    // @Pointcut("execution(* com.jsu.judge.controller.admin.*.*(..))")
    public void insertOrUpdate() {
    }

    /**
     * 检查该账号是否拥有管理员权限
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
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
