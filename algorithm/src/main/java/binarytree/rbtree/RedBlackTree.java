package binarytree.rbtree;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/5/26
 */
public class RedBlackTree {

    private RbNode root;

    public RbNode getRoot() {
        return root;
    }

    /**
     * 添加节点
     * @param value
     * @return 返回插入的新节点
     */
    public RbNode addNode(int value) {
        RbNode newNode = new RbNode();
        newNode.setValue(value);
        newNode.setColor(Color.RED);

        if (root == null) {
            root = newNode;
        } else {
            RbNode cursor = root;
            while (true) {
                if (value > cursor.getValue()) {
                    if (cursor.getR() == null) {
                        newNode.setP(cursor);
                        cursor.setR(newNode);
                        break;
                    }
                    cursor = cursor.getR();
                } else if (value < cursor.getValue()) {
                    if (cursor.getL() == null) {
                        newNode.setP(cursor);
                        cursor.setL(newNode);
                        break;
                    }
                    cursor = cursor.getL();
                } else {
                    System.out.println("ignore " + value);
                }
            }
        }
        // 修复红黑树
        fixupRbTree(newNode);
        return newNode;
    }

    /**
     * 修复红黑树（插入新节点之后操作）
     * @param node
     */
    private void fixupRbTree(RbNode node) {
        RbNode cursor = node;
        while (true) {
            boolean pIsRed = cursor.getP() != null && cursor.getP().getColor() == Color.RED;
            if (! pIsRed) {
                break;
            }

            if (cursor.getP() == root) {
                break;
            }

            boolean pIsLeft = cursor.getP() == cursor.getP().getP().getL();
            RbNode uncle = pIsLeft ? cursor.getP().getP().getR() : cursor.getP().getP().getL();
            boolean uncleIsRed = uncle != null && uncle.getColor() == Color.RED;

            if (uncleIsRed) {
                // 【情况 1】变色
                cursor.getP().setColor(Color.BLACK);
                uncle.setColor(Color.BLACK);
                uncle.getP().setColor(Color.RED);
                cursor = uncle.getP();
            } else if (pIsLeft) {
                boolean curIsRight = cursor == cursor.getP().getR();
                if (curIsRight) {
                    // 【情况2 => 转情况 3】左旋
                    leftRotation(cursor.getP());
                    cursor = cursor.getL();
                }
                // 【情况3】右旋 + 变色
                cursor.getP().setColor(Color.BLACK);
                cursor.getP().getP().setColor(Color.RED);
                rightRotation(cursor.getP().getP());
                cursor = cursor.getP();
            } else {
                // 和 pIsLeft 一样，只是 left 变 right , right 变 left
                boolean curIsLeft = cursor == cursor.getP().getL();
                if (curIsLeft) {
                    rightRotation(cursor.getP());
                    cursor = cursor.getR();
                }
                cursor.getP().setColor(Color.BLACK);
                cursor.getP().getP().setColor(Color.RED);
                leftRotation(cursor.getP().getP());
                cursor = cursor.getP();
            }
        }
        root.setColor(Color.BLACK);
    }

    /**
     * 左旋
     * @param node
     */
    private void leftRotation(RbNode node) {
        RbNode r = node.getR();
        RbNode rLeft = r.getL();
        r.setP(node.getP());
        r.setL(node);
        node.setP(r);
        node.setR(rLeft);
        if (r.getP() != null) {
            r.getP().setR(r);
        } else {
            root = r;
        }
    }

    /**
     * 右旋
     * @param node
     */
    private void rightRotation(RbNode node) {
        RbNode l = node.getL();
        RbNode lRight = l.getR();
        l.setP(node.getP());
        l.setR(node);
        node.setP(l);
        node.setL(lRight);
        if (l.getP() != null) {
            l.getP().setL(l);
        } else {
            root = l;
        }
    }
}
