package binarytree;

/**
 * @author 🌺xuliangliang🌺
 * @Description 判断一棵树是否均衡
 * @Date 2021/5/23
 */
public class BinaryTreeIsBalancedDemo {

    public static void main(String[] args) {
        INode<Integer> balancedTree = BinaryTreeProvider.balancedTree();
        System.out.println(isBalanced(balancedTree));

        Node<Integer> noBalancedTree = BinaryTreeProvider.noBalancedTree();
        System.out.println(isBalanced(noBalancedTree));
    }

    public static boolean isBalanced(INode<Integer> head) {
        if (head == null) {
            return true;
        }
        Info info = process(head);
        return info.isBalanced;
    }

    private static Info process(INode<Integer> head) {
        if (head == null) {
            return null;
        }
        Info left = process(head.getLeft());
        Info right = process(head.getRight());
        int minValue = head.getVal();
        int maxValue = head.getVal();
        int leftHeight = 0;
        int rightHeight = 0;

        if (left != null) {
            minValue = Math.min(minValue, left.minValue);
            maxValue = Math.max(maxValue, left.maxValue);
            leftHeight = left.height;
        }

        if (right != null) {
            minValue = Math.min(minValue, right.minValue);
            maxValue = Math.max(maxValue, right.maxValue);
            rightHeight = right.height;
        }

        boolean isBalanced = true;

        if (isBalanced && left != null && !left.isBalanced) {
            isBalanced = false;
        }

        if (isBalanced && right != null && !right.isBalanced) {
            isBalanced = false;
        }

        if (isBalanced && left != null && left.maxValue > head.getVal()) {
            isBalanced = false;
        }
        if (isBalanced && right != null && right.minValue < head.getVal()) {
            isBalanced = false;
        }

        if (isBalanced && Math.abs(leftHeight - rightHeight) > 1) {
            isBalanced = false;
        }

        return new Info(minValue, maxValue, isBalanced, Math.max(leftHeight, rightHeight) + 1);
    }

    static class Info {
        private final int minValue;
        private final int maxValue;
        private final boolean isBalanced;
        private final int height;

        public Info(int minValue, int maxValue, boolean isBalanced, int height) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }
}
