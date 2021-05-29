package graph;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/5/29
 */
public class GraphTraverseUtilTest {

    public static void main(String[] args) {
        Graph graph001 = GraphGenerator.createGraph(GraphExample.GRAPH_001);
        Vertex startVertex = graph001.getVertexes().values()
                .stream()
                .filter(v -> v.getValue() == 1)
                .findFirst()
                .orElse(null);

        GraphTraverseUtil.bfs(startVertex);
        GraphTraverseUtil.dfs(startVertex);
        GraphTraverseUtil.traverseByTopologicalOrder(graph001);
    }
}
