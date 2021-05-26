package binarytree.avl;

import binarytree.INode;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/23
 */
public class AvlNode implements INode<Integer> {

    private int value;

    private AvlNode l;

    private AvlNode r;

    private AvlNode p;

    private int height;

    public AvlNode(int value) {
        this.value = value;
        this.height = 1;
    }

    /**
     * 均衡
     * @return head
     */
    public AvlNode balance() {
        int lHeight = 0;
        int rHeight = 0;
        if (l != null) {
            lHeight = l.getHeight();
        }
        if (r != null) {
            rHeight = r.getHeight();
        }

        boolean isBalanced = Math.abs(lHeight - rHeight) <= 1;
        if (! isBalanced) {
            if (lHeight > rHeight) {
                AvlNode left = l;
                AvlNode parent = p;
                this.setL(left.getR());
                this.setP(left);
                left.setR(this);
                left.setP(parent);
                if (parent != null) {
                    parent.setL(left);
                }
                return balance();
            } else {
                AvlNode right = r;
                AvlNode parent = p;
                this.setR(right.getL());
                this.setP(right);
                right.setL(this);
                right.setP(parent);
                if (parent != null) {
                    parent.setR(right);
                }
                return balance();
            }
        } else {
            this.height = Math.max(lHeight, rHeight) + 1;

            if (p != null) {
                return p.balance();
            }
            return this;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public AvlNode getL() {
        return l;
    }

    public void setL(AvlNode l) {
        this.l = l;
    }

    public AvlNode getR() {
        return r;
    }

    public void setR(AvlNode r) {
        this.r = r;
    }

    public AvlNode getP() {
        return p;
    }

    public void setP(AvlNode p) {
        this.p = p;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Integer getVal() {
        return value;
    }

    @Override
    public INode<Integer> getLeft() {
        return l;
    }

    @Override
    public INode<Integer> getRight() {
        return r;
    }
}
