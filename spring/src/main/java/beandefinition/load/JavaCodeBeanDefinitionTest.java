package beandefinition.load;

import model.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.stream.Stream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/5
 */
public class JavaCodeBeanDefinitionTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinition userRepositoryBd = BeanDefinitionBuilder.genericBeanDefinition(model.UserRepository.class)
                .addAutowiredProperty("idGenerator")
                .getBeanDefinition();

        BeanDefinition userServiceBd = BeanDefinitionBuilder.genericBeanDefinition(model.UserService.class)
                //.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT)
                .addConstructorArgReference("userRepository")
                .getBeanDefinition();

        beanFactory.registerSingleton("userIdGenerator", new model.UserIdGenerator());
        beanFactory.registerBeanDefinition("userRepository", userRepositoryBd);
        beanFactory.registerBeanDefinition("userService", userServiceBd);

        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof model.UserService) {
                    return model.UserServiceProxy.getInstance((model.IUserService) bean);
                }
                return bean;
            }
        });

        model.IUserService userService = beanFactory.getBean("userService", model.IUserService.class);

        Stream.of(new User("Jerry", 8), new User("Tom", 18))
                .forEach(userService::addUser);

        userService.listUser().stream().forEach(System.out::println);
    }

}
