package demo.cglib;

import model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.util.StopWatch;

/**
 * @author xuliang
 * @Date 2020/9/21
 * @Description
 */
public class ProxyTest {

    public static void main(String[] args) {
        testAspectJProxyFactory();
        testEnhancer();
    }

    private static void testAspectJProxyFactory() {
        User user = new User();
        user.setName("jerry");
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(user);
        proxyFactory.addAspect(UserAspect.class);
        User userProxy = proxyFactory.getProxy();
        System.out.println(userProxy.getName());
    }

    private static void testEnhancer() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(User.class);
        enhancer.setUseCache(false);
        enhancer.setCallback((MethodInterceptor) (obj, method, args1, methodProxy) -> {
            System.out.println("pre processor");
            return methodProxy.invokeSuper(obj, args1);
        });
        Object userProxyInstance = enhancer.create();
        System.out.println(User.class.isAssignableFrom(userProxyInstance.getClass()));
        ((User) userProxyInstance).setName("jerry");
        System.out.println(userProxyInstance);
    }

    @Aspect
    public static class UserAspect {

        @Pointcut("execution(public String model.User.getName())")
        public void preGetName() {}

        @Around("preGetName()")
        public Object preGetNameAdvice(ProceedingJoinPoint jp) throws Throwable {
            StopWatch stopWatch = new StopWatch(jp.getTarget().getClass().getSimpleName());
            try {
                Signature signature = jp.getSignature();
                stopWatch.start(signature.getDeclaringTypeName() + "." + signature.getName());
                return jp.proceed();
            } finally {
                stopWatch.stop();
                System.out.println(stopWatch.prettyPrint());
            }
        }

    }

}
