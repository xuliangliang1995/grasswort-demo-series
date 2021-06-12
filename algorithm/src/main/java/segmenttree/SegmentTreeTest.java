package segmenttree;

import sort.util.IntArrayUtil;

import java.util.Random;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/12
 */
public class SegmentTreeTest {

    public static void main(String[] args) {
        int testCount = 100;
        int addOrUpdateCount = 100;
        Random random = new Random();
        for (int i = 0; i < testCount; i++) {
            int[] arr = IntArrayUtil.randomArray(100, 0, 100);
            SegmentTree segmentTree = new SegmentTree(arr);
            Right right = new Right(arr);

            for (int j = 0; j < addOrUpdateCount; j++) {
                int dice = random.nextInt(10);
                int[] lr = {random.nextInt(arr.length), random.nextInt(arr.length)};
                int L = Math.min(lr[0], lr[1]);
                int R = Math.max(lr[0], lr[1]);
                int C = random.nextInt(100);
                if (dice < 5) {
                    segmentTree.add(L, R, C);
                    right.add(L, R, C);
                } else {
                    segmentTree.update(L, R, C);
                    right.update(L, R, C);
                }
                assert segmentTree.sum(L, R) == right.sum(L, R);
            }
        }

    }

    static class Right {
        private int[] arr;

        public Right(int[] arr) {
            this.arr = arr;
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public int sum(int L, int R) {
            int sum = 0;
            for (int i = L; i <= R; i++) {
                sum += arr[i];
            }
            return sum;
        }
    }
}
