package beandefinition.api;

import model.User;
import model.UserIdGenerator;
import model.UserRepository;
import model.UserService;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/26
 */
public class GenericBeanDefinitionTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        GenericBeanDefinition userBeanDefinition = new GenericBeanDefinition();
        userBeanDefinition.setBeanClass(User.class);
        MutablePropertyValues tomPropertyValues = new MutablePropertyValues();
        tomPropertyValues.addPropertyValue("id", 1);
        tomPropertyValues.addPropertyValue("name", "tom");
        tomPropertyValues.addPropertyValue("age", 18);
        userBeanDefinition.setPropertyValues(tomPropertyValues);
        beanFactory.registerBeanDefinition("tom", userBeanDefinition);

        GenericBeanDefinition idGeneratorBeanDefinition = new GenericBeanDefinition();
        idGeneratorBeanDefinition.setBeanClass(UserIdGenerator.class);
        beanFactory.registerBeanDefinition("userIdGenerator", idGeneratorBeanDefinition);

        GenericBeanDefinition userRepositoryBeanDefinition = new GenericBeanDefinition();
        userRepositoryBeanDefinition.setBeanClass(UserRepository.class);
        MutablePropertyValues userRepositoryPropertyValues = new MutablePropertyValues();
        userRepositoryPropertyValues.addPropertyValue("idGenerator",
                // 引用容器中命名为 idGenerator 的 Bean
                new RuntimeBeanReference("userIdGenerator"));
        userRepositoryBeanDefinition.setPropertyValues(userRepositoryPropertyValues);
        beanFactory.registerBeanDefinition("userRepository", userRepositoryBeanDefinition);

        GenericBeanDefinition userServiceBeanDefinition = new GenericBeanDefinition();
        userServiceBeanDefinition.setBeanClass(UserService.class);
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0, new RuntimeBeanReference("userRepository"));
        userServiceBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
        beanFactory.registerBeanDefinition("userService", userServiceBeanDefinition);

        UserService userService = beanFactory.getBean(UserService.class);
        User tom = beanFactory.getBean("tom", User.class);
        userService.addUser(tom);
        userService.listUser().forEach(System.out::println);
    }
}
