package application;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GraphService {

    private static ArrayList<GraphEdge> edges = new ArrayList<>();
    private static ArrayList<GraphVertex> vertices = new ArrayList<>();

    private static ArrayList <ArrayList <GraphEdge> > graph = new ArrayList<>();

    private GraphicService graphicService;

    GraphService(GraphicService graphicService) {
        graph.add(new ArrayList<>());
        this.graphicService = graphicService;
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

    public void updateEdgeWeightById(int edgeID, int newValue) {
        for (GraphEdge edge : edges) {
            if (edge.getId() == edgeID) {
                edge.setWeight(newValue);
            }
        }
    }

    public GraphVertex getGraphVertexById(int id) throws Exception {
        for (GraphVertex vertex : vertices) {
            if (vertex.id == id)
                return vertex;
        }

        throw new Exception("Vertex with given id is not found");
    }

    static class Algorithm {
         static int bfs(int s, int t, int[] parent, int[][] capacity) {
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

        static void maxFlow(int s, int t) {
            int flow = 0;
            int[] parent = new int[graph.size()];

            int[][] capacity = new int[graph.size()][graph.size()];
            for (int i = 0; i < graph.size(); i++) {
                for (int j = 0; j < graph.size(); j++) {
                    capacity[i][j] = 0;
                }
            }
            GraphEdge[][] capacityWithEdges = new GraphEdge[graph.size()][graph.size()];
            for (GraphEdge edge : edges) {
                capacity[edge.getFrom()][edge.getTo()] = edge.getWeight();
                capacityWithEdges[edge.getFrom()][edge.getTo()] = edge;
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
//                    System.out.println(capacity[cur][prev] + "/" + capacity[prev][cur]);
//                    System.out.print(capacityWithEdges[prev][cur]);
//                    System.out.print(capacityWithEdges[prev][cur].edgeUI);
//                    + "/" + capacity[cur][prev]
                    capacityWithEdges[prev][cur].edgeUI.setText(
                            String.valueOf(capacityWithEdges[prev][cur].getWeight() - capacity[prev][cur])
                    );
                    cur = prev;
                }
            }

            System.out.println("flow: " + flow);
        }
    }

    public void lockEdgesInputs() {
        for (GraphEdge edge : edges) {
            edge.lockChanges();
        }
    }

    public void unlockEdgesInputs() {
        for (GraphEdge edge : edges) {
            edge.unlockChanges();
        }
    }

    public void runMaxFlow(int source, int sink) {
        this.lockEdgesInputs();
        for (GraphEdge edge : edges) {
            edge.edgeUI.setText(edge.getWeight() + "/0");
        }
        Algorithm.maxFlow(source, sink);
    }

    public void resetGraph() {
        this.unlockEdgesInputs();
        for (GraphEdge edge : edges) {
            System.out.println("weight: " + edge.getWeight());
            edge.edgeUI.setText(String.valueOf(edge.getWeight()));
        }
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
