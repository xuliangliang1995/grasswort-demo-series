package juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(countDownLatch::countDown).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
