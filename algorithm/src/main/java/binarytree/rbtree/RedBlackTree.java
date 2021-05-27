package binarytree.rbtree;

import binarytree.avl.AvlNode;

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
     * 查询元素,返回元素查询路径
     * @param value
     * @return
     */
    public RbNode query(int value) {
        int count = 1;
        try {
            RbNode cur = root;
            while (true) {
                if (cur != null) {
                    if (value == cur.getValue()) {
                        return cur;
                    }
                    if (value < cur.getVal()) {
                        cur = cur.getL();
                    } else {
                        cur = cur.getR();
                    }
                } else {
                    return null;
                }
                count++;
            }
        } finally {
            System.out.println("查询" + value + "需要" + count + "步");
        }
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
        fixupInsert(newNode);
        return newNode;
    }

    /**
     * 节点数量
     * @return
     */
    public int nodeCount() {
        return processNodeCount(root);
    }

    /**
     * 删除节点
     * @param val
     */
    public void deleteNode(int val) {
        RbNode delNode = query(val);
        System.out.println("查询结果 : " + delNode);
        if (delNode != null) {
            fixupDelete(delNode);
        }
        assert query(val) == null;
    }

    private int processNodeCount(RbNode node) {
        if (node == null) {
            return 0;
        }
        return processNodeCount(node.getL()) + processNodeCount(node.getR()) + 1;
    }


    /**
     * 修复红黑树(删除节点之后)
     * @param z
     */
    private void fixupDelete(RbNode z) {
        int zChildCount = (z.getL() != null ? 1 : 0) + (z.getR() != null ? 1 : 0);
        if (zChildCount == 0) {
            boolean zIsRed = Color.RED == z.getColor();
            if (zIsRed) {
                // 删除
                if (z.getP().getL() == z) {
                    z.getP().setL(null);
                } else {
                    z.getP().setR(null);
                }
            } else {
                if (z.getP() == null) {
                    root = null;
                    z.setP(null);
                    return;
                }
                while (true) {
                    if (z.getP() == null) {
                        assert z.getWeight() > 1;
                        z.setWeight(1);
                        break;
                    }
                    boolean zIsLeft = z == z.getP().getL();
                    RbNode brother = zIsLeft ? z.getP().getR() : z.getP().getL();
                    boolean brotherIsRed = Color.RED == brother.getColor();

                    if (brotherIsRed) {
                        // [情况四] 兄为红
                        z.getP().setColor(Color.RED);
                        brother.setColor(Color.BLACK);
                        if (zIsLeft) {
                            leftRotation(z.getP());
                        } else {
                            rightRotation(z.getP());
                        }
                        // 情况四 => 情况一,二,三之一
                    } else {
                        boolean brotherIsLeft = brother == brother.getP().getL();
                        // 兄同侧子(用 son 表示)
                        RbNode son = brotherIsLeft ? brother.getL() : brother.getR();
                        // 兄异侧子(用 daughter 表示)
                        RbNode daughter = brotherIsLeft ? brother.getR() : brother.getL();
                        boolean sonIsRed = son != null && Color.RED == son.getColor();
                        boolean daughterIsRed = daughter != null && Color.RED == daughter.getColor();
                        if (sonIsRed) {
                            // [情况一] 兄为黑,且同侧子为红
                            brother.setColor(brother.getP().getColor());
                            brother.getP().setColor(Color.BLACK);
                            son.setColor(Color.BLACK);
                            if (zIsLeft) {
                                leftRotation(brother.getP());
                            } else {
                                rightRotation(brother.getP());
                            }

                            if (z.getWeight() > 1) {
                                // 减去一层权重
                                z.setWeight(1);
                            } else {
                                if (zIsLeft) {
                                    z.getP().setL(null);
                                } else {
                                    z.getP().setR(null);
                                }
                                z.setP(null);
                            }
                            break;

                        } else if (daughterIsRed) {
                            // [情况二] 兄为黑,同侧子为黑,异侧子为红
                            brother.setColor(Color.RED);
                            daughter.setColor(Color.BLACK);
                            if (zIsLeft) {
                                rightRotation(brother);
                            } else {
                                leftRotation(brother);
                            }
                            // 情况二 => 情况一, 递归进入情况一
                        } else {
                            // [情况三] 兄为黑,兄无红子
                            RbNode father = brother.getP();
                            boolean fatherIsRed = Color.RED == father.getColor();
                            if (fatherIsRed) {
                                // 变色 + 删除 Z
                                father.setColor(Color.BLACK);
                                brother.setColor(Color.RED);

                                if (z.getWeight() > 1) {
                                    z.setWeight(1);
                                } else {
                                    z.setP(null);
                                    if (zIsLeft) {
                                        father.setL(null);
                                    } else {
                                        father.setR(null);
                                    }
                                }
                                break;
                            } else {
                                // 变色 + 删除 Z + 加权重
                                father.setWeight(2);
                                brother.setColor(Color.RED);

                                if (z.getWeight() > 1) {
                                    z.setWeight(1);
                                } else {
                                    z.setP(null);
                                    if (zIsLeft) {
                                        father.setL(null);
                                    } else {
                                        father.setR(null);
                                    }
                                }
                                // father 成为新的 z
                                z = father;
                            }

                        }
                    }
                }

            }
        } else if (zChildCount == 1) {
            // 根据红黑树性质
            // z 必为 黑色, 且子节点必为红色
            RbNode zUniqueChild = z.getL() != null ? z.getL() : z.getR();
            z.setValue(zUniqueChild.getValue());
            zUniqueChild.setValue(null);
            // 递归调用必进 zChildCount == 0 且节点为红,必被删
            fixupDelete(zUniqueChild);
        } else {
            // zChildCount == 2
            RbNode successor = successor(z);
            // successor 仅可能存在一个右红子节点
            z.setValue(successor.getValue());
            // 递归调用必进 zChildCount <= 1 , 若有子节点,子节点为红
            fixupDelete(successor);
        }
    }

    /**
     * 查找后驱节点
     * @param node
     * @return
     */
    private RbNode successor(RbNode node) {
        RbNode cursor = node.getR();
        while (cursor.getL() != null) {
            cursor = cursor.getL();
        }
        return cursor;
    }


    /**
     * 修复红黑树（插入新节点之后操作）
     * @param node
     */
    private void fixupInsert(RbNode node) {
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
        RbNode p = node.getP();
        RbNode r = node.getR();
        RbNode rLeft = r.getL();
        r.setP(p);
        r.setL(node);
        node.setP(r);
        node.setR(rLeft);
        if (rLeft != null) {
            rLeft.setP(node);
        }
        if (p != null) {
            if (p.getL() == node) {
                p.setL(r);
            } else {
                p.setR(r);
            }
        } else {
            root = r;
        }
    }

    /**
     * 右旋
     * @param node
     */
    private void rightRotation(RbNode node) {
        RbNode p = node.getP();
        RbNode l = node.getL();
        RbNode lRight = l.getR();
        l.setP(p);
        l.setR(node);
        node.setP(l);
        node.setL(lRight);
        if (lRight != null) {
            lRight.setP(node);
        }
        if (p != null) {
            if (p.getL() == node) {
                p.setL(l);
            } else {
                p.setR(l);
            }
        } else {
            root = l;
        }
    }

    private static void mid(RbNode node) {
        if (node == null) {
            return;
        }
        mid(node.getL());
        System.out.println(node);
        mid(node.getR());
    }

    public void print() {
        mid(root);
    }
}
