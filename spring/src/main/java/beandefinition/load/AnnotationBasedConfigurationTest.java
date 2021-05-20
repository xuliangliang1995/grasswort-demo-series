package beandefinition.load;

import model.User;
import model.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.stream.Stream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/25
 */
public class AnnotationBasedConfigurationTest {

    private static Logger logger = LoggerFactory.getLogger(AnnotationBasedConfigurationTest.class);

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //GenericApplicationContext beanFactory = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        int loadCount = xmlBeanDefinitionReader.loadBeanDefinitions("com/grasswort/beans/beandefinition/configurationmeta/user-service-annotation-based.xml");
        logger.info("已解析 BeanDefinition 数量 : {}", loadCount);

        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(beanPostProcessor);
        beanFactory.addBeanPostProcessor(new PostPropertiesBeanPostProcessor());

        Stream.of(beanFactory.getBeanDefinitionNames())
                .forEach(System.out::println);

        UserService userService = beanFactory.getBean(UserService.class);
        beanFactory.getBeansOfType(User.class).values().forEach(userService::addUser);
        userService.listUser().forEach(System.out::println);
    }

    public static class PostPropertiesBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
        @Override
        public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
            if ("tom".equals(beanName)) {
                MutablePropertyValues tomPropertyValues = new MutablePropertyValues();
                tomPropertyValues.addPropertyValue("id", 1L);
                tomPropertyValues.addPropertyValue("name", "tom");
                tomPropertyValues.addPropertyValue("age", 18);
                return tomPropertyValues;
            } else if ("jerry".equals(beanName)) {
                MutablePropertyValues jerryPropertyValues = new MutablePropertyValues();
                jerryPropertyValues.addPropertyValue("id", 2L);
                jerryPropertyValues.addPropertyValue("name", "jerry");
                jerryPropertyValues.addPropertyValue("age", 8);
                return jerryPropertyValues;
            }
            return pvs;
        }
    }

}
