package com.example.demo1.model.graph;

import java.util.*;

public class DijkstraStrategy implements ShortestPathStrategy {
    @Override
    public List<GraphNode> findPath(Graph graph, GraphNode start, GraphNode end) {
        Map<GraphNode, Double> distances = new HashMap<>();
        Map<GraphNode, GraphNode> previous = new HashMap<>();
        PriorityQueue<GraphNode> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        for (GraphNode node : graph.getNodes()) {
            distances.put(node, Double.POSITIVE_INFINITY);
            previous.put(node, null);
        }
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            if (current == end) break;

            for (Map.Entry<GraphNode, Double> neighborEntry : current.getNeighbors().entrySet()) {
                GraphNode neighbor = neighborEntry.getKey();
                double weight = neighborEntry.getValue();
                double alt = distances.get(current) + weight;
                if (alt < distances.get(neighbor)) {
                    distances.put(neighbor, alt);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<GraphNode> path = new ArrayList<>();
        for (GraphNode at = end; at != null; at = previous.get(at)) {
            path.add(0, at);
        }
        return path;
    }
}