package juc;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/18
 */
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) {
        PriorityBlockingQueue<Score> queue = new PriorityBlockingQueue<>();

        new Thread(() -> {
            queue.put(new Score(50, "jerry"));
            queue.put(new Score(40, "tom"));
            queue.put(new Score(60, "jack"));
        }).start();

        while (true) {
            try {
                Score score = queue.take();
                System.out.println(score);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class Score implements Comparable {

        private int score;

        private String name;

        public Score(int score, String name) {
            this.score = score;
            this.name = name;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Score) {
                return this.score - ((Score) o).score;
            }
            return 1;
        }

        @Override
        public String toString() {
            return "Score{" +
                    "score=" + score +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
