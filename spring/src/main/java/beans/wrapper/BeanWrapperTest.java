package beans.wrapper;

import model.User;
import model.UserIdGenerator;
import model.UserRepository;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/31
 */
public class BeanWrapperTest {

    public static void main(String[] args) {
        User user = new User();
        BeanWrapper userWrapper = new BeanWrapperImpl(user);
        userWrapper.setPropertyValue("name", "jerry");
        userWrapper.setPropertyValue("age", 18);
        System.out.println(user);

        UserRepository userRepository = new UserRepository();
        BeanWrapper userRepositoryWrapper = new BeanWrapperImpl(userRepository);
        userRepositoryWrapper.setPropertyValue("idGenerator", new UserIdGenerator());
        userRepository.addUser(user);
        userRepository.listUser().stream().forEach(System.out::println);
    }
}
