package acautomaton;

import java.util.*;

/**
 * @author 🌺xuliangliang🌺
 * @Description AC 自动机
 * @Date 2021/6/12
 */
public class AcAutomaton {
    /**
     * 根节点
     */
    private final Node root = new Node();
    /**
     * 除 root 外所有节点(用于清空状态便于下次检查)
     */
    private final List<Node> nodes = new LinkedList<>();

    public static void main(String[] args) {
        AcAutomaton acAutomaton = new AcAutomaton();

        // 添加敏感词汇
        acAutomaton.insert("abcde");
        acAutomaton.insert("bcdek");
        acAutomaton.insert("cd");
        acAutomaton.insert("cde");

        // build 之后不支持再添加敏感词汇
        acAutomaton.build();

        // 敏感词汇检测
        List<String> arr1 = acAutomaton.check("abcdekscdeabc");
        for (String str : arr1) {
            System.out.println(str);
        }

        List<String> arr2 = acAutomaton.check("abcdekscdeabc");
        for (String str : arr2) {
            System.out.println(str);
        }
    }

    /**
     * 添加词汇到词库
     * @param s
     */
    public void insert(String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        char[] chars = s.toCharArray();
        Node cursor = root;
        for (char c : chars) {
            int index = c - 'a';
            if (cursor.getNexts()[index] == null) {
                Node newNode = new Node(c);
                cursor.getNexts()[index] = newNode;
                nodes.add(newNode);
            }
            cursor = cursor.getNexts()[index];
        }
        cursor.setEnd(true);
        cursor.setStr(s);
    }

    /**
     * 构建 fail 指针
     */
    public void build() {
        // 宽度优先遍历
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (! queue.isEmpty()) {
            Node current = queue.poll();
            Node[] nexts = current.getNexts();
            for (Node next : nexts) {
                if (next != null) {
                    queue.add(next);
                    Node cur = current;
                    char c = next.getValue();
                    int cIndex = c - 'a';
                    while (cur.getFail() != null) {
                        if (cur.getFail().getNexts()[cIndex] != null) {
                            next.setFail(cur.getFail().getNexts()[cIndex]);
                            break;
                        }
                        cur = cur.getFail();
                    }
                    if (next.getFail() == null) {
                        next.setFail(root);
                    }
                }
            }
        }
    }

    /**
     * 文本校验
     * @param text
     * @return
     */
    public List<String> check(String text) {
        List<String> array = new LinkedList<>();
        if (text == null || text.length() == 0) {
            return null;
        }
        char[] chars = text.toCharArray();
        Node current = root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            while (current.getNexts()[index] == null && current.getFail() != null) {
                current = current.getFail();
            }
            if (current.getNexts()[index] == null && current == root) {
                continue;
            }

            current = current.getNexts()[index];
            Node follow = current;
            while (follow != root) {
                if (follow.isEndVia()) {
                    break;
                }
                if (follow.isEnd()) {
                    array.add(follow.getStr());
                    follow.setEndVia(true);
                }
                follow = follow.getFail();
            }
        }
        clear();
        return array;
    }

    /**
     * 清空状态
     */
    private void clear() {
        nodes.forEach(n -> n.setEndVia(false));
    }


}
