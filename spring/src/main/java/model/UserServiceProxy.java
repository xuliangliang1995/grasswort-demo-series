package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/9
 */
public class UserServiceProxy implements InvocationHandler {

    private static Logger logger = LoggerFactory.getLogger(UserServiceProxy.class);

    private final IUserService iUserService;

    public UserServiceProxy(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    public static IUserService getInstance(IUserService iUserService) {
        return (IUserService) Proxy.newProxyInstance(iUserService.getClass().getClassLoader(),
                iUserService.getClass().getInterfaces(),
                new UserServiceProxy(iUserService));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("invoke method {}.{} [args]: {}",
                iUserService.getClass().getSimpleName(),
                method.getName(),
                null == args
                        ? ""
                        : Stream.of(args).map(arg -> "\n" + arg).reduce("", (a, b) -> a + b));

        Object obj = method.invoke(iUserService, args);

        logger.info("result : {}", obj);
        return obj;
    }
}
