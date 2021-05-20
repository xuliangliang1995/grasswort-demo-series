package model;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/8/5
 */
@Component
public class UserIdGenerator implements IdGenerator {

    private final AtomicLong atomicLong = new AtomicLong();
    /**
     * 生成 ID
     *
     * @return
     */
    @Override
    public Long generateId() {
        return atomicLong.incrementAndGet();
    }
}
