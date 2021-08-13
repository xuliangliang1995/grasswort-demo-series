package juc.locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/8/9
 */
public class SynchronizedDemo {

    private static final Object lock = new Object();

    private static final Queue<String> queue = new LinkedList<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(SynchronizedDemo::consume);
        executorService.execute(SynchronizedDemo::produce);
    }

    public static void consume() {
        synchronized (lock) {
            while (true) {
                if (queue.isEmpty()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(queue.poll());
                    lock.notify();
                }
            }
        }
    }

    public static void produce() {
        int i = 0;
        synchronized (lock) {
            while (true) {
                if (queue.isEmpty()) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 5; j++) {
                        queue.offer("food_" + i++);
                    }
                    lock.notify();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
