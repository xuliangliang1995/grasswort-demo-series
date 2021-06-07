package others;

import java.util.LinkedList;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/7
 */
public class RollingWindowMaxValueProblem {
    /**
     * 假设一个固定大小为 W 的窗口，依次划过数组 arr
     * 返回每一次划出状况的最大值
     * 例如： arr = [4, 3, 5, 4, 3, 3, 6, 7], W = 3
     * 返回： [5, 5, 5, 4, 6, 7]
     */

    public static void main(String[] args) {
        int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
        int w = 3;
        RollingWindow window = new RollingWindow(arr, w);
        while (window.right());
    }


    static class RollingWindow {
        private int[] arr;
        private final int windowSize;
        private LinkedList<Unit> twoWayQueue = new LinkedList<>();
        private int left;
        private int right;

        public RollingWindow(int[] arr, int windowSize) {
            this.arr = arr;
            this.windowSize = windowSize;
            for (int i = 0; i < windowSize - 1; i++) {
                rightCursorMove();
            }
            printCurMaxValue();
        }

        /**
         * 右移
         */
        public boolean right() {
            boolean canMove = rightCursorMove();
            if (canMove) {
                leftCursorMove();
                printCurMaxValue();
            }
            return canMove;
        }

        public boolean leftCursorMove() {
            left++;
            popInvalidUnit();
            return true;
        }

        public boolean rightCursorMove() {
            if (right == arr.length - 1) {
                return false;
            }
            right++;
            enqueueNewUnit(new Unit(right, arr[right]));
            return true;
        }

        private void printCurMaxValue() {
            for (int i = left; i <= right; i++) {
                System.out.print(arr[i] + ",");
            }
            System.out.println("当前窗口最大值：" + twoWayQueue.getFirst().value);
        }

        private void popInvalidUnit() {
            Unit firstUnit = twoWayQueue.peekFirst();
            while (firstUnit != null && firstUnit.index < left) {
                twoWayQueue.removeFirst();
                firstUnit = twoWayQueue.peekFirst();
            }
        }

        private void enqueueNewUnit(Unit unit) {
            Unit lastUnit = twoWayQueue.peekLast();
            while (lastUnit != null && lastUnit.value <= unit.value) {
                twoWayQueue.removeLast();
                lastUnit = twoWayQueue.peekLast();
            }
            twoWayQueue.addLast(unit);
        }

        static class Unit {
            private int index;
            private int value;

            public Unit(int index, int value) {
                this.index = index;
                this.value = value;
            }
        }
    }
}
