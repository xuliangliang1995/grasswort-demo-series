package juc.pool;

import javafx.concurrent.Task;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class ForkJoinPoolDemo {

    static Random random = new Random();

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        MyTask task1 = new MyTask();
        MyTask task2 = new MyTask();

        forkJoinPool.execute(task1);
        forkJoinPool.execute(task2);
        System.out.println("computing .");

        System.out.println(task1.join());
        System.out.println(task2.join());

    }

    static class MyTask extends RecursiveTask<Long> {

        /**
         * The main computation performed by this task.
         *
         * @return the result of the computation
         */
        @Override
        protected Long compute() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return random.nextLong();
        }
    }
}
