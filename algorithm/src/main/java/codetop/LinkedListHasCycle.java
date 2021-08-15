package codetop;

/**
 * @author xuliangliang
 * @Description 环形链表
 * @Date 2021/8/14
 */
public class LinkedListHasCycle {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node3;
        System.out.println(new LinkedListHasCycle().hasCycle(node1));
    }

    /**
     * 是否有环
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        ListNode slowCursorNode = head;
        ListNode quickCursorNode = head;
        while (slowCursorNode != null && quickCursorNode != null) {
            slowCursorNode = slowTraverse(slowCursorNode);
            quickCursorNode = quickTraverse(quickCursorNode);
            if (slowCursorNode == quickCursorNode) {
                return true;
            }
        }

        return false;
    }

    /**
     * 快指针遍历
     * @param node
     * @return
     */
    private ListNode quickTraverse(ListNode node) {
        return node != null
                ? (node.next != null ? node.next.next : null)
                : null;
    }

    /**
     * 慢指针遍历
     * @param node
     * @return
     */
    private ListNode slowTraverse(ListNode node) {
        return node != null ? node.next : null;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
