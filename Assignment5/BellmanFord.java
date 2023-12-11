package Assignment5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BellmanFord {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java BellmanFordShortestPath <total_vertices> <start_vertex>");
            System.exit(1);
        }

        int totalVertices = Integer.parseInt(args[0]);
        int startVertex = Integer.parseInt(args[1]);

        Map<Integer, List<Edge>> adjacencyList = new HashMap<>();

        // Read input from file and construct adjacency list
        try (BufferedReader br = new BufferedReader(new FileReader("Assignment5/sample_graph.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int vertex = Integer.parseInt(parts[0]);
                List<Edge> edges = new ArrayList<>();

                for (int i = 1; i < parts.length; i++) {
                    String[] edgeParts = parts[i].split(",");
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

        // Find shortest paths using Bellman-Ford algorithm
        int[] distances = bellmanFord(adjacencyList, totalVertices, startVertex);

        // Print the result
        for (int i = 1; i <= totalVertices; i++) {
            if(i > 1) {
                System.out.print(",");
            }
            System.out.print(distances[i]);
        }
    }

    private static int[] bellmanFord(Map<Integer, List<Edge>> adjacencyList, int totalVertices, int startVertex) {
        int[] distances = new int[totalVertices + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        // Relax edges repeatedly
        for (int i = 1; i <= totalVertices - 1; i++) {
            for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
                int u = entry.getKey();
                for (Edge edge : entry.getValue()) {
                    int v = edge.vertex;
                    int weight = edge.weight;

                    if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                        distances[v] = distances[u] + weight;
                    }
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
}
