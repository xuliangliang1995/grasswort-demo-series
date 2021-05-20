package beans.create;

import beans.IocContainer;
import model.UserRepository;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/28
 */
public class UserRepositoryCreateTest {

    public static void main(String[] args) {
        BeanFactory beanFactory = IocContainer.start();
        UserRepository userRepository = beanFactory.getBean(UserRepository.class);
        System.out.println(userRepository);
    }
}
