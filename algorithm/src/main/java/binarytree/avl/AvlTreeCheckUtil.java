package binarytree.avl;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/28
 */
public class AvlTreeCheckUtil {

    /**
     * is avl tree
     * @param root
     * @return
     */
    public static boolean isAvlTree(AvlNode root) {
        return process(root).isAvlTree;
    }

    /**
     * process node
     * @param node
     * @return
     */
    private static Info process(AvlNode node) {
        if (node == null) {
            return new Info(0, true);
        }
        Info left = process(node.getL());
        Info right = process(node.getR());

        int height = Math.max(left.height, right.height) + 1;
        boolean isAvlTree = Math.abs(left.height - right.height) <= 1;

        // 再额外添加一些二叉查询树基本校验
        if (node.getL() != null) {
            assert node.getL().getVal() < node.getVal();
            assert node.getL().getP() == node;
        }
        if (node.getR() != null) {
            assert node.getR().getVal() > node.getVal();
            assert node.getR().getP() == node;
        }

        return new Info(height, isAvlTree);
    }


    static class Info {
        private int height;
        private boolean isAvlTree;

        public Info(int height, boolean isAvlTree) {
            this.height = height;
            this.isAvlTree = isAvlTree;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isAvlTree() {
            return isAvlTree;
        }

        public void setAvlTree(boolean avlTree) {
            isAvlTree = avlTree;
        }
    }
}
