package typeconverter;

import model.User;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;

import java.util.Properties;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/5
 */
public class PropertiesToUserConverter implements Converter<Properties, User>, ConditionalConverter {

    @Override
    public User convert(Properties source) {
        User user = new User();
        user.setId(Long.valueOf(source.getProperty("id", "0L")));
        user.setName(String.valueOf(source.getOrDefault("name", "")));
        user.setAge(Integer.valueOf(source.getProperty("age", "0")));
        return user;
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getObjectType().isAssignableFrom(Properties.class)
                && targetType.getObjectType().isAssignableFrom(User.class);
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("id", "1");
        properties.setProperty("name", "jerry");
        properties.setProperty("age", "8");
        PropertiesToUserConverter converter = new PropertiesToUserConverter();
        boolean matched = converter.matches(TypeDescriptor.forObject(properties), TypeDescriptor.valueOf(User.class));
        if (matched) {
            User user = converter.convert(properties);
            System.out.println(user);
        }
    }
}
