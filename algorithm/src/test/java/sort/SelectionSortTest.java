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
class SelectionSortTest {

    private IMathArraySort selectionSort;

    @BeforeEach
    void setUp() {
        selectionSort = new SelectionSort();
    }

    @AfterEach
    void tearDown() {
        selectionSort = null;
    }

    @RepeatedTest(1000)
    void sort() {
        int[] array = IntArrayUtil.randomArray(10000, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int[] duplicate = IntArrayUtil.copyArray(array);
        selectionSort.sort(array);
        Arrays.sort(duplicate);
        assertTrue(IntArrayUtil.isEqual(array, duplicate));
    }
}