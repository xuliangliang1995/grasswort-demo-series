package binarytree;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/18
 */
public class Node {

    private Object val;

    private Node left;

    private Node right;

    @Override
    public String toString() {
        return val.toString();
    }

    public Node() {
    }

    public Node(Object val) {
        this.val = val;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
