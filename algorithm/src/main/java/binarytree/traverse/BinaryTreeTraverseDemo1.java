package binarytree.traverse;

import binarytree.BTNode;

/**
 * @author 🌺xuliangliang🌺
 * @Description 二叉树遍历[先序/中序/后序](递归)
 * @Date 2021/5/18
 */
public class BinaryTreeTraverseDemo1 {

    /**
     * 先序
     * @param node
     */
    private static void pre(BTNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node);
        pre(node.getLeft());
        pre(node.getRight());
    }

    /**
     * 中序
     * @param node
     */
    private static void mid(BTNode node) {
        if (node == null) {
            return;
        }
        mid(node.getLeft());
        System.out.println(node);
        mid(node.getRight());
    }

    /**
     * 后序
     * @param node
     */
    private static void post(BTNode node) {
        if (node == null) {
            return;
        }
        post(node.getLeft());
        post(node.getRight());
        System.out.println(node);
    }
}
