package com.example.demo1.model.graph;

import java.util.List;

public interface ShortestPathStrategy {
    List<GraphNode> findPath(Graph graph, GraphNode start, GraphNode end);
}