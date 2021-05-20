package beandefinition.merge;

import model.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/31
 */
public class MergeBeanDefinitionTest {

    public static void main(String[] args) {
        BeanDefinition rootBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(User.class)
                .addPropertyValue("id", 1L)
                .getBeanDefinition();

        BeanDefinition childBeanDefinition = BeanDefinitionBuilder.childBeanDefinition("root")
                .addPropertyValue("name", "tom")
                .addPropertyValue("age", 18)
                .getBeanDefinition();

        RootBeanDefinition mergedBeanDefinition = new RootBeanDefinition((RootBeanDefinition) rootBeanDefinition);
        mergedBeanDefinition.overrideFrom(childBeanDefinition);

        mergedBeanDefinition.getPropertyValues().forEach(propertyValue ->
                System.out.println(String.format("%s:%s", propertyValue.getName(), propertyValue.getValue())));

    }
}
