package binarytree.avl;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/23
 */
public class AvlTree {

    private AvlNode head;

    public AvlNode getHead() {
        return head;
    }

    /**
     * 查询元素,返回元素查询路径
     * @param value
     * @return
     */
    public int query(int value) {
        int count = 1;
        AvlNode cur = head;

        while (true) {
            if (cur != null) {
                if (value == cur.getValue()) {
                    return count;
                }
                if (value < cur.getVal()) {
                    cur = cur.getL();
                } else {
                    cur = cur.getR();
                }
            } else {
                return count;
            }
            count++;
        }
    }

    /**
     * 添加节点
     * @param value
     */
    public void addNode(int value) {
        AvlNode newNode = new AvlNode(value);
        if (head == null) {
            head = newNode;
            return;
        }

        AvlNode cur = head;
        while (true) {
            int curVal = cur.getValue();
            if (value < curVal) {
                // 往左
                AvlNode leftNode = cur.getL();
                if (leftNode == null) {
                    cur.setL(newNode);
                    newNode.setP(cur);
                    head = cur.balance();
                    return;
                }
                cur = leftNode;
            } else if (value > curVal) {
                // 往右
                AvlNode rightNode = cur.getR();
                if (rightNode == null) {
                    cur.setR(newNode);
                    newNode.setP(cur);
                    head = cur.balance();
                    return;
                }
                cur = rightNode;
            } else {
                System.out.println("ignore node " + value);
                return;
            }
        }
    }

    public void print() {
        mid(head);
    }

    private static void mid(AvlNode node) {
        if (node == null) {
            return;
        }
        mid(node.getL());
        System.out.println(node);
        mid(node.getR());
    }
}
