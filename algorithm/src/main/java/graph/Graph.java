package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 🌺xuliangliang🌺
 * @Description 图
 * @Date 2021/5/29
 */
public class Graph {
    /**
     * 顶点
     */
    private Map<Integer, Vertex> vertexes;

    /**
     * 有向边
     */
    private Set<Arc> arcs;

    public Graph() {
        this.vertexes = new HashMap<>();
        this.arcs = new HashSet<>();
    }

    public Map<Integer, Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(Map<Integer, Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public Set<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(Set<Arc> arcs) {
        this.arcs = arcs;
    }
}
