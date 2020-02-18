package application;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class Graph {

    private ArrayList<GraphEdge> edges = new ArrayList<>();
    private Integer numberOfVertices = 0;
    private ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private ArrayList<Boolean> visited = new ArrayList<>();

    public Graph() {

        this.initGraph();
    };

    public Graph(ArrayList<GraphEdge> edges, Integer numberOfVertices) {
        this.edges = edges;
        this.numberOfVertices = numberOfVertices;

        this.initGraph();
    }

    public void initGraph() {
        graph = new ArrayList<>(numberOfVertices + 1);
        graph.add(new ArrayList<>());
        visited.add(false);

        for (GraphEdge edge : edges) {
            graph.get(edge.getStart()).add(edge.getEnd());
            graph.get(edge.getEnd()).add(edge.getStart());
        }

        System.out.println("after init: " + graph);
    }

    public void addEdge(GraphEdge edge) {
        edges.add(edge);

        System.out.println("added edge: " + graph);

        graph.get(edge.getStart()).add(edge.getEnd());
        graph.get(edge.getEnd()).add(edge.getStart());
    }

    public void addVertex() {
        numberOfVertices++;

        System.out.println("added vertex");

        graph.add(new ArrayList<>());
        visited.add(false);
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

    //TODO FIX!!!!!!!!!!!!!!!!!!!!!!!!!!
    public ArrayList<Pair<String, Pair<Integer, Integer>>> dfsRoute = new ArrayList<>();

    public void dfs(Integer vertex) {
        System.out.println(vertex + " " + graph);
        visited.set(vertex, true);
        if (graph.get(vertex).size() == 0) {
            return;
        }
        for (Integer to : graph.get(vertex)) {
            if (!visited.get(to)) {
                dfsRoute.add(new Pair<>("Vertex", new Pair<>(vertex, null)));
                dfsRoute.add(new Pair<>("Edge", new Pair<>(vertex, to)));
                dfs(to);
            }
        }
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

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }
}
