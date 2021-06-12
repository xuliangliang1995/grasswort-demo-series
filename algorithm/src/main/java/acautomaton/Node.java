package acautomaton;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/12
 */
public class Node {

    private char value;

    private Node[] nexts = new Node[26];

    private Node fail;

    private boolean end;

    private boolean endVia;

    private String str;

    public Node(char value) {
        this.value = value;
    }

    public Node() {
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public Node[] getNexts() {
        return nexts;
    }

    public void setNexts(Node[] nexts) {
        this.nexts = nexts;
    }

    public Node getFail() {
        return fail;
    }

    public void setFail(Node fail) {
        this.fail = fail;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isEndVia() {
        return endVia;
    }

    public void setEndVia(boolean endVia) {
        this.endVia = endVia;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
