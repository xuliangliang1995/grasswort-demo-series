package graph;

import java.util.Map;
import java.util.Set;

/**
 * @author 🌺xuliangliang🌺
 * @Description 图生成器
 * @Date 2021/5/29
 */
public class GraphGenerator {

    /**
     * 根据弧数组入参生成图. int[] 示例: {weight, arcTail, arcHead}
     * @param arcs 弧[]
     * @return graph
     */
    public static Graph createGraph(int[][] arcs) {
        Graph graph = new Graph();
        Map<Integer, Vertex> vertexes = graph.getVertexes();
        Set<Arc> graphArcs = graph.getArcs();

        for (int[] arcInfo : arcs) {
            int weight = arcInfo[0];
            int tail = arcInfo[1];
            int head = arcInfo[2];

            if (! vertexes.containsKey(tail)) {
                vertexes.put(tail, new Vertex(tail));
            }
            if (! vertexes.containsKey(head)) {
                vertexes.put(head, new Vertex(head));
            }

            Vertex arcTail = vertexes.get(tail);
            Vertex arcHead = vertexes.get(head);
            Arc arc = new Arc(weight, arcTail, arcHead);

            graphArcs.add(arc);

            arcTail.getArcs().add(arc);
            arcTail.getAdjacencyVertexes().add(arcHead);
            arcTail.setOutDegree(arcTail.getOutDegree() + 1);
            arcHead.setInDegree(arcHead.getInDegree() + 1);
        }

        return graph;
    }

}
