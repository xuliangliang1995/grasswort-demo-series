package beandefinition.configurationmeta;

import model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/13
 */
@Configuration
@PropertySource("classpath:beandefinition/configurationmeta/user-beans.properties")
public class UsersConfiguration {

    @Bean
    public User tom(@Value("${tom.id}") Long id,
                    @Value("${tom.name}") String name,
                    @Value("${tom.age}") Integer age) {
        User tom = new User();
        tom.setId(id);
        tom.setAge(age);
        tom.setName(name);
        return tom;
    }

    @Bean
    public User jerry(@Value("${jerry.id}") Long id,
                    @Value("${jerry.name}") String name,
                    @Value("${jerry.age}") Integer age) {
        User jerry = new User();
        jerry.setId(id);
        jerry.setAge(age);
        jerry.setName(name);
        return jerry;
    }
}
