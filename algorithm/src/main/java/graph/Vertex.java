package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 🌺xuliangliang🌺
 * @Description 图的术语 : 顶点 (图分为无向图和有向图,无向图也可以用有向图来表示)
 * @Date 2021/5/29
 */
public class Vertex {

    /**
     * 结点值(可以理解为结点标识,不重复)
     */
    private int value;

    /**
     * 入度(弧头连接本结点的弧的数量)
     */
    private int inDegree;

    /**
     * 出度(弧尾连接本结点的弧的数量)
     */
    private int outDegree;

    /**
     * 邻接点
     * 术语 : 有向边 <5,3> 表示 5 邻接到 3, 3 邻接自 5
     */
    private List<Vertex> adjacencyVertexes;

    /**
     * 有向边(弧)
     */
    private List<Arc> arcs;

    public Vertex(int value) {
        this.value = value;
        this.adjacencyVertexes = new LinkedList<>();
        this.arcs = new LinkedList<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getInDegree() {
        return inDegree;
    }

    public void setInDegree(int inDegree) {
        this.inDegree = inDegree;
    }

    public int getOutDegree() {
        return outDegree;
    }

    public void setOutDegree(int outDegree) {
        this.outDegree = outDegree;
    }

    public List<Vertex> getAdjacencyVertexes() {
        return adjacencyVertexes;
    }

    public void setAdjacencyVertexes(List<Vertex> adjacencyVertexes) {
        this.adjacencyVertexes = adjacencyVertexes;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(List<Arc> arcs) {
        this.arcs = arcs;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Vertex)) {
            return false;
        }

        return this.hashCode() == obj.hashCode();
    }
}
