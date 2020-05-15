package application;

import javafx.application.Platform;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GraphService {

    private ArrayList<GraphEdge> edges = new ArrayList<>();
    private ArrayList<GraphVertex> vertices = new ArrayList<>();

    private ArrayList <ArrayList <GraphEdge> > graph = new ArrayList<>();

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

        graph.get(from).add(edge);
    }

    public void updateEdgeWeight(int edgeID, int newValue) {
        for (GraphEdge edge : edges) {
            if (edge.getId() == edgeID) {
                edge.setWeight(newValue);
            }
        }
    }

    private int bfs(int s, int t, int[] parent, int[][] capacity) {
        Arrays.fill(parent, -1);
        parent[s] = -2;
        LinkedList<Pair<Integer, Integer>> queue = new LinkedList<>();
        int INF = 100000000;
        queue.push(new Pair<>(s, INF));

        System.out.println(graph.size() + " " + graph.get(0).size() + " " + graph.get(1).size() + " " + graph.get(2).size());
        while (queue.size() != 0) {
            Pair<Integer, Integer> front = queue.poll();
            int cur = front.getKey();
            int flow = front.getValue();

            for (GraphEdge adjEdge : graph.get(cur)) {
                int next = adjEdge.getTo();
                System.out.println("next: " + next);
                if (parent[next] == -1 && capacity[cur][next] > 0) {
                    parent[next] = cur;
                    int new_flow = Math.min(flow, capacity[cur][next]);
                    if (next == t)
                        return new_flow;
                    queue.push(new Pair<>(next, new_flow));
                }
            }
        }

        return 0;
    }

    private void maxFlow(int s, int t) {
        int flow = 0;
        int[] parent = new int[graph.size()];

        int[][] capacity = new int[graph.size()][graph.size()];
        for (GraphEdge edge : edges) {
            capacity[edge.getFrom()][edge.getTo()] = edge.getWeight();
            System.out.println("weight: " + edge.getWeight());
        }

        while (true) {
            int newFlow = bfs(s, t, parent, capacity);
            if (newFlow == 0)
                break;
            flow += newFlow;
            int cur = t;
            while (cur != s) {
                int prev = parent[cur];
                capacity[prev][cur] -= newFlow;
                capacity[cur][prev] += newFlow;
                cur = prev;
            }
        }

        System.out.println("flow: " + flow);
    }

    public void runMaxFlow() {
        maxFlow(1, 3);
    }

//    private void dfs(int v) {
//        used[v] = true;
//        new Thread(new Runnable() {
//            @Override public void run() {
//                for (GraphVertex vertex : vertices) {
//                    if (vertex.id == v) {
//                        Platform.runLater(new Runnable() {
//                            @Override public void run() {
//                                vertex.vertexUI.highlight();
//                            }
//                        });
//                        break;
//                    }
//                }
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//
//                System.out.println("passed: " + v);
//                for (Pair<Integer, Integer> to : graph.get(v)) {
//                    if (!used[to.getKey()])
//                        dfs(to.getKey());
//                }
//            }
//        }).start();
//
//
//    }
}
