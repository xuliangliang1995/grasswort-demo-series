package binarytree.rbtree;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/26
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {
        RedBlackTree rbTree = new RedBlackTree();
        for (int i = 1; i <= 1000; i++) {
            rbTree.addNode(i);
        }

        RbNode root = rbTree.getRoot();

        for (int i = 1; i <= 1000 ; i++) {
            System.out.printf("查询 %d 需要 %d 步 \n", i, rbTree.query(i));
        }

        System.out.println("是否红黑树 ： " + RedBlackTreeCheckUtil.isBlackRedTree(root));

    }
}
