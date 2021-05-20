package databinder;

import model.User;
import typeconverter.PropertiesToUserConverter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;

import java.beans.*;
import java.util.Properties;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/4
 */
public class BeanWrapperTest {

    public static void main(String[] args) throws IntrospectionException {
        testBeanWrapperIntrospector();
        testPropertyAccessor();
        testBeanWrapper();
        testBeanWrapperWithConversionService();
    }

    private static void testBeanWrapperIntrospector() {
        BeanWrapper beanWrapper = new BeanWrapperImpl(User.class);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            System.out.printf("%s : %s, %s\n", propertyDescriptor.getName(),
                    propertyDescriptor.getReadMethod(),
                    propertyDescriptor.getWriteMethod());
        }
    }

    private static void testPropertyAccessor() {
        BeanWrapper beanWrapper = new BeanWrapperImpl(User.class);
        beanWrapper.setPropertyValue("id", "1");
        beanWrapper.setPropertyValue("name", "jerry");
        System.out.println("id : " + beanWrapper.getPropertyValue("id"));
        System.out.println("name : " + beanWrapper.getPropertyValue("name"));
        System.out.println(beanWrapper.getWrappedInstance());
    }

    private static void testBeanWrapperWithConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new PropertiesToUserConverter());

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(UserHolder.class);
        beanWrapper.setConversionService(conversionService);

        Properties properties = new Properties();
        properties.setProperty("id", "1");
        properties.setProperty("name", "jerry");
        properties.setProperty("age", "18");

        beanWrapper.setPropertyValue("user", properties);
        System.out.println(((UserHolder)beanWrapper.getWrappedInstance()).getUser());
    }

    private static void testBeanWrapper() {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(User.class);
        beanWrapper.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        beanWrapper.setPropertyValue("name", " jerry ");
        System.out.println(beanWrapper.getWrappedInstance());
    }


    static class UserHolder {
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
