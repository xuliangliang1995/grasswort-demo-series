package beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.stream.Stream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/28
 */
public class IocContainer {

    public static Logger logger = LoggerFactory.getLogger(IocContainer.class);

    public static BeanFactory start() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        int loadCount = xmlBeanDefinitionReader.loadBeanDefinitions(
                "com/grasswort/beans/beandefinition/configurationmeta/user-service.xml");

        logger.info("已解析 BeanDefinition 数量 : {}", loadCount);

        Stream.of(beanFactory.getBeanDefinitionNames())
                .forEach(System.out::println);
        return beanFactory;
    }
}
