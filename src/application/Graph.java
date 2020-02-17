package application;

import java.util.ArrayList;

public class Graph {

    private ArrayList<GraphEdge> edges = new ArrayList<>();
    private Integer numberOfVertices = 0;

    public Graph() {

    };

    public Graph(ArrayList<GraphEdge> edges, Integer numberOfVertices) {
        this.edges = edges;
        this.numberOfVertices = numberOfVertices;
    }

    public void addEdge(GraphEdge edge) {
        edges.add(edge);
    }

    public void addVertex() {
        numberOfVertices++;
    }

    public Integer getNumberOfVertices() {
        return this.numberOfVertices;
    }

    public String toString() {

        String result = "";

        result += ("Graph contain " + numberOfVertices + " vertices: \n");

        for (GraphEdge edge : edges) {
            result += edge.toString() + "\n";
        }

        return result;
    }
}

class GraphEdge {

    private Integer start;
    private Integer end;
    private Integer weight = 0;

    public GraphEdge(Integer start, Integer end, Integer weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public String toString() {
        return ("Edge: " + start + " -> " + end);
    }
}
