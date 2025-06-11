package com.example.demo1.model.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<GraphNode> nodes = new ArrayList<>();

    public void addNode(GraphNode node) {
        nodes.add(node);
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }
}