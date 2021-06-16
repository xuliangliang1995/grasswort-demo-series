package binarytree.test;

import binarytree.avl.AvlNode;
import binarytree.avl.AvlTree;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.RepeatedTest;
import sort.util.IntArrayUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/15
 */
public class AvlTreeTest {

    @RepeatedTest(1000)
    public void test() {
        AvlTree<Integer, String> avlTree = new AvlTree<>();
        int testCount = 100;
        int[] keys = new int[testCount];

        for (int i = 0; i < testCount; i++) {
            keys[i] = i;
        }

        Random random = new Random();
        for (int i = 0; i < testCount; i++) {
            IntArrayUtil.swap(keys, i, keys[random.nextInt(testCount)]);
        }

        Map<Integer, String> resultMap = new HashMap<>();
        for (int i = 0; i < testCount; i++) {
            Integer key = keys[i];
            resultMap.put(key, RandomStringUtils.randomAlphabetic(10));
            avlTree.put(key, resultMap.get(key));
            assertTrue(isAvlTree(avlTree));
            assertEquals(i + 1, avlTree.size());
        }

        int size = avlTree.size();
        for (int i = 0; i < testCount; i++) {
            Integer key = keys[i];
            assertEquals(resultMap.get(key), avlTree.remove(key));
            assertTrue(isAvlTree(avlTree));
            assertEquals(--size, avlTree.size());
        }

    }


    /**
     * is avl tree
     * @param avlTree
     * @return
     */
    private static <K extends Comparable<K>, V> boolean isAvlTree(AvlTree<K, V> avlTree) {
        return process((AvlNode<K, V>)avlTree.getHead()).isAvlTree;
    }

    /**
     * process node
     * @param node
     * @return
     */
    private static <K extends Comparable<K>, V> Info process(AvlNode<K, V> node) {
        if (node == null) {
            return new Info(0, true);
        }
        Info left = process((AvlNode<K, V>) node.getLeft());
        Info right = process((AvlNode<K, V>) node.getRight());

        int height = Math.max(left.height, right.height) + 1;
        boolean isAvlTree = Math.abs(left.height - right.height) <= 1;

        // 再额外添加一些二叉查询树基本校验
        if (node.getLeft() != null) {
            assertTrue(node.getLeft().isLessThan(node.getKey()));
            assertTrue(node.getLeft().getParent() == node);
        }
        if (node.getRight() != null) {
            assertTrue(node.getRight().isMoreThan(node.getKey()));
            assertTrue(node.getRight().getParent() == node);
        }

        return new Info(height, isAvlTree);
    }


    static class Info {
        private int height;
        private boolean isAvlTree;

        public Info(int height, boolean isAvlTree) {
            this.height = height;
            this.isAvlTree = isAvlTree;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isAvlTree() {
            return isAvlTree;
        }

        public void setAvlTree(boolean avlTree) {
            isAvlTree = avlTree;
        }
    }
}
