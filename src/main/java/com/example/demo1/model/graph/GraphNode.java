package com.example.demo1.model.graph;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.Map;

public class GraphNode extends Circle {
    private final int nodeId; // Renamed from id to nodeId
    private final Map<GraphNode, Double> neighbors = new HashMap<>();
    private Line connectionLine; // Added to store connection line

    public GraphNode(int nodeId, double x, double y, double radius) {
        super(x, y, radius);
        this.nodeId = nodeId;
        setFill(Color.LIGHTBLUE);
        setStroke(Color.DARKBLUE);
    }

    public int getNodeId() { // Renamed from getId
        return nodeId;
    }

    public Map<GraphNode, Double> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(GraphNode node, double weight) {
        neighbors.put(node, weight);
    }

    // Added to return the Circle object (self)
    public Circle getCircle() {
        return this;
    }

    // Added to store the connection line
    public void setConnectionLine(GraphNode neighbor, Line line) {
        this.connectionLine = line;
    }

    public Line getConnectionLine() {
        return connectionLine;
    }
}