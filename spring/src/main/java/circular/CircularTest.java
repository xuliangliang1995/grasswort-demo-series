package circular;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/17
 */
public class CircularTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(CircularConfiguration.class);
        /// 默认允许循环依赖,便不需要 @Lazy 注解
        // context.setAllowCircularReferences(false);
        context.refresh();
        Stream.of(context.getBeanDefinitionNames())
                .forEach(System.out::println);

        CircularDependency001 circularDependency001 = context.getBean(CircularDependency001.class);
        CircularDependency002 circularDependency002 = context.getBean(CircularDependency002.class);
        System.out.println(circularDependency001);
        System.out.println(circularDependency002);
    }
}
