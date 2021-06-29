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
class BubbleSortTest {

    private IMathArraySort bubbleSort;

    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
    }

    @AfterEach
    void tearDown() {
        bubbleSort = null;
    }

    @RepeatedTest(1000)
    void sort() {
        int[] array = IntArrayUtil.randomArray(10000, 0, Integer.MAX_VALUE);
        int[] duplicate = IntArrayUtil.copyArray(array);
        bubbleSort.sort(array);
        Arrays.sort(duplicate);
        assertTrue(IntArrayUtil.isEqual(array, duplicate));
    }
}