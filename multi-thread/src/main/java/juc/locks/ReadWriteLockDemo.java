package juc.locks;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class ReadWriteLockDemo {

    static ReadWriteLock lock = new ReentrantReadWriteLock();

    static Lock readLock = lock.readLock();

    static Lock writeLock = lock.writeLock();

    private static int content;

    private static Random random = new Random();

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for(;;) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    read();
                }
            }).start();
        }

        new Thread(() -> {
            for (;;) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                write();
            }
        }).start();

        LockSupport.park();
    }

    private static void read() {
        readLock.lock();
        try {
            System.out.println(content);
        } finally {
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            content = random.nextInt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
