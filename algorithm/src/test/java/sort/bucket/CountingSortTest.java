package sort.bucket;

import sort.IMathArraySort;
import sort.util.IntArrayUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/7/19
 */
class CountingSortTest {

    private IMathArraySort countingSort;

    @BeforeEach
    void setUp() {
        countingSort = new CountingSort();
    }

    @AfterEach
    void tearDown() {
        countingSort = null;
    }

    @RepeatedTest(1000)
    void sort() {
        int[] array = IntArrayUtil.randomArray(10000, 0, 200);
        int[] duplicate = IntArrayUtil.copyArray(array);
        countingSort.sort(array);
        Arrays.sort(duplicate);
        assertTrue(IntArrayUtil.isEqual(array, duplicate));
    }

}