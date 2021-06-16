package unionset;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/16
 */
public class UnionSetTest {

    @Test
    public void test() {
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("d");
        set.add("e");
        set.add("f");
        set.add("g");

        UnionSet<String> unionSet = new UnionSet<>(set);
        assertEquals(set.size(), unionSet.sets());

        unionSet.union("a", "b");
        unionSet.union("c", "d");
        unionSet.union("e", "f");
        unionSet.union("f", "g");

        assertTrue(unionSet.isSameSet("a", "b"));
        assertTrue(unionSet.isSameSet("c", "d"));
        assertTrue(unionSet.isSameSet("e", "f"));
        assertTrue(unionSet.isSameSet("f", "g"));
        assertTrue(unionSet.isSameSet("e", "g"));

        assertEquals(3, unionSet.sets());
    }
}
