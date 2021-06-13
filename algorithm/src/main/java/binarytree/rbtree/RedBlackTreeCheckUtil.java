package binarytree.rbtree;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/27
 */
public class RedBlackTreeCheckUtil {


    /**
     * 是否红黑树
     * 性质1 ： red Or black
     * 性质2 ： root is black
     * 性质3 ： nil is black
     * 性质4 ： can't red-red
     * 性质5 ： black height
     * @param root
     * @return
     */
    public static boolean isBlackRedTree(RbNode root) {
        if (root == null) {
            return true;
        }
        if (Color.BLACK != root.getColor()) {
            // check 2
            return false;
        }
        return process(root).isValid();
    }

    private static Info process(RbNode node) {
        if (node == null) {
            return new Info(0, Color.BLACK, true, null);
        }
        Info left = process(node.getL());
        Info right = process(node.getR());

        boolean isValid = true;

        // check 1
        if (Color.BLACK != node.getColor() && Color.RED != node.getColor()) {
            isValid = false;
        }
        // check 4
        if (Color.RED == node.getColor() && (Color.RED == left.color || Color.RED == right.color)) {
            isValid = false;
        }
        // check 5;
        if (left.height != right.height) {
            isValid = false;
        }

        // 基本二叉查询树校验
        if (left.getValue() != null && left.getValue() > node.getValue()) {
            isValid = false;
        }

        if (right.getValue() != null && right.getValue() < node.getValue()) {
            isValid = false;
        }

        // 旋转出错校验
        if (node.getL() != null) {
            assert node.getL().getP() == node;
        }
        if (node.getR() != null) {
            assert node.getR().getP() == node;
        }

        assert node.getWeight() == 1;

        int height = left.height + (Color.BLACK == node.getColor() ? 1 : 0);

        return new Info(height, node.getColor(), isValid, node.getValue());
    }


    static class Info {
        int height;
        Color color;
        boolean valid;
        Long value;

        public Info(int height, Color color, boolean isValid, Long value) {
            this.height = height;
            this.color = color;
            this.valid = isValid;
            this.value = value;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public boolean isValid() {
            return valid;
        }

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }
    }
}
