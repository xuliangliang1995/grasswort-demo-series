package juc.locks;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class ReentrantLockDemo2 {

    static ReentrantLock lock = new ReentrantLock();

    static Condition needProduce = lock.newCondition();

    static Condition canConsume = lock.newCondition();

    static Queue<Object> queue = new ConcurrentLinkedDeque<>();

    public static void main(String[] args) {
        new Thread(ReentrantLockDemo2::produce).start();
        consume();
    }

    private static void produce() {
        lock.lock();
        try {
            while (true) {
                try {
                    for (int i = 0; i < 5; i++) {
                        queue.add(new Object());
                    }
                    canConsume.signalAll();
                    needProduce.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private static void consume() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        try {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Object obj = queue.poll();

                System.out.println(obj);
                if (obj == null) {
                    needProduce.signalAll();
                    try {
                        canConsume.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
