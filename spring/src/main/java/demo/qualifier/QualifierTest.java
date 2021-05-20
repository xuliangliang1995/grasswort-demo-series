package demo.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/7/24
 */
public class QualifierTest {

    public static void main(String[] args) throws NoSuchMethodException {
        Method methodForSetStudent = Teacher.class.getDeclaredMethod("setStudent", Student.class);

        MethodParameter methodParameter = new MethodParameter(methodForSetStudent, 0);

        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(methodParameter, true);

        BeanDefinition studentBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Student.class)
                .addPropertyValue("name", "Tom")
                .addPropertyValue("age", 18)
                .getBeanDefinition();

        BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(studentBeanDefinition, "tom");

        QualifierAnnotationAutowireCandidateResolver resolver = new QualifierAnnotationAutowireCandidateResolver();
        System.out.println(resolver.isAutowireCandidate(beanDefinitionHolder, dependencyDescriptor));
    }

    class Teacher {

        private Student student;

        /**
         * 只有优等生才有资格注入进来
         * @param student
         */
        public void setStudent(@ExcellentStudent @Autowired Student student) {
            this.student = student;
        }
    }

    @ExcellentStudent
    class Student {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }


    /**
     * 优秀生
     */
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Qualifier
    @interface ExcellentStudent {
        @AliasFor(annotation = Qualifier.class)
        String value() default "";
    }
}
