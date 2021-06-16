package binarytree.test;

import binarytree.redblack.Color;
import binarytree.redblack.RedBlackNode;
import binarytree.redblack.RedBlackTree;
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
public class RedBlackTreeTest {

    @RepeatedTest(1000)
    public void test() {
        RedBlackTree<Integer, String> rbTree = new RedBlackTree<>();

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
            rbTree.put(key, resultMap.get(key));
            assertTrue(isBlackRedTree(rbTree));
            assertEquals(i + 1, rbTree.size());
        }

        for (int i = 0; i < testCount; i++) {
            Integer key = keys[i];
            assertEquals(resultMap.get(key), rbTree.get(key));
        }

        int size = rbTree.size();
        for (int i = 0; i < testCount; i++) {
            Integer key = keys[i];
            assertEquals(resultMap.get(key), rbTree.remove(key));
            assertTrue(isBlackRedTree(rbTree));
            assertEquals(--size, rbTree.size());
        }
    }

    /**
     * 是否红黑树
     * 性质1 ： red Or black
     * 性质2 ： root is black
     * 性质3 ： nil is black
     * 性质4 ： can't red-red
     * 性质5 ： black height
     * @param rbTree
     * @return
     */
    public static <K extends Comparable<K>, V> boolean isBlackRedTree(RedBlackTree<K, V> rbTree) {
        RedBlackNode<K, V> root = (RedBlackNode<K, V>) rbTree.getHead();
        if (root == null) {
            return true;
        }
        if (Color.BLACK != root.getColor()) {
            // check 2
            return false;
        }
        return process(root).isValid();
    }

    private static <K extends Comparable<K>, V> Info<K> process(RedBlackNode<K, V> node) {
        if (node == null) {
            return new Info(0, Color.BLACK, true, null);
        }
        Info left = process((RedBlackNode<K, V>) node.getLeft());
        Info right = process((RedBlackNode<K, V>) node.getRight());

        boolean isValid = true;

        // check 1
        if (Color.BLACK != node.getColor() && Color.RED != node.getColor()) {
            isValid = false;
        }
        // check 4
        if (Color.RED == node.getColor() && (Color.RED == left.color || Color.RED == right.color)) {
            isValid = false;
        }
        // check 5;
        if (left.height != right.height) {
            isValid = false;
        }

        // 基本二叉查询树校验
        if (left.getKey() != null && node.isLessThan((K) left.getKey())) {
            isValid = false;
        }

        if (right.getKey() != null && node.isMoreThan((K) right.getKey())) {
            isValid = false;
        }

        // 旋转出错校验
        if (node.getLeft() != null) {
            assertTrue(node.getLeft().getParent() == node);
        }
        if (node.getRight() != null) {
            assertTrue(node.getRight().getParent() == node);
        }

        assert node.getWeight() == 1;

        int height = left.height + (Color.BLACK == node.getColor() ? 1 : 0);

        return new Info<K>(height, node.getColor(), isValid, node.getKey());
    }


    static class Info<K extends Comparable<K>> {
        int height;
        Color color;
        boolean valid;
        K key;

        public Info(int height, Color color, boolean isValid, K value) {
            this.height = height;
            this.color = color;
            this.valid = isValid;
            this.key = value;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public boolean isValid() {
            return valid;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }
    }
}
