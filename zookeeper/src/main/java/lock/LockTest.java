package lock;

import org.apache.zookeeper.ZooKeeper;
import util.ZkUtil;

import javax.swing.plaf.IconUIResource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/16
 */
public class LockTest {

    public static void main(String[] args) {
        int threads = 100;
        CountDownLatch latch = new CountDownLatch(threads);
        ZooKeeper zk = ZkUtil.getZk();

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                ZkLockWatchCallback lockCallback = new ZkLockWatchCallback("/lock");
                lockCallback.setZk(zk);
                lockCallback.tryLock();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lockCallback.unlock();
                latch.countDown();
                System.out.println(latch.getCount());
            }, String.valueOf(i)).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
