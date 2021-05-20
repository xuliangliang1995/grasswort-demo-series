package aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.stream.Stream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/10/23
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("aop")
public class AopTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AopTest.class);
        context.refresh();
        Stream.of(context.getBeanDefinitionNames())
                .forEach(System.out::println);
        aop.DomainService domainService = context.getBean(aop.DomainService.class);
        domainService.doSomeThing();
        domainService.doSomeThingQuickly();
    }


}
