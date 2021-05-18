package juc;

import java.time.Instant;
import java.util.Calendar;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class DelayQueueDemo {

    public static void main(String[] args) {
        DelayQueue<DelayJob> delayJobs = new DelayQueue<>();
        delayJobs.offer(new DelayJob(3, "吃饭"));
        delayJobs.offer(new DelayJob(1, "喝酒"));
        delayJobs.offer(new DelayJob(5, "做作业"));

        while (true) {
            try {
                DelayJob delayJob = delayJobs.take();
                System.out.println(delayJob);
                if (delayJobs.isEmpty()) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class DelayJob implements Delayed {

        private long nano;

        private String work;

        public DelayJob(int seconds, String work) {
            this.nano = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
            this.work = work;
        }

        @Override
        public String toString() {
            return "DelayJob{" +
                    "nano=" + nano +
                    ", work='" + work + '\'' +
                    '}';
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long nano = this.nano - System.nanoTime();
            switch (unit) {
                case NANOSECONDS:
                    return nano;
                case MICROSECONDS:
                    return nano / 1000;
                case MILLISECONDS:
                    return nano / 1000 / 1000;
                case SECONDS:
                    return nano / 1000 / 1000 / 1000;
                case MINUTES:
                    return nano / 1000 / 1000 / 1000 / 60;
                case HOURS:
                    return nano / 1000 / 1000 / 1000 / 60 / 360;
                case DAYS:
                    return nano / 1000 / 1000 / 1000 / 60 / 360 / 24;
                default:
                    return nano;
            }
        }

        @Override
        public int compareTo(Delayed o) {
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return diff == 0L ? 0 : (diff > 0 ? 1 : -1);
        }
    }
}
