package sort.util;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/7/12
 */
public class IntArrayUtilTest {

    //@RepeatedTest(1000)
    public void randomArray() {
        int size = 1000;
        int minimum = -1000;
        int maximum = 1000;
        int[] array = IntArrayUtil.randomArray(size, minimum, maximum);
        Arrays.sort(array);
        assertEquals(size, array.length);
        assertTrue(array[0] >= minimum);
        assertTrue(array[array.length - 1] <= maximum);
    }
}