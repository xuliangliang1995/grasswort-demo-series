package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/10/23
 */
@Aspect
@Component
public class RetryAspect implements Ordered {

    private final int maxRetries = 3;

    @Pointcut("@annotation(aop.Idempotent)")
    public void idempotent() {}

    @Around("idempotent()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        int numAttempts = 0;
        PessimisticLockingFailureException lockFailureException;
        do {
            numAttempts++;
            try {
                return joinPoint.proceed();
            } catch(PessimisticLockingFailureException ex) {
                lockFailureException = ex;
            }
        } while(numAttempts <= this.maxRetries);
        throw lockFailureException;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
