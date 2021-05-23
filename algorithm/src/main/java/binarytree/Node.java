package binarytree;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/18
 */
public class Node<T> implements INode<T> {

    private T val;

    private Node<T> left;

    private Node<T> right;

    @Override
    public String toString() {
        return val.toString();
    }

    public Node() {
    }

    public Node(T val) {
        this.val = val;
    }

    @Override
    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    @Override
    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    @Override
    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

}
