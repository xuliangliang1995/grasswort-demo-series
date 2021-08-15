package codetop;

import org.junit.jupiter.api.RepeatedTest;
import sort.util.IntArrayUtil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/8/15
 */
public class LinkedListHasCycleTest {

    private LinkedListHasCycle linkedListHasCycle = new LinkedListHasCycle();

    @RepeatedTest(1000)
    public void test() {
        LinkedListInfo info = randomListNode();
        assertEquals(info.hasCycle, linkedListHasCycle.hasCycle(info.head));
    }


    public LinkedListInfo randomListNode() {
        Random random = new Random();
        int size = random.nextInt(5) + 5;
        int[] arr = IntArrayUtil.randomArray(size, 0, 10000);
        LinkedListHasCycle.ListNode[] nodeArr = new LinkedListHasCycle.ListNode[arr.length];

        for (int i = 0; i < arr.length; i++) {
            nodeArr[i] = new LinkedListHasCycle.ListNode(arr[i]);
            if (i - 1 >= 0) {
                nodeArr[i - 1].next = nodeArr[i];
            }
        }

        boolean generateCycle = random.nextBoolean();
        if (generateCycle) {
            int index = random.nextInt(size - 1);
            nodeArr[size - 1].next = nodeArr[index];
        }

        return new LinkedListInfo(nodeArr[0], generateCycle);
    }

    public class LinkedListInfo {
        private LinkedListHasCycle.ListNode head;
        private final boolean hasCycle;

        public LinkedListInfo(LinkedListHasCycle.ListNode head, boolean hasCycle) {
            this.head = head;
            this.hasCycle = hasCycle;
        }
    }
}
