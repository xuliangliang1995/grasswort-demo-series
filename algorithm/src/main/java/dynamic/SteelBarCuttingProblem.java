package dynamic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author 🌺xuliangliang🌺
 * @Description 动态规划(钢条切割问题)
 * 注: 实现使用了全局变量存储信息,线程不安全.
 * @Date 2021/5/29
 */
public class SteelBarCuttingProblem {

    /**
     * 钢条长度与价格对照表(可以用数组,此处为了便于理解使用 map)
     */
    final static Map<Integer, Integer> lenPriceMap = new HashMap<>();
    /**
     * 切割方案记录处(长度为 i 时,切割长度记录)
     */
    final static int[] cutProgram = new int[11];
    /**
     * 长度和最大收益存储处
     */
    final static Result[] lenMaxPrice = new Result[11];

    static {
        lenPriceMap.put(0, 0);
        lenPriceMap.put(1, 1);
        lenPriceMap.put(2, 5);
        lenPriceMap.put(3, 8);
        lenPriceMap.put(4, 9);
        lenPriceMap.put(5, 10);
        lenPriceMap.put(6, 17);
        lenPriceMap.put(7, 17);
        lenPriceMap.put(8, 20);
        lenPriceMap.put(9, 24);
        lenPriceMap.put(10, 30);
        lenMaxPrice[0] = new Result(0);
        lenMaxPrice[0].setPrice(0);
    }

    /**
     * 长度为 i 的钢筋,如何切割实现利益最大化.(假设切割工序无成本)
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Result cutResult = memoizedBottomUpFunc(i);
            /// 自顶向下需要递归
            // memoizedTopDownFunc(length);
            // System.out.println("MAX-PRICE : " + cutResult.getPrice());
            cutResult.printCutProgram();
        }
    }

    /**
     * 自顶向下求解
     * @param len
     * @return
     */
    public static Result memoizedTopDownFunc(final int len) {
        if (lenMaxPrice[len] != null) {
            return lenMaxPrice[len];
        }
        Result result = new Result(len);
        int q = Integer.MIN_VALUE;
        for (int i = 1; i <= len; i++) {
            int subQ = lenPriceMap.get(i) + memoizedTopDownFunc(len - i).getPrice();
            if (q < subQ) {
                q = subQ;
                cutProgram[len] = i;
            }
        }
        result.setPrice(q);
        lenMaxPrice[len] = result;
        return result;
    }

    /**
     * 自底向上求解
     * @param len
     * @return
     */
    public static Result memoizedBottomUpFunc(final int len) {
        for (int i = 1; i <= len; i++) {
            Result result = new Result(i);
            int q = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                int subQ = lenPriceMap.get(j) + lenMaxPrice[i - j].getPrice();
                if (q < subQ) {
                    q = subQ;
                    cutProgram[i] = j;
                }
            }
            result.setPrice(q);
            lenMaxPrice[i] = result;
        }
        return lenMaxPrice[len];
    }

    /**
     * 结果
     */
    static class Result {
        /**
         * 钢条长度
         */
        final int length;
        /**
         * 最大切割价值
         */
        int price;

        public Result(int length) {
            this.length = length;
        }

        /**
         * 打印切割方案
         */
        public void printCutProgram() {
            System.out.print("切割最优解 : ");
            Queue<Integer> queue = new LinkedList<>();
            int l = this.length;
            while (l > 0) {
                int cut = cutProgram[l];
                if (cut == 0) {
                    cut = l;
                }
                queue.add(cut);
                l = l - cut;
            }
            String result = null;
            while (! queue.isEmpty()) {
                int subLen = queue.poll();;
                if (result == null) {
                    result = subLen + "[" + lenPriceMap.get(subLen) + "]";
                } else {
                    result = result + " + " + subLen + "[" + lenPriceMap.get(subLen) + "]";
                }
            }
            System.out.print(this.length + "[" + price + "] = " + result + "\n");
        }


        public int getLength() {
            return length;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

    }
}
