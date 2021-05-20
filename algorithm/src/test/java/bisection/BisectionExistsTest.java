package bisection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/7/12
 */
class BisectionExistsTest {

    @Test
    void exists() {
        int[] sortedArray = {1, 3, 5, 9, 10, 13, 17};
        assertTrue(BisectionExists.exists(sortedArray, 9));
        assertTrue(BisectionExists.exists(sortedArray, 1));
        assertTrue(BisectionExists.exists(sortedArray, 17));
        assertTrue(! BisectionExists.exists(sortedArray, 8));
    }
}