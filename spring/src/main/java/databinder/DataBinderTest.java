package databinder;

import model.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.*;

public class DataBinderTest {

    public static void main(String[] args) {
        User user = new User();
        DataBinder dataBinder = new DataBinder(user, "user");
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue("id", 10);
        propertyValues.addPropertyValue("name", " jerry");
        propertyValues.addPropertyValue("age", 18);

        dataBinder.bind(propertyValues);
        System.out.println(user);

    }
}
