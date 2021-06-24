package heap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/6/23
 */
public class HeapPlusTest {

    private static HeapPlus<StudentScore> heap;

    @Test
    public void test() {
        StudentScore score1 = new StudentScore(1, "xuliang", 100);
        StudentScore score2 = new StudentScore(2, "xiaoming", 90);
        StudentScore score3 = new StudentScore(3, "jerry", 20);
        StudentScore score4 = new StudentScore(4, "tom", 40);
        heap.put(score1);
        heap.put(score2);
        heap.put(score3);
        heap.put(score4);
        assertEquals(4, heap.size());
        assertEquals(score1, heap.peek());

        score1.score = 30;
        heap.resign(score1);
        assertEquals(score2, heap.peek());

        assertEquals(score2, heap.poll());
        assertEquals(3, heap.size());

        assertTrue(heap.contains(score4));
    }

    @Test
    public void testRepeatPut() {
        StudentScore score1 = new StudentScore(1, "xuliang", 100);
        StudentScore score2 = new StudentScore(1, "xuliang", 100);
        heap.put(score1);
        assertThrows(HeapPlus.ElementAlreadyExistsException.class, () -> heap.put(score1));
        assertThrows(HeapPlus.ElementAlreadyExistsException.class, () -> heap.put(score2));
        assertEquals(1, heap.size());
    }

    @BeforeEach
    public void setUp() {
        heap = new HeapPlus<>((o1, o2) -> {
            if (o1.score != o2.score) {
                return o1.score > o2.score ? -1 : 1;
            }
            if (o1.id == o2.id) {
                return 0;
            }
            return o1.id > o2.id ? 1 : -1;
        });
    }

    @AfterEach
    public void tearDown() {
        heap = null;
    }

    static class StudentScore {
        private int id;
        private String name;
        private int score;

        public StudentScore(int id, String name, int score) {
            this.id = id;
            this.name = name;
            this.score = score;
        }

        @Override
        public int hashCode() {
            return id;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StudentScore) {
                return this.id == ((StudentScore) obj).id;
            }
            return false;
        }
    }
}
