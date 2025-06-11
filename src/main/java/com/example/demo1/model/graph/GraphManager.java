package com.example.demo1.model.graph;

public class GraphManager {
    private final Graph graph = new Graph();
    private int nodeIdCounter = 0;

    public GraphNode createNode(double x, double y) {
        GraphNode node = new GraphNode(nodeIdCounter++, x, y, 10);
        graph.addNode(node);
        return node;
    }

    public void connectNodes(GraphNode n1, GraphNode n2, double weight) {
        n1.addNeighbor(n2, weight);
        n2.addNeighbor(n1, weight); // si le graphe est non orienté
    }

    public Graph getGraph() {
        return graph;
    }
}