package sort;

import sort.util.IntArrayUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/7/18
 */
class QuickSortTest {

    private IMathArraySort quickSort;

    @BeforeEach
    void setUp() {
        quickSort = new QuickSort();
    }

    @AfterEach
    void tearDown() {
        quickSort = null;
    }

    @RepeatedTest(1000)
    void sort() {
        int[] array = IntArrayUtil.randomArray(10000, 0, Integer.MAX_VALUE);
        int[] duplicate = IntArrayUtil.copyArray(array);
        quickSort.sort(array);
        Arrays.sort(duplicate);
        assertTrue(IntArrayUtil.isEqual(array, duplicate));
    }

}