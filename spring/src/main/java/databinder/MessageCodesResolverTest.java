package databinder;

import org.springframework.validation.DefaultMessageCodesResolver;

import java.util.stream.Stream;

public class MessageCodesResolverTest {

    public static void main(String[] args) {
        DefaultMessageCodesResolver messageCodesResolver = new DefaultMessageCodesResolver();
        messageCodesResolver.setPrefix("check-");
        String[] codes = messageCodesResolver.resolveMessageCodes("invalid", "user");
        Stream.of(codes)
                .forEach(System.out::println);


    }
}
