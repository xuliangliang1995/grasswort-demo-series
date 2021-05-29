package graph;

/**
 * @author 🌺xuliangliang🌺
 * @Description 有向边(弧) 术语 <5, 3>  5(弧尾) -> 3(弧头)
 * @Date 2021/5/29
 */
public class Arc {
    /**
     * 权重
     */
    private int weight;

    /**
     * 弧尾
     */
    private Vertex tail;

    /**
     * 弧头
     */
    private Vertex head;

    public Arc(int weight, Vertex tail, Vertex head) {
        this.weight = weight;
        this.tail = tail;
        this.head = head;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Vertex getTail() {
        return tail;
    }

    public void setTail(Vertex tail) {
        this.tail = tail;
    }

    public Vertex getHead() {
        return head;
    }

    public void setHead(Vertex head) {
        this.head = head;
    }

    @Override
    public int hashCode() {
        return tail.getValue() * 1000 + head.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Arc)) {
            return false;
        }

        return  (tail.getValue() == ((Arc) obj).getTail().getValue())
                && (head.getValue() == ((Arc) obj).getHead().getValue());
    }

}
