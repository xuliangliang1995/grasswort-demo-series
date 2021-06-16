package binarytree.traverse;

import binarytree.BTNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/22
 */
public class BinaryTreeTierTraverseDemo {

    /**
     * 按层遍历
     * @param head
     */
    private static void tierTraverse(BTNode head) {
        if (head == null) {
            return;
        }

        Queue<BTNode> queue = new LinkedList<>();
        queue.add(head);

        while (! queue.isEmpty()) {
            BTNode cur = queue.poll();
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
