package binarytree.traverse;

import binarytree.BTNode;

import java.util.Stack;

/**
 * @author 🌺xuliangliang🌺
 * @Description 二叉树遍历[先序/中序/后序](非递归)
 * @Date 2021/5/18
 */
public class BinaryTreeTraverseDemo2 {

    /**
     * 先序（头左右）
     * @param cur
     */
    private static void pre(BTNode cur) {
        Stack<BTNode> stack = new Stack<>();
        stack.push(cur);

        while (!stack.isEmpty()) {
            cur = stack.pop();
            System.out.println(cur);
            if (cur.getRight() != null) {
                stack.push(cur.getRight());
            }
            if (cur.getLeft() != null) {
                stack.push(cur.getLeft());
            }
        }
    }

    /**
     * 中序（左头右）
     * @param cur
     */
    private static void mid(BTNode cur) {
        Stack<BTNode> stack = new Stack<>();
        while (cur != null) {
            stack.push(cur);
            cur = cur.getLeft();
        }
        while (! stack.isEmpty()) {
            cur = stack.pop();
            System.out.println(cur);
            if (cur.getRight() != null) {
                cur = cur.getRight();
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.getLeft();
                }
            }
        }
    }

    /**
     * 后序（左右头）（头右左 + 反转）
     * @param cur
     */
    private static void post(BTNode cur) {
        Stack<BTNode> stack = new Stack<>();
        Stack<BTNode> stack1 = new Stack<>();
        stack.push(cur);

        while (! stack.isEmpty()) {
            cur = stack.pop();
            stack1.push(cur);

            if (cur.getLeft() != null) {
                stack.push(cur.getLeft());
            }
            if (cur.getRight() != null) {
                stack.push(cur.getRight());
            }
        }

        while (! stack1.isEmpty()) {
            System.out.println(stack1.pop());
        }
    }
}
