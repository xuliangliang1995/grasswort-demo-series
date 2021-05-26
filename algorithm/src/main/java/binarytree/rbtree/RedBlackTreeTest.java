package binarytree.rbtree;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/26
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {
        RedBlackTree rbTree = new RedBlackTree();
        for (int i = 1; i <= 8; i++) {
            rbTree.addNode(i);
        }
        rbTree.addNode(10);
        rbTree.addNode(9);
        RbNode root = rbTree.getRoot();
        
    }
}
