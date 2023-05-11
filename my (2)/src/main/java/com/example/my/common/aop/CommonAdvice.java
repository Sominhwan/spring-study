package com.example.my.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.my.common.exception.EntityNotFoundException;

@Aspect
@Component
public class CommonAdvice {
    
    // @Before("execution(public * com.example.my..controller..*(..))")
    // @Before("within(com.example.my..controller.*)")
    // @Before("bean(*ControllerApi*)")
    // public void tempAdvice() {
    //     log.warn("요청이 들어왔습니다.");
    // }

    @Around("execution(* com.example.my.todo.repository..findByIdx(..))")
    public Object checkFindByIdx(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // proceedingJoinPoint.proceed() -> 리파지토리의 findByIdx 함수 그 자체
        // proceedingJoinPoint.getArgs() -> 리파지토리의 findByIdx 매개변수 그 자체
        Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

        if (result == null) {
            throw new EntityNotFoundException("해당하는 데이터가 없습니다!");
        }

        return result;
    }
}
