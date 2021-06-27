package daily.t20210627;

import org.junit.jupiter.api.RepeatedTest;
import sort.IMathArraySort;
import sort.util.IntArrayUtil;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description 插入排序复习
 * @Date 2021/6/27
 */
public class Review001 {

    @RepeatedTest(100)
    public void test() {
        int[] arr1 = IntArrayUtil.randomArray(1000, 0, 1000);
        int[] arr2 = IntArrayUtil.copyArray(arr1);
        Arrays.sort(arr1);
        new InsertionSort().sort(arr2);
        assertTrue(IntArrayUtil.isEqual(arr1, arr2));
    }

    static class InsertionSort implements IMathArraySort {

        @Override
        public void sort(int[] array) {
            for (int i = 1; i < array.length; i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = key;
            }
        }
    }
}
