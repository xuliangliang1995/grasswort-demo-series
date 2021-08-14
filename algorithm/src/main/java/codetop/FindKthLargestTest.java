package codetop;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import sort.util.IntArrayUtil;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/8/14
 */
public class FindKthLargestTest {


    @RepeatedTest(1000)
    public void testFindKthLargest() {
        Random random = new Random();
        int size = 100;
        int K = random.nextInt(size) + 1;
        int[] arr1 = IntArrayUtil.randomArray(size, 0, Integer.MAX_VALUE);
        int[] arr2 = IntArrayUtil.copyArray(arr1);
        Arrays.sort(arr1);
        int expectedValue = arr1[size - K];

        assertEquals(expectedValue, FindKthLargest.findKthLargest(arr2, K));
    }
}
