package dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 🌺xuliangliang🌺
 * @Description 将一个金额分摊到不同数量的设备上
 * @Date 2021/5/30
 */
public class AdjustPriceProblem {

    private static int[] deviceQuantity = {20, 13, 11, 6, 5};

    private static Map<Integer, Result> resultMap = new HashMap<>();

    private static Map<Integer, Integer> apportionMap = new HashMap<>();


    static {
        for (int i : deviceQuantity) {
            resultMap.put(i, new Result(true, i));
            apportionMap.put(i, i);
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 100 ; i++) {
            process(i).printResult();
        }
    }


    public static Result process(int amount) {
        if (amount < 0) {
            return new Result(false, amount);
        }
        if (amount == 0) {
            return new Result(true, amount);
        }
        if (resultMap.containsKey(amount)) {
            return resultMap.get(amount);
        }
        boolean q = false;
        for (int cutAmount : deviceQuantity) {
            boolean subQ = process(amount - cutAmount).isSuccess();
            if (subQ) {
                q = subQ;
                apportionMap.put(amount, cutAmount);
                break;
            }
        }
        Result result = new Result(q, amount);
        resultMap.put(amount, result);
        return result;
    }


    static class Result {
        boolean success;
        int amount;

        public Result(boolean success, int amount) {
            this.success = success;
            this.amount = amount;
        }

        public void printResult() {
            System.out.println(amount + " 是否可以分摊成功 ? " + (success ? "是" : "否"));
            if (! success) {
                return;
            }
            int amountBak = amount;
            String result = null;
            while (amountBak > 0) {
                int cut = apportionMap.get(amountBak);
                if (result == null) {
                    result = String.valueOf(cut);
                } else {
                    result = result + " + " + cut;
                }
                amountBak -= cut;
            }
            System.out.print(amount + " = " + result + "\n");
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
