package skiplist;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/14
 */
public class SkipListTest {

    /**
     * skip list test
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        SkipList<Integer, String> skipList = new SkipList<>();
        skipList.put(1, "jerry");
        System.out.println(skipList.put(1, "jerry-plus"));;
        skipList.put(2, "tom");
        System.out.println(skipList.containsKey(1));
        System.out.println(skipList.containsKey(2));
        System.out.println(skipList.remove(1));
        System.out.println(skipList.remove(2));

        int testCount = 1000;

        for (int i = 0; i < testCount; i++) {
            skipList.put(i, RandomStringUtils.randomAlphabetic(10));
            System.out.println(skipList.size());
            System.out.println(skipList.level());
        }

        TimeUnit.SECONDS.sleep(5);

        for (int i = 0; i < testCount; i++) {
            System.out.println(skipList.get(i));
        }

        TimeUnit.SECONDS.sleep(5);

        for (int i = 0; i < testCount; i++) {
            skipList.remove(i);
            System.out.println("size : " + skipList.size());
            System.out.println("level : " + skipList.level());
        }
    }
}
