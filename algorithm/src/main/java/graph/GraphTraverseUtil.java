package graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 🌺xuliangliang🌺
 * @Description Graph 遍历工具
 * @Date 2021/5/29
 */
public class GraphTraverseUtil {

    /**
     * 宽度遍历
     * @param vertex
     */
    public static void bfs(Vertex vertex) {
        if (vertex == null) {
            return;
        }
        Set<Vertex> traversedVertexes = new HashSet<>();

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(vertex);

        while (! queue.isEmpty()) {
            Vertex cursor = queue.poll();
            if (traversedVertexes.contains(cursor)) {
                continue;
            }
            System.out.print(cursor.getValue() + ",");
            queue.addAll(cursor.getAdjacencyVertexes());
            traversedVertexes.add(cursor);
        }
        System.out.print("\n");
    }

    /**
     * 深度遍历
     * @param vertex
     */
    public static void dfs(Vertex vertex) {
        if (vertex == null) {
            return;
        }
        Set<Vertex> traversedVertexes = new HashSet<>();

        Stack<Vertex> stack = new Stack<>();
        Vertex cursor = vertex;

        while (true) {
            if (cursor != null) {
                stack.push(cursor);
                if (! traversedVertexes.contains(cursor)) {
                    System.out.print(cursor.getValue() + ",");
                    traversedVertexes.add(cursor);
                }
            } else {
                if (stack.isEmpty()) {
                    System.out.print("\n");
                    break;
                }
                cursor = stack.pop();
            }

            cursor = cursor.getAdjacencyVertexes().stream()
                    .filter(v -> ! traversedVertexes.contains(v))
                    .findFirst()
                    .orElse(null);
        }

    }

    /**
     * 拓扑遍历(仅支持无环有向图)
     * @param graph
     */
    public static void traverseByTopologicalOrder(Graph graph) {
        Map<Vertex, Integer> inDegreeMap = graph.getVertexes()
                .values().stream().collect(Collectors.toMap(v -> v, Vertex::getInDegree));

        Set<Vertex> traversedVertexes = new HashSet<>();
        while (true) {
            for (Map.Entry<Vertex, Integer> entry : inDegreeMap.entrySet()) {
                Vertex vertex = entry.getKey();
                int inDegree = entry.getValue();

                if (inDegree == 0 && ! traversedVertexes.contains(vertex)) {
                    System.out.print(vertex.getValue() + ",");
                    traversedVertexes.add(vertex);

                    for (Vertex adjacencyVertex : vertex.getAdjacencyVertexes()) {
                        inDegreeMap.put(adjacencyVertex, inDegreeMap.get(adjacencyVertex) - 1);
                    }
                }
            }
            if (traversedVertexes.size() == inDegreeMap.size()) {
                System.out.print("\n");
                break;
            }
        }
    }
}
