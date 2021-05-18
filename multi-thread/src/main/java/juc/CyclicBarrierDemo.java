package juc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> System.out.println("所有人员已就位"));

        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            final int num = i;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(3));
                    System.out.println(num + "已就位");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        try {
            cyclicBarrier.await();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("所有人员已就位");
    }
}
