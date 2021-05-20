package trie;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author xuliangliang
 * @Description
 * @Date 2020/7/19
 */
class TrieTreeTest {

    private TrieTree trieTree;
    private List<String> list;

    @BeforeEach
    void setUp() {
        trieTree = new TrieTree();
        list = new LinkedList<>();
    }

    @AfterEach
    void tearDown() {
        trieTree = null;
        list = null;
    }

    @RepeatedTest(1000)
    void test() {
        String[] array = {
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10)
        };
        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            trieTree.add(str);
            list.add(str);
            assertTrue(trieTree.size() == list.size());
            assertTrue(trieTree.search(str) == listSearch(list, str));
        }

        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            trieTree.remove(str);
            clear(list, str);
            assertTrue(trieTree.size() == list.size());
            assertTrue(trieTree.search(str) == listSearch(list, str));
        }
    }


    private int listSearch(List<String> list, String str) {
        return (int)list.stream().filter(s -> Objects.equals(s, str))
                .count();
    }

    private void clear(List<String> list, String str) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (Objects.equals(s, str)) {
                iterator.remove();
            }
        }
    }


}