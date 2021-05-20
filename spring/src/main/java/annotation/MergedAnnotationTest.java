package annotation;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/9/14
 */
public class MergedAnnotationTest {

    public static void main(String[] args) {
        Consumer<AccessibleObject> consumer = accessibleObject -> {
            MergedAnnotations mergedAnnotations = MergedAnnotations.from(accessibleObject);
            for (MergedAnnotation<Annotation> mergedAnnotation : mergedAnnotations) {
                Map<String, Object> attributesMap = mergedAnnotation.asMap(m -> new AnnotationAttributes(m.getType()));
                System.out.printf("%s%s\n", mergedAnnotation.getType(), attributesMap);
            }
        };
        ReflectionUtils.doWithLocalFields(TestDTO.class, consumer::accept);
        ReflectionUtils.doWithLocalMethods(TestDTO.class, consumer::accept);


    }

    static class TestDTO {

        @Qualifier("user")
        @Autowired(required = false)
        private User user;

        private Integer age;

        @Autowired(required = true)
        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "TestDTO{" +
                    "user=" + user +
                    ", age=" + age +
                    '}';
        }
    }
}
