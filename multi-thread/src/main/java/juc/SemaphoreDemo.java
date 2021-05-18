package juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < 10; i++) {
            final int num = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是 " + num);
                semaphore.release();
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
