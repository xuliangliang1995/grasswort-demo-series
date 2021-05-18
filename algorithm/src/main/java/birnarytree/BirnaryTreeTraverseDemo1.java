package birnarytree;

/**
 * @author 🌺xuliangliang🌺
 * @Description 二叉树遍历[先序/中序/后序](递归)
 * @Date 2021/5/18
 */
public class BirnaryTreeTraverseDemo1 {

    public static void main(String[] args) {
        Node tree = BirnaryTreeProvider.birnaryTree();

        System.out.println("先序 : ");
        pre(tree);

        System.out.println("中序 : ");
        mid(tree);

        System.out.println("后序 : ");
        post(tree);
    }

    /**
     * 先序
     * @param node
     */
    private static void pre(Node node) {
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
    private static void mid(Node node) {
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
    private static void post(Node node) {
        if (node == null) {
            return;
        }
        post(node.getLeft());
        post(node.getRight());
        System.out.println(node);
    }
}
