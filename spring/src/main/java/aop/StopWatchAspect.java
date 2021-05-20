package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/10/23
 */
@Aspect
@Component
public class StopWatchAspect implements Ordered {

    @Pointcut("execution(public * aop.DomainService.*())")
    public void pointcut(){}


    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch(joinPoint.getTarget().getClass().getSimpleName());
        try {
            Signature signature = joinPoint.getSignature();
            stopWatch.start(signature.getDeclaringTypeName() + "." + signature.getName());
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            System.out.print("\n" + stopWatch.prettyPrint());
            System.out.print("耗时 : " + "[" + stopWatch.getLastTaskTimeMillis() + " ms]\n");
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
