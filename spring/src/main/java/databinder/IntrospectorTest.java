package databinder;

import model.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/12
 */
public class IntrospectorTest {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo userBeanInfo = Introspector.getBeanInfo(User.class);
        PropertyDescriptor[] propertyDescriptors  = userBeanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        }
    }
}
