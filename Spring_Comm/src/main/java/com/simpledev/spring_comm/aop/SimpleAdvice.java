package com.simpledev.spring_comm.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 执行顺序
 * try{
 *     try {
 *         //@Before
 *         method.invoke(..);
 *     } finally {
 *         //@After
 *     }
 *     //@AfterReturning
 * } catch() {
 *     //@AfterThrowing
 * }
 */
@Component
@Aspect
public class SimpleAdvice {

    @Before("execution(* com.simpledev.spring_comm.aop.Cooker.*(..))")
    public void beforeDoCook(JoinPoint joinPoint) {
        System.out.println("----beforeAllMethod-----------------start--");
        System.out.println("--target:" + joinPoint.getTarget());
        System.out.println("--method:" + joinPoint.getSignature().getName());
        System.out.print("--params:" );
        for (Object o : joinPoint.getArgs()) {
            System.out.print(" " + o);
        }
        System.out.println();
        System.out.println("----beforeAllMethod-----------------end----");
    }

    @After("execution(* com.simpledev.spring_comm.aop.Cooker.*(..))")
    public void afterDoCook(JoinPoint joinPoint) {
        System.out.println("----after-----------------start--");
        System.out.println("--target:" + joinPoint.getTarget());
        System.out.println("--method:" + joinPoint.getSignature().getName());
        System.out.print("--params:" );
        for (Object o : joinPoint.getArgs()) {
            System.out.print(" " + o);
        }
        System.out.println();
        System.out.println("----after-----------------end----");
    }

    @AfterReturning(pointcut = "execution(* com.simpledev.spring_comm.aop.Cooker.*(..))", returning = "retVal")
    public void afterCookAndBeginEat(JoinPoint joinPoint, Object retVal) {
        System.out.println("----afterReturning-----------------start--");
        System.out.println("--target:" + joinPoint.getTarget());
        System.out.println("--method:" + joinPoint.getSignature().getName());
        System.out.print("--params:" );
        for (Object o : joinPoint.getArgs()) {
            System.out.print(" " + o);
        }
        System.out.println();
        System.out.println("--return value:" + retVal);
        System.out.println("----afterReturning-----------------end----");
    }

    @AfterThrowing(pointcut = "execution(* com.simpledev.spring_comm.aop.Cooker.*(..))", throwing = "ex")
    public void afterCookAndFire(JoinPoint joinPoint, Throwable ex) {
        System.out.println("----afterThrowing-----------------start--");
        System.out.println("--target:" + joinPoint.getTarget());
        System.out.println("--method:" + joinPoint.getSignature().getName());
        System.out.print("--params:" );
        for (Object o : joinPoint.getArgs()) {
            System.out.print(" " + o);
        }
        System.out.println();
        System.out.println("--exception:" + ex.getMessage());
        System.out.println("----afterThrowing-----------------end----");
    }

    @Around("execution(* com.simpledev.spring_comm.aop.Cooker.*(..))")
    public void aroundCook(ProceedingJoinPoint joinPoint) {
        System.out.println("----around-----------------start--");
        System.out.println("--target:" + joinPoint.getTarget());
        System.out.println("--method:" + joinPoint.getSignature().getName());
        System.out.print("--params:" );
        for (Object o : joinPoint.getArgs()) {
            System.out.print(" " + o);
        }
        System.out.println();
        try {
            joinPoint.proceed();
        } catch (Throwable e) {

        }
        System.out.println("----around-----------------end----");
    }
}
