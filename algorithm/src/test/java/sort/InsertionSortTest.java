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
 * @Date 2020/7/12
 */
class InsertionSortTest {

    private IMathArraySort insertionSort;

    @BeforeEach
    void setUp() {
        insertionSort = new InsertionSort();
    }

    @AfterEach
    void tearDown() {
        insertionSort = null;
    }

    @RepeatedTest(1000)
    void sort() {
        int[] array = IntArrayUtil.randomArray(10000, 0, Integer.MAX_VALUE);
        int[] duplicate = IntArrayUtil.copyArray(array);
        insertionSort.sort(array);
        Arrays.sort(duplicate);
        assertTrue(IntArrayUtil.isEqual(array, duplicate));
    }
}