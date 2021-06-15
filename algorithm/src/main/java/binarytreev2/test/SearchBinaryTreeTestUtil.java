package binarytreev2.test;

import binarytreev2.SearchBinaryTree;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.RepeatedTest;
import sort.util.IntArrayUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/15
 */
public class SearchBinaryTreeTestUtil {

    @RepeatedTest(100)
    public void test() {
        SearchBinaryTree<Integer, String> searchBinaryTree = new SearchBinaryTree<>();
        int testCount = 100;
        int[] keys = new int[testCount];
        for (int i = 0; i < testCount; i++) {
            keys[i] = i;
        }

        Random random = new Random();
        for (int i = 0; i < testCount; i++) {
            IntArrayUtil.swap(keys, i, random.nextInt(testCount));
        }

        Map<Integer, String> resultMap = new HashMap<>(testCount);

        for (int i = 0; i < testCount; i++) {
            Integer key = keys[i];
            resultMap.put(key, RandomStringUtils.randomAlphabetic(10));
            searchBinaryTree.put(key, resultMap.get(key));
            assertEquals(i + 1, searchBinaryTree.size());
            assertNotNull(searchBinaryTree.get(key));
        }

        int size = searchBinaryTree.size();
        for (int i = 0; i < testCount; i++) {
            Integer key = keys[i];
            assertEquals(resultMap.get(key), searchBinaryTree.get(key));
            assertNotNull(searchBinaryTree.remove(key));
            assertEquals(--size, searchBinaryTree.size());
            assertNull(searchBinaryTree.get(key));
        }

        assertEquals(0, searchBinaryTree.size());

    }
}
