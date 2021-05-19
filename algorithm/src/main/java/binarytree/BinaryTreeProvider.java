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
    public static Node birnaryTree() {
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
}
