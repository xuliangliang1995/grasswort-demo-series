package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/7/30
 */
@Qualifier
public class Test {
    @Autowired
    private Desc desc;


    public static void main(String[] args) {
        AnnotationMetadata annotationMetadata = AnnotationMetadata.introspect(Test.class);
        Set<String> annotationNames = annotationMetadata.getAnnotationTypes();
        annotationNames.forEach(System.out::println);
    }


    static class Desc {
        String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}
