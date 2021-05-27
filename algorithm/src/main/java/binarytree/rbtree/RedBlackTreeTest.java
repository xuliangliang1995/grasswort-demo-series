package binarytree.rbtree;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/26
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {
        int nodeCount = 1000;
        RedBlackTree rbTree = new RedBlackTree();
        for (int i = 1; i <= nodeCount; i++) {
            rbTree.addNode(i);
        }

        RbNode root = rbTree.getRoot();

        for (int i = 1; i <= nodeCount ; i++) {
            rbTree.query(i);
        }

        System.out.println("是否红黑树 ： " + RedBlackTreeCheckUtil.isBlackRedTree(root));
        rbTree.print();

        for (int i = nodeCount; i >= 1; i--) {
            int delVal = i;
            System.out.println("delete " + delVal);
            rbTree.deleteNode(delVal);
            System.out.println("节点数量 >>>>>>>>>> : " + rbTree.nodeCount() + ",应该是 : " + (i - 1));
            assert rbTree.nodeCount() == (i - 1);
            System.out.println("是否删除成功 : " + (rbTree.query(delVal) == null));
            System.out.println("是否依然保持红黑树性质 : " + RedBlackTreeCheckUtil.isBlackRedTree(rbTree.getRoot()));
            /*try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }
}
