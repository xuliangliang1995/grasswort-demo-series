package model;

import java.util.List;

/**
 * @author xuliangliang
 * @Description 用户服务接口
 * @Date 2020/8/9
 */
public interface IUserService {
    /**
     * 添加用户
     * @param user
     * @return
     */
    Long addUser(User user);

    /**
     * 查询用户
     * @param userId
     * @return
     */
    public User selectUser(Long userId);

    /**
     * 用户列表
     * @return
     */
    public List<User> listUser();
}
