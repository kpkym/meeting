package com.jsu.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.validation.BindingResult;

// @Component
@Slf4j
@Aspect
public class ControllerVerifyAOP {
    @Pointcut("execution(* com.jsu.judge.controller..verify*(..))")
    public void aspect() {
    }


    /**
     * 每个方法前验证
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "aspect()")
    public Object validate(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object o : args) {
            if (o instanceof BindingResult) {
            }
        }

        return pjp.proceed();
    }
}
