package binarytreev2.avl;

import binarytreev2.BTNode;
import binarytreev2.SearchBinaryTree;

/**
 * @author xuliangliang
 * @Description avl tree
 * @Date 2021/6/15
 */
public class AvlTree<K extends Comparable<K>, V> extends SearchBinaryTree<K, V> {

    @Override
    protected BTNode<K, V> constructor(K key, V value) {
        return new AvlNode<>(key, value);
    }

    @Override
    protected void postInsert(BTNode<K, V> newNode) {
        regulateHeight((AvlNode<K, V>) newNode);
        balance((AvlNode<K, V>) newNode);
    }

    @Override
    protected void postRemove(BTNode<K, V> parentNode) {
        regulateHeight((AvlNode<K, V>) parentNode);
        balance((AvlNode<K, V>) parentNode);
    }

    /**
     * 平衡
     * @param node
     */
    private void balance(AvlNode<K, V> node) {
        if (node == null) {
            return;
        }
        AvlNode<K, V> parent = (AvlNode<K, V>) node.getParent();
        AvlNode<K, V> left = (AvlNode<K, V>) node.getLeft();
        AvlNode<K, V> right = (AvlNode<K, V>) node.getRight();
        int leftHeight = left != null ? left.getHeight() : 0;
        int rightHeight = right != null ? right.getHeight() : 0;
        int diffHeight = Math.abs(leftHeight - rightHeight);
        if (diffHeight > 1) {
            if (leftHeight > rightHeight) {
                // LL or LR
                int llHeight = left.getLeft() != null ? ((AvlNode<K, V>)left.getLeft()).getHeight() : 0;
                int lrHeight = left.getRight() != null ? ((AvlNode<K, V>)left.getRight()).getHeight() : 0;
                boolean isLL = llHeight >= lrHeight;
                if (isLL) {
                    rightRotate(node);
                } else {
                    leftRotate(left);
                    rightRotate(node);
                }
            } else {
                // RR or RL
                int rlHeight = right.getLeft() != null ? ((AvlNode<K, V>)right.getLeft()).getHeight() : 0;
                int rrHeight = right.getRight() != null ? ((AvlNode<K, V>)right.getRight()).getHeight() : 0;
                boolean isRR = rrHeight >= rlHeight;
                if (isRR) {
                    leftRotate(node);
                } else {
                    rightRotate(right);
                    leftRotate(node);
                }
            }
        }
        balance(parent);
    }

    /**
     * 左旋
     * @param node
     */
    private void leftRotate(AvlNode<K, V> node) {
        AvlNode<K, V> p = (AvlNode<K, V>) node.getParent();
        AvlNode<K, V> right = (AvlNode<K, V>) node.getRight();
        AvlNode<K, V> rightLeft = (AvlNode<K, V>) right.getLeft();

        node.setRight(rightLeft);
        if (rightLeft != null) {
            rightLeft.setParent(node);
        }

        node.setParent(right);
        right.setLeft(node);
        right.setParent(p);

        if (p != null) {
            boolean nodeIsLeft = p.getLeft() == node;
            if (nodeIsLeft) {
                p.setLeft(right);
            } else {
                p.setRight(right);
            }
        } else {
            head = right;
        }
        regulateHeight(node);
    }


    /**
     * 右旋
     * @param node
     */
    private void rightRotate(AvlNode<K, V> node) {
        AvlNode<K, V> p = (AvlNode<K, V>) node.getParent();
        AvlNode<K, V> left = (AvlNode<K, V>) node.getLeft();
        AvlNode<K, V> leftRight = (AvlNode<K, V>) left.getRight();

        node.setLeft(leftRight);
        if (leftRight != null) {
            leftRight.setParent(node);
        }

        node.setParent(left);
        left.setRight(node);
        left.setParent(p);

        if (p != null) {
            boolean nodeIsLeft = p.getLeft() == node;
            if (nodeIsLeft) {
                p.setLeft(left);
            } else {
                p.setRight(left);
            }
        } else {
            head = left;
        }
        regulateHeight(node);
    }



    /**
     * 校正节点高度(确保子节点高度是正确的)
     * @param node
     */
    private void regulateHeight(AvlNode<K, V> node) {
        if (node == null) {
            return;
        }
        int leftHeight = node.getLeft() != null ? ((AvlNode<K, V>)node.getLeft()).getHeight() : 0;
        int rightHeight = node.getRight() != null ? ((AvlNode<K, V>)node.getRight()).getHeight() : 0;
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        regulateHeight((AvlNode<K, V>) node.getParent());
    }

}
