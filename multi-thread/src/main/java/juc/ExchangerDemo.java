package juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                String val = exchanger.exchange("x");
                System.out.println("x : " + val);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                String val = exchanger.exchange("y");
                System.out.println("y : " + val);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
