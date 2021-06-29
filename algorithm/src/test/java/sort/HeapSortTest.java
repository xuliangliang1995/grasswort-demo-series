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
class HeapSortTest {
    private IMathArraySort heapSort;

    @BeforeEach
    void setUp() {
        heapSort = new HeapSort();
    }

    @AfterEach
    void tearDown() {
        heapSort = null;
    }

    @RepeatedTest(1000)
    void sort() {
        int[] array = IntArrayUtil.randomArray(10000, 0, Integer.MAX_VALUE);
        int[] duplicate = IntArrayUtil.copyArray(array);
        heapSort.sort(array);
        Arrays.sort(duplicate);
        assertTrue(IntArrayUtil.isEqual(array, duplicate));
    }

}