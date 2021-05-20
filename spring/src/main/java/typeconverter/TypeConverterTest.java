package typeconverter;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Properties;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/5
 */
public class TypeConverterTest {

    private static Logger logger = LoggerFactory.getLogger(TypeConverterTest.class);

    public static void main(String[] args) {
        SimpleTypeConverter simpleTypeConverter = new SimpleTypeConverter();
        simpleTypeConverter.registerCustomEditor(User.class, new StringToUserPropertyEditor());
        simpleTypeConverter.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        // PropertyEditor
        User user = simpleTypeConverter.convertIfNecessary("1-jerry-18", User.class);
        logger.info("转化后: {}", user);

        String trimmerName = simpleTypeConverter.convertIfNecessary(" jerry", String.class);
        logger.info("转化后: {}", trimmerName);

        // Converter or GenericConverter or ConverterFactory
        Properties properties = new Properties();
        properties.setProperty("id", "1");
        properties.setProperty("name", "jerry");
        properties.setProperty("age", "18");

        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new PropertiesToUserConverter());
        logger.info("是否可以将 Properties 转化为 User : {}", conversionService.canConvert(Properties.class, User.class));
        User user2 = conversionService.convert(properties, User.class);
        logger.info("转换后: {}", user2);
    }
}
