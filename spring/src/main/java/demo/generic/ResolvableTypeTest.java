package demo.generic;

import org.springframework.core.ResolvableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/11
 */
public class ResolvableTypeTest {

    private HashMap<String, Object> map;

    public static void main(String[] args) throws NoSuchFieldException {
        ResolvableType stringListResolvableType = ResolvableType.forClass(StringList.class);
        Stream.of(stringListResolvableType.asCollection().getGenerics())
                .forEach(System.out::println);

        ResolvableType customMapResolvableType = ResolvableType.forClass(CustomMap.class);
        Stream.of(customMapResolvableType.asMap().getGenerics())
                .forEach(System.out::println);

        ResolvableType customFunctionResolvableType = ResolvableType.forClass(CustomFunction.class);
        Stream.of(customFunctionResolvableType.as(Function.class).getGenerics())
                .forEach(System.out::println);

        ResolvableType fieldMapResolvableType = ResolvableType.forField(ResolvableTypeTest.class.getDeclaredField("map"));
        Stream.of(fieldMapResolvableType.getGenerics())
                .forEach(System.out::println);
    }

    static class StringList extends ArrayList<String> {}
    static class CustomMap extends HashMap<String, String>{}
    static class CustomFunction implements Function<CustomMap, StringList> {
        @Override
        public StringList apply(CustomMap customMap) {
            return null;
        }
    }
}
