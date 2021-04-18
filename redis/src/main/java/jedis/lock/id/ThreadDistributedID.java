package jedis.lock.id;

import jedis.env.EnvInfo;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/4/18
 */
public class ThreadDistributedID {

    /**
     * 分布式线程 ID
     * @return
     */
    public static final String id() {
        return EnvInfo.LOCAL_MAC + "-" + EnvInfo.PROCESS_ID + "-" + Thread.currentThread().getId();
    }

    public static void main(String[] args) {
        System.out.println(ThreadDistributedID.id());
    }
}
