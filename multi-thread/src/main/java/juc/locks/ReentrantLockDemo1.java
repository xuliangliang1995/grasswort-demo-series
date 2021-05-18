package juc.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class ReentrantLockDemo1 {

    static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> recursionInvoke(2)).start();
        }

        LockSupport.park();
    }

    private static void recursionInvoke(int i) {
        lock.lock();
        System.out.println("do something . ");
        try {
            TimeUnit.SECONDS.sleep(1);
            if (i == 0) {
                return;
            }
            recursionInvoke(--i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
