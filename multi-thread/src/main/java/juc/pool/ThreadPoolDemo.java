package juc.pool;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class ThreadPoolDemo {

    static int corePoolSize = 5;
    static int maxPoolSize = 10;

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
            1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10),
                new DefaultThreadFactory(),
                new DefaultRejectedExecutionHandler());

        for (int i = 0; i < 100; i++) {
            executorService.execute(ThreadPoolDemo::task);
        }

        LockSupport.park();
    }

    private static void task() {
        try {
            System.out.println("executing .");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("线程池繁忙，已拒绝：" + r);
            System.out.println("active count : " + executor.getActiveCount());
            System.out.println("completed task count : " + executor.getCompletedTaskCount());
            System.out.println("task count : " + executor.getTaskCount());
        }
    }

    static class DefaultThreadFactory implements ThreadFactory {


        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }
}
