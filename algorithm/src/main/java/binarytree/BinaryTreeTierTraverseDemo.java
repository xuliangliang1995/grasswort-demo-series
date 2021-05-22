package binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/22
 */
public class BinaryTreeTierTraverseDemo {

    public static void main(String[] args) {
        Node binaryTree = BinaryTreeProvider.binaryTree();
        tierTraverse(binaryTree);
    }

    /**
     * 按层遍历
     * @param head
     */
    private static void tierTraverse(Node head) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        while (! queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur);
            if (cur.getLeft() != null) {
                queue.add(cur.getLeft());
            }
            if (cur.getRight() != null) {
                queue.add(cur.getRight());
            }
        }

    }
}
