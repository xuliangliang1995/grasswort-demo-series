package sort.bucket;

import sort.IMathArraySort;

/**
 * @author xuliangliang
 * @Description 计数排序(对数组有要求,例如数组内的树在[0, 200] 一定小范围之间)
 * @Date 2020/7/19
 */
public class CountingSort implements IMathArraySort {
    /**
     * 数组排序, 等同于 Arrays.sort(int[] array)
     *
     * @param array
     */
    @Override
    public void sort(int[] array) {
        // 获取最大值和最小值
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            max = array[i] > max ? array[i] : max;
            min = array[i] < min ? array[i] : min;
        }
        // 创建辅助数组开始计数
        int[] assist = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            assist[array[i] - min]++;
        }

        int j = 0;
        // 重新排序
        for (int i = 0; i < assist.length; i++) {
            if (assist[i] > 0) {
                for (int k = 0; k < assist[i]; k++) {
                    array[j++] = i + min;
                }
            }
        }
    }
}
