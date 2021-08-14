package codetop;

/**
 * @author xuliangliang
 * @Description K 个一组翻转链表
 * @Date 2021/8/14
 */
public class ReverseKGroup {

    public static void main(String[] args) {
        ListNode head = new ListNode(1,
                new ListNode(2,
                        new ListNode(3,
                                new ListNode(4,
                                        new ListNode(5, null)))));
        System.out.println(reverseKGroup(head, 3));
    }

    /**
     * K 个一组翻转链表
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        ListNode pre = null;
        ListNode next = head;
        ListNode start = null;
        ListNode end = null;
        ListNode finalHead = null;

        ListNode cursor = null;
        while (next != null) {
            for (int i = 0; i < k; i++) {
                if (i == 0) {
                    start = next;
                    cursor = start;
                } else {
                    cursor = cursor.next;
                    if (cursor == null) {
                        pre.next = start;
                        next = null;
                        break;
                    }
                    if (i == k - 1) {
                        // 凑齐一组
                        end = cursor;
                        next = cursor.next;
                        end.next = null;

                        // reverse
                        ListNode newHead = reverse(start);
                        if (pre != null) {
                            pre.next = newHead;
                        } else {
                            finalHead = newHead;
                        }

                        // reset pre
                        pre = start;
                    }
                }
            }
        }
        return finalHead;
    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cursor = head;
        while (cursor != null) {
            ListNode next = cursor.next;
            cursor.next = pre;
            pre = cursor;
            cursor = next;
        }
        return pre;
    }


    /**
     * 题中给的
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public String toString() {
            return val + "," + (next != null ? next.toString() : "");
        }
    }
}
