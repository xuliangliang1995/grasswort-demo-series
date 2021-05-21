package circular;

import aop.PessimisticLockingFailureException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/21
 */
@Aspect
@Component
public class ProxyAspect {

    @Pointcut("execution(* circular.*.doSomething())")
    public void pointcut() {}

    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
