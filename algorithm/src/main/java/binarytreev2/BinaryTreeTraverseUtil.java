package binarytreev2;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/15
 */
public class BinaryTreeTraverseUtil {

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
