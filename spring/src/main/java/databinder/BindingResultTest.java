package databinder;

import model.User;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class BindingResultTest {

    public static void main(String[] args) {
        BeanPropertyBindingResult beanPropertyBindingResult =
                new BeanPropertyBindingResult(new User(), "user", true, 256);

        beanPropertyBindingResult.addError(new ObjectError("user", "invalid user"));
        beanPropertyBindingResult.addError(new FieldError("user", "name", "用户名不能为空"));
        new DefaultBindingErrorProcessor().processMissingFieldError("age", beanPropertyBindingResult);

        beanPropertyBindingResult.getAllErrors().stream()
                .forEach(objectError -> {
                    System.out.println("objectName : " + objectError.getObjectName());
                    System.out.println("code : " + objectError.getCode());
                    System.out.println("defaultMessage : " + objectError.getDefaultMessage());
                    System.out.println("\n");
                });

    }

}
