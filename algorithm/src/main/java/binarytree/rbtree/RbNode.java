package binarytree.rbtree;

import binarytree.INode;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/26
 */
public class RbNode implements INode<Integer> {

    private RbNode l;

    private RbNode r;

    private RbNode p;

    private Color color;

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public RbNode getL() {
        return l;
    }

    public void setL(RbNode l) {
        this.l = l;
    }

    public RbNode getR() {
        return r;
    }

    public void setR(RbNode r) {
        this.r = r;
    }

    public RbNode getP() {
        return p;
    }

    public void setP(RbNode p) {
        this.p = p;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
