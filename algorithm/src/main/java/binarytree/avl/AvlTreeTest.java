package binarytree.avl;

import binarytree.BinaryTreeIsBalancedDemo;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/23
 */
public class AvlTreeTest {

    public static void main(String[] args) {
        AvlTree tree = new AvlTree();
        for (int i = 1; i <= 1000; i++) {
            tree.addNode(i);
        }

        System.out.println(BinaryTreeIsBalancedDemo.isBalanced(tree.getHead()));

        for (int i = 1; i <= 1000 ; i++) {
            System.out.printf("查询 %d 需要 %d 步 \n", i, tree.query(i));
        }

    }
}
