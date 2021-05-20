package beandefinition.load;

import model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.stream.Stream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/9
 */
public class AnnotatedBeanDefinitionReaderTest {

    private static Logger logger = LoggerFactory.getLogger(AnnotatedBeanDefinitionReaderTest.class);

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);

        reader.register(UserRepository.class);

        logger.info("BeanDefinition Count : {}", beanFactory.getBeanDefinitionCount());
        Stream.of(beanFactory.getBeanDefinitionNames())
                .forEach(System.out::println);

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userRepository");
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            logger.info("userRepository 注解元数据 : ");
            AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
            metadata.getAnnotationTypes().forEach(type -> System.out.println("@" + type));
        }
    }
}
