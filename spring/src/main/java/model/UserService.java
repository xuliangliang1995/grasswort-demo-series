package model;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/5
 */
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public Long addUser(User user) {
        return userRepository.addUser(user);
    }

    /**
     * 查询用户
     *
     * @param userId
     * @return
     */
    @Override
    public User selectUser(Long userId) {
        return userRepository.selectUser(userId);
    }

    /**
     * 用户列表
     *
     * @return
     */
    @Override
    public List<User> listUser() {
        return userRepository.listUser();
    }

}
