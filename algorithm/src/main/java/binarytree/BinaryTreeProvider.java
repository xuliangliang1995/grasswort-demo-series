package binarytree;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/18
 */
public class BinaryTreeProvider {

    /**
     * 返回一个二叉树
     *             a
     *          b     c
     *       d    e f   g
     * @return
     */
    public static Node binaryTree() {
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        Node f = new Node("f");
        Node g = new Node("g");
        a.setLeft(b);
        a.setRight(c);
        b.setLeft(d);
        b.setRight(e);
        c.setLeft(f);
        c.setRight(g);
        return a;
    }


    /**
     * 返回一个二叉树
     *             4
     *          2     6
     *       1    3 5   7
     * @return
     */
    public static INode<Integer> balancedTree() {
        Node<Integer> a = new Node<>(4);
        Node<Integer> b = new Node<>(2);
        Node<Integer> c = new Node<>(6);
        Node<Integer> d = new Node<>(1);
        Node<Integer> e = new Node<>(3);
        Node<Integer> f = new Node<>(5);
        Node<Integer> g = new Node<>(7);
        a.setLeft(b);
        a.setRight(c);
        b.setLeft(d);
        b.setRight(e);
        c.setLeft(f);
        c.setRight(g);
        return a;
    }


    /**
     * 返回一个二叉树
     *             3
     *          2
     *       1
     * @return
     */
    public static Node<Integer> noBalancedTree() {
        Node<Integer> a = new Node<>(3);
        Node<Integer> b = new Node<>(2);
        Node<Integer> c = new Node<>(1);

        a.setLeft(b);
        b.setLeft(c);
        return a;
    }
}
