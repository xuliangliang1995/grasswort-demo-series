package binarytree.test;

import binarytree.sbt.SBNode;
import binarytree.sbt.SBTree;
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
 * @Date 2021/6/16
 */
public class SBTreeTest {

    @RepeatedTest(1000)
    public void test() {
        SBTree<Integer, String> sbt = new SBTree<>();
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
            sbt.put(key, resultMap.get(key));
            assertEquals(resultMap.get(key), sbt.get(key));
            assertEquals(i + 1, sbt.size());
            assertTrue(isSBT(sbt));
        }

        int size = sbt.size();
        for (int i = 0; i < testCount / 2; i++) {
            Integer key = keys[i];
            assertEquals(resultMap.get(key), sbt.remove(key));
            assertEquals(--size, sbt.size());
        }

        int[] otherKeys = new int[testCount];
        for (int i = 0; i < testCount; i++) {
            otherKeys[i] = i + 1 + testCount;
        }

        for (int i = 0; i < testCount; i++) {
            IntArrayUtil.swap(otherKeys, i, random.nextInt(testCount));
        }

        for (int i = 0; i < testCount; i++) {
            sbt.put(otherKeys[i], null);
            assertTrue(isSBT(sbt));
        }
    }

    private <K extends Comparable<K>, V> boolean isSBT(SBTree<K, V> sbTree) {
        return process((SBNode<K, V>)sbTree.getHead()).isSBT();
    }

    private <K extends Comparable<K>, V> Info process(SBNode<K, V> node) {
        if (node == null) {
            return new Info(0, 0, 0, true);
        }
        Info left = process((SBNode<K, V>) node.getLeft());
        Info right = process((SBNode<K, V>) node.getRight());

        boolean isSBT = true;
        if (right.getRight() > left.getSize() || right.getLeft() > left.getSize()) {
            isSBT = false;
        }
        if (left.getRight() > right.getSize() || left.getLeft() > right.getSize()) {
            isSBT = false;
        }

        if (node.getSize() != (left.getSize() + right.getSize())) {
            isSBT = false;
        }

        return new Info(node.getSize(), left.getSize(), right.getSize(), isSBT);
    }

    private static class Info {
        private final int size;
        private final int left;
        private final int right;
        private final boolean isSBT;

        public Info(int size, int left, int right, boolean isSBT) {
            this.size = size;
            this.left = left;
            this.right = right;
            this.isSBT = isSBT;
        }

        public int getSize() {
            return size;
        }

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        public boolean isSBT() {
            return isSBT;
        }
    }
}
