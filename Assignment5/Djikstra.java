package Assignment5;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Djikstra {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Djikstra <total_vertices> <start_vertex>");
            System.exit(1);
        }

        int totalVertices = Integer.parseInt(args[0]);
        int startVertex = Integer.parseInt(args[1]);

        Map<Integer, List<Edge>> adjacencyList = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Assignment5/sample_graph.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parsedLine = line.split("\\s+");
                int vertex = Integer.parseInt(parsedLine[0]);
                List<Edge> edges = new ArrayList<>();

                for (int i = 1; i < parsedLine.length; i++) {
                    String[] edgeParts = parsedLine[i].split(",");
                    int neighbor = Integer.parseInt(edgeParts[0]);
                    int weight = Integer.parseInt(edgeParts[1]);
                    edges.add(new Edge(neighbor, weight));
                }

                adjacencyList.put(vertex, edges);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int[] distances = dijkstra(adjacencyList, startVertex);

        for (int i = 1; i <= totalVertices; i++) {
            if (i > 1) {
                System.out.print(",");
            }
            System.out.print(distances[i]);
        }
    }

    private static int[] dijkstra(Map<Integer, List<Edge>> adjacencyList, int startVertex) {
        int[] distances = new int[adjacencyList.size() + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        priorityQueue.add(new Node(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            int currentVertex = currentNode.vertex;

            for (Edge neighbor : adjacencyList.getOrDefault(currentVertex, Collections.emptyList())) {
                int newDistance = distances[currentVertex] + neighbor.weight;

                if (newDistance < distances[neighbor.vertex]) {
                    distances[neighbor.vertex] = newDistance;
                    priorityQueue.add(new Node(neighbor.vertex, newDistance));
                }
            }
        }

        return distances;
    }

    static class Edge {
        int vertex;
        int weight;

        public Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    static class Node {
        int vertex;
        int distance;

        public Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}
