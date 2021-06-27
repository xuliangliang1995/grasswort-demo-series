package daily.t20210627;

import binarytree.BTNode;
import binarytree.BinaryTreeTraverseUtil;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Line;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/27
 */
public class Topic001 {

    static Random random = new Random();
    static int bound = 100;
    static double dice = 0.6d;

    public static void main(String[] args) {
        BTNode<Integer, Integer> head = randomBinaryTree();
        BinaryTreeTraverseUtil.mid(head);
    }

    @RepeatedTest(1000)
    public void testMaxPath1() {
        BTNode<Integer, Integer> head = randomBinaryTree();
        assertEquals(maxPath1Right(head), maxPath1(head));
    }

    @RepeatedTest(1000)
    public void testMaxPath2() {
        BTNode<Integer, Integer> head = randomBinaryTree();
        assertEquals(maxPath2Right(head), maxPath2(head).maxPath);
    }

    @Test
    public void testMaxPath3() {
        // test 3 没想好暴击方法怎么写
        BTNode<Integer, Integer> head = randomBinaryTree();
        System.out.println(maxPath3(head).maxPath);
    }

    /**
     * 最长路径 1
     * @param head
     * @return
     */
    private static int maxPath1(BTNode<Integer, Integer> head) {
        if (head.getLeft() == null && head.getRight() == null) {
            return head.getKey();
        }
        int ans = Integer.MIN_VALUE;
        if (head.getLeft() != null) {
            ans = Math.max(ans, maxPath1(head.getLeft()) + head.getKey());
        }
        if (head.getRight() != null) {
            ans = Math.max(ans, maxPath1(head.getRight()) + head.getKey());
        }
        return ans;
    }

    /**
     * 暴力方式求解路径 1
     * @param head
     * @return
     */
    private static int maxPath1Right(BTNode<Integer, Integer> head) {
        int ans = Integer.MIN_VALUE;
        Stack<BTNode<Integer, Integer>> stack = new Stack<>();
        stack.push(head);
        while (! stack.isEmpty()) {
            BTNode<Integer, Integer> cursor = stack.pop();
            boolean isLeaf = cursor.getLeft() == null && cursor.getRight() == null;
            if (isLeaf) {
                // 计算到 head 的路径和
                int subAns = cursor.getKey();
                while (cursor.getParent() != null) {
                    cursor = cursor.getParent();
                    subAns += cursor.getKey();
                }
                ans = Math.max(ans, subAns);
            } else {
                if (cursor.getLeft() != null) {
                    stack.push(cursor.getLeft());
                }
                if (cursor.getRight() != null) {
                    stack.push(cursor.getRight());
                }
            }
        }
        return ans;
    }

    /**
     * 最长路径 2
     * @param head
     * @return
     */
    private static Info2 maxPath2(BTNode<Integer, Integer> head) {
        if (head.getLeft() == null && head.getRight() == null) {
            return new Info2(head.getKey(), head.getKey());
        }
        Info2 left = head.getLeft() != null ? maxPath2(head.getLeft()) : null;
        Info2 right = head.getRight() != null ? maxPath2(head.getRight()) : null;

        int maxPathAns = head.getKey();
        int maxPathAnsFromHead = head.getKey();

        if (left != null) {
            maxPathAns = Math.max(maxPathAns, left.maxPath);
            maxPathAns = Math.max(maxPathAns, left.maxPathFromHead + (head.getKey() > 0 ? head.getKey() : 0));
            maxPathAnsFromHead = Math.max(maxPathAnsFromHead, head.getKey() + left.maxPathFromHead);
        }

        if (right != null) {
            maxPathAns = Math.max(maxPathAns, right.maxPath);
            maxPathAns = Math.max(maxPathAns, right.maxPathFromHead + (head.getKey() > 0 ? head.getKey() : 0));
            maxPathAnsFromHead = Math.max(maxPathAnsFromHead, head.getKey() + right.maxPathFromHead);
        }

        return new Info2(maxPathAns, maxPathAnsFromHead);
    }

    /**
     * 暴力求解最长路径2
     * @param head
     * @return
     */
    private static int maxPath2Right(BTNode<Integer, Integer> head) {
        Stack<BTNode<Integer, Integer>> stack = new Stack<>();
        stack.push(head);
        int ans = Integer.MIN_VALUE;
        while (! stack.isEmpty()) {
            BTNode<Integer, Integer> cursor = stack.pop();
            if (cursor.getLeft() != null) {
                stack.push(cursor.getLeft());
            }
            if (cursor.getRight() != null) {
                stack.push(cursor.getRight());
            }
            int sum = 0;
            while (cursor != null) {
                sum += cursor.getKey();
                ans = Math.max(ans, sum);
                cursor = cursor.getParent();
            }
        }
        return ans;
    }


    static class Info2 {
        private final int maxPath;
        private final int maxPathFromHead;

        public Info2(int maxPath, int maxPathFromHead) {
            this.maxPath = maxPath;
            this.maxPathFromHead = maxPathFromHead;
        }
    }

    /**
     * 最大路径 3
     * @param head
     * @return
     */
    private static Info3 maxPath3(BTNode<Integer, Integer> head) {
        int headKey = head.getKey();
        if (head.getLeft() == null && head.getRight() == null) {
            return new Info3(headKey, headKey);
        }

        Info3 left = head.getLeft() != null ? maxPath3(head.getLeft()) : null;
        Info3 right = head.getRight() != null ? maxPath3(head.getRight()) : null;

        int maxPathAns = headKey;
        int maxPathAnsFromHead = headKey;

        if (left != null) {
            maxPathAns = Math.max(maxPathAns, left.maxPath);
            maxPathAns = Math.max(maxPathAns, left.maxPathFromHead + headKey);
            maxPathAnsFromHead = Math.max(maxPathAnsFromHead, left.maxPathFromHead + headKey);
        }
        if (right != null) {
            maxPathAns = Math.max(maxPathAns, right.maxPath);
            maxPathAns = Math.max(maxPathAns, right.maxPathFromHead + headKey);
            maxPathAnsFromHead = Math.max(maxPathAnsFromHead, right.maxPathFromHead + headKey);
        }

        if (left != null && right != null) {
            maxPathAns = Math.max(maxPathAns, left.maxPathFromHead + headKey + right.maxPathFromHead);
        }

        return new Info3(maxPathAns, maxPathAnsFromHead);
    }

    static class Info3 {
        private final int maxPath;
        private final int maxPathFromHead;

        Info3(int maxPath, int maxPathFromHead) {
            this.maxPath = maxPath;
            this.maxPathFromHead = maxPathFromHead;
        }
    }


    /**
     * 生成随机二叉树
     * @param head
     * @return
     */
    private static BTNode<Integer, Integer> generatorRandomBinaryTree(BTNode<Integer, Integer> head) {
        if (Math.random() > dice) {
            BTNode<Integer, Integer> left = new BTNode<>(random.nextInt(bound) - bound / 2, null);
            head.setLeft(left);
            left.setParent(head);
            generatorRandomBinaryTree(left);
        }
        if (Math.random() > dice) {
            BTNode<Integer, Integer> right = new BTNode<>(random.nextInt(bound) - bound / 2, null);
            head.setRight(right);
            right.setParent(head);
            generatorRandomBinaryTree(right);
        }
        return head;
    }

    /**
     * 随机二叉树
     * @return
     */
    private static BTNode<Integer, Integer> randomBinaryTree() {
        return generatorRandomBinaryTree(new BTNode<>(random.nextInt(bound) - bound / 2, null));
    }


}
