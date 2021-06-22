package dynamic;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description 钢材用料最少问题
 * @Date 2021/6/19
 */
public class SteelBarCuttingProblem002 {
    /**
     * 钢管长度 N 米(无限根)
     * 钢管长度集合 L {l1, l2, l3} 表示长度为 l1, l2, l3 米的钢管
     * 需求个数集合 A {a1, a2, a3} 表示需要 a1 个长度为 l1 的钢管 ...
     * 求所需 N 米钢管的最少用料
     */
    public static void main(String[] args) {
        int N = 6;
        // 欲切割长度(必须从大到小排列)
        int[] lenArr = {3, 2, 1};
        // 欲切割数量(与长度对应)
        int[] aArr = {1, 2, 5};

        // 先列举单条钢筋的可切割方案
        int[][] cuttingPlans = cuttingPlan(N, lenArr, 0);
        System.out.println("单根钢条切割方案如下:");
        for (int[] cuttingPlan : cuttingPlans) {
            for (int i = 0; i < cuttingPlan.length; i++) {
                System.out.printf("[%d] * %d,", lenArr[i], cuttingPlan[i]);
            }
            System.out.println();
        }

        StringBuilder str = new StringBuilder("\n切割目标:\n");
        for (int i = 0; i < lenArr.length; i++) {
            String text = String.format("%d 根 %d 米的钢条\n", aArr[i], lenArr[i]);
            str.append(text);
        }

        // 如果只需要结果,不需要记录,可以不传入 cutRecord
        CutRecord cutRecord = new CutRecord();
        int minSteelCount = process(aArr, cuttingPlans, new HashMap<>(), cutRecord);
        System.out.printf("%s\n至少需要长度为 %d 米的钢条 : %d 根\n", str.toString(), N, minSteelCount);
        cutRecord.printCutPlan(lenArr, aArr, N);
    }

    /**
     * 最小需要钢条条数
     * @param aArr
     * @param plans
     * @param cache
     * @param cutRecord
     * @return
     */
    private static int process(int[] aArr, int[][] plans, Map<String, Integer> cache, CutRecord cutRecord) {
        boolean over = Arrays.stream(aArr).noneMatch(a -> a > 0);
        if (over) {
            return 0;
        }
        String key = serializeArr(aArr);

        if (cache.containsKey(key)) {
            /// 打印出这句话,说明动态规划启到了优化的作用
            System.out.println("打印出这句话,说明动态规划有利可图." + key);
            return cache.get(key);
        }

        // 寻找个数还没满足要求的长度索引(用于后面剪枝)
        int index = -1;
        for (int i = 0; i < aArr.length; i++) {
            if (aArr[i] > 0) {
                index = i;
                break;
            }
        }

        int q = Integer.MAX_VALUE;

        for (int[] plan : plans) {
            if (plan[index] == 0) {
                // 剪枝
                continue;
            }
            int[] aArrBak = ArrayUtils.clone(aArr);
            int[] planBak = ArrayUtils.clone(plan);
            for (int j = 0; j < aArrBak.length; j++) {
                if (aArrBak[j] < planBak[j]) {
                    planBak[j] = aArrBak[j];
                }
                aArrBak[j] = Math.max(aArrBak[j] - plan[j], 0);
            }
            String after = serializeArr(aArrBak);

            int subQ = 1 + process(aArrBak, plans, cache, cutRecord);
            if (subQ < q) {
                q = subQ;
                cutRecord.recordPlan(key, after, planBak);
            }
        }

        cache.put(key, q);
        return cache.get(key);
    }

    /**
     * 返回二维数组(返回切割方案)
     * [
     *  [1, 2, 3] 方案一, 对应集合 L, 表示 l1 长度的切 1 根
     *  [] 方案二
     * ]
     * @param N
     * @param lenArr
     * @param index 待决策位置
     */
    private static int[][] cuttingPlan(int N, int[] lenArr, int index) {
        int len = lenArr[index];

        if (index == lenArr.length - 1) {
            int[][] plan = new int[1][lenArr.length];
            plan[0] = new int[lenArr.length];
            plan[0][index] = N / len;
            return plan;
        }

        List<PlanHolder> planHolders = new ArrayList<>();

        for (int i = 0; i <= N / len; i++) {
            int cutLen = len * i;
            int[][] subPlans = cuttingPlan(N - cutLen, lenArr, index + 1);
            for (int[] subPlan : subPlans) {
                subPlan[index] = i;
            }
            planHolders.add(new PlanHolder(subPlans));
        }

        int planCount = planHolders.stream().map(h -> h.plan.length)
                .reduce(0, Integer::sum);
        int[][] plan = new int[planCount][lenArr.length];
        int i = 0;
        for (PlanHolder planHolder : planHolders) {
            for (int[] subPlan : planHolder.plan) {
                plan[i++] = subPlan;
            }
        }
        return plan;
    }

    /**
     * arr 转 str
     * @param arr
     * @return
     */
    private static String serializeArr(int[] arr) {
        return Arrays.stream(arr).mapToObj(String::valueOf).reduce((a, b) -> a + "," + b).get();
    }

    /**
     * 存数组的对象
     */
    static class PlanHolder {
        int[][] plan;

        public PlanHolder(int[][] plan) {
            this.plan = plan;
        }
    }

    /**
     * 切割记录
     */
    static class CutRecord {
        /**
         * 切割前后记录
         */
        Map<String, String> preAfterMap = new HashMap<>();
        /**
         * 切割方案记录
         */
        Map<String, String> planMap = new HashMap<>();

        public void recordPlan(String key, String after, int[] plan) {
            preAfterMap.put(key, after);
            planMap.put(key, serializeArr(plan));
        }

        public void printCutPlan(int[] lenArr, int[] aArr, int N) {
            String key = serializeArr(aArr);
            int j = 1;
            while (planMap.containsKey(key)) {
                String planStr = planMap.get(key);
                String[] plan = planStr.split(",");
                StringBuilder planText = new StringBuilder();
                planText.append("第").append(j++).append("根 :").append(N).append(" =");
                for (int i = 0; i < plan.length; i++) {
                    planText.append(String.format(" + [%d] * %s", lenArr[i], plan[i]));
                }
                System.out.println(planText.toString().replaceFirst("\\+", ""));
                key = preAfterMap.get(key);
            }
        }
    }


}
