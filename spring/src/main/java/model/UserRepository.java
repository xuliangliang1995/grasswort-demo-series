package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/5
 */
@Repository
public class UserRepository {

    private final List<User> userList = new ArrayList<>(5);

    private IdGenerator idGenerator;

    /**
     * setter dependency injection
     * @param idGenerator
     */
    @Autowired(required = false)
    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    public Long addUser(User user) {
        user.setId(idGenerator.generateId());
        userList.add(user);
        return user.getId();
    }

    /**
     * 查找用户
     * @param userId
     * @return
     */
    public @Nullable User selectUser(Long userId) {
        return userList.stream().filter(user -> Objects.equals(user.getId(), userId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 用户列表
     * @return
     */
    public List<User> listUser() {
        return userList;
    }
}
