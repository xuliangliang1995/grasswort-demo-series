package binarytree.rbtree;

import sort.util.IntArrayUtil;

import java.util.Random;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/26
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {
        int nodeCount = 1000;
        RedBlackTree rbTree = new RedBlackTree();
        int[] nodes = new int[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            nodes[i] = i + 1;
        }

        Random random = new Random();
        for (int i = 0; i < nodes.length; i++) {
            // 打乱顺序
            IntArrayUtil.swap(nodes, i, random.nextInt(nodes.length));
        }
        for (int i = 0; i < nodeCount; i++) {
            rbTree.addNode(nodes[i]);
        }

        RbNode root = rbTree.getRoot();

        for (int i = 1; i <= nodeCount ; i++) {
            rbTree.query(i);
        }

        System.out.println("是否红黑树 ： " + RedBlackTreeCheckUtil.isBlackRedTree(root));
        rbTree.print();

        for (int i = 0; i < nodes.length; i++) {
            // 打乱顺序
            IntArrayUtil.swap(nodes, i, random.nextInt(nodes.length));
        }

        for (int i = 0; i < nodes.length; i++) {
            int delVal = nodes[i];
            System.out.println("delete " + delVal);
            rbTree.deleteNode(delVal);
            System.out.println("节点数量 >>>>>>>>>> : " + rbTree.nodeCount() + ",应该是 : " + (--nodeCount));
            assert rbTree.nodeCount() == (i - 1);
            System.out.println("是否删除成功 : " + (rbTree.query(delVal) == null));
            boolean isRbTree = RedBlackTreeCheckUtil.isBlackRedTree(rbTree.getRoot());
            assert isRbTree;
            System.out.println("是否依然保持红黑树性质 : " + isRbTree);
            /*try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }
}
