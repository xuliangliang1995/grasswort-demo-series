package beandefinition.configurationmeta;

import model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collection;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/25
 */
@Configuration
@Import(UsersConfiguration.class)
public class UserServiceConfiguration {

    @Bean
    public IdGenerator idGenerator() {
        return new model.UserIdGenerator();
    }

    @Bean
    public model.UserRepository userRepository(IdGenerator idGenerator) {
        model.UserRepository userRepository = new model.UserRepository();
        userRepository.setIdGenerator(idGenerator);
        return userRepository;
    }

    @Bean
    public model.UserService userService(model.UserRepository userRepository, Collection<User> users) {
        model.UserService userService = new model.UserService(userRepository);
        users.forEach(userService::addUser);
        return userService;
    }
}
