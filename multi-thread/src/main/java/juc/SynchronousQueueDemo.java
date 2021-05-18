package juc;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) {
        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();


        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    synchronousQueue.put(i++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(synchronousQueue.poll());;
            }

        }).start();

        LockSupport.park();
    }
}
