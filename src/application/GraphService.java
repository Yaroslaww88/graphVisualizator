package application;

import javafx.application.Platform;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GraphService {

    private ArrayList<GraphEdge> edges = new ArrayList<>();
    private ArrayList<GraphVertex> vertices = new ArrayList<>();

    private ArrayList <ArrayList <Pair<Integer, Integer> > > graph = new ArrayList<>();

    GraphService() {
        graph.add(new ArrayList<>());
    }

    public int getVerticesCount() {
        return vertices.size();
    }

    public int getEdgesCount() {
        return edges.size();
    }

    public void addVertex(GraphVertex vertex) {
        vertices.add(vertex);

        graph.add(new ArrayList<>());
    }

    public void addEdge(GraphEdge edge) {
        edges.add(edge);

        System.out.println(graph.size());

        int from = edge.getFrom();
        int to = edge.getTo();

        System.out.println(from + "->" + to);

        graph.get(from).add(new Pair<>(to, 0));
    }

    public void updateEdgeWeight(int edgeID, int newValue) {
        for (GraphEdge edge : edges) {
            if (edge.getId() == edgeID) {
                edge.setWeight(newValue);
            }
        }
    }

    boolean[] used;
    public void runDfs() {
        used = new boolean[graph.size()];
        dfs(1);
    }

    private void wait(int timeInMilliseconds) {
        try {
            Thread.sleep(timeInMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void dfs(int v) {
        used[v] = true;
        new Thread(new Runnable() {
            @Override public void run() {
                for (GraphVertex vertex : vertices) {
                    if (vertex.id == v) {
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                vertex.vertexUI.highlight();
                            }
                        });
                        break;
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                System.out.println("passed: " + v);
                for (Pair<Integer, Integer> to : graph.get(v)) {
                    if (!used[to.getKey()])
                        dfs(to.getKey());
                }
            }
        }).start();


    }
}
