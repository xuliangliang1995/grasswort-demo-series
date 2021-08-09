package juc.map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuliangliang
 * @Description
 * @Date 2021/8/9
 */
public class ConcurrentHashMapDemo {

    static class Key {

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }


    public static void main(String[] args) {
        ConcurrentHashMap<Key, String> map = new ConcurrentHashMap<>();
        map.put(new Key(), "hello");
        map.put(new Key(), "world");
        map.put(new Key(), "!");
    }
}
