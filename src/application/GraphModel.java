package application;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class GraphModel {

    private GraphService graphService;
    private GraphicService graphicService;

    private double firstPositionX = -1;
    private double firstPositionY = -1;

    private double secondPositionX = -1;
    private double secondPositionY = -1;

    public GraphModel(GraphicService graphicService, GraphService graphService) {
        this.graphicService = graphicService;
        this.graphService = graphService;
    }

    public void addVertex(double positionX, double positionY) {

        int vertexId = graphService.getVerticesCount() + 1;

        GraphVertexUI vertexUI = graphicService.addGraphVertexUI(vertexId, String.valueOf(vertexId), positionX, positionY);

        graphService.addVertex(new GraphVertex(vertexId, vertexUI));
    }

    public void addEdge(double positionX, double positionY) {
        if (graphicService.isVertexOnCoordinates(positionX, positionY)) {

            if (firstPositionX == -1 && firstPositionY == -1) {
                firstPositionX = positionX;
                firstPositionY = positionY;
                return;
            }

            if (secondPositionX == -1 && secondPositionY == -1) {
                secondPositionX = positionX;
                secondPositionY = positionY;
            }

            int edgeId = graphService.getEdgesCount() + 1;

            int firstVertexId = graphicService.getGraphVertexIdByCoordinates(firstPositionX, firstPositionY);
            int secondVertexId = graphicService.getGraphVertexIdByCoordinates(secondPositionX, secondPositionY);

            GraphVertexUI firstVertexUI = graphicService.getGraphVertexUIbyId(firstVertexId);
            GraphVertexUI secondVertexUI = graphicService.getGraphVertexUIbyId(secondVertexId);

            GraphEdgeUI edgeUI = graphicService.addGraphEdgeUI(edgeId, firstVertexUI.getCenterX(), firstVertexUI.getCenterY(),
                    secondVertexUI.getCenterX(), secondVertexUI.getCenterY());

            firstPositionX = firstPositionY = secondPositionX = secondPositionY = -1;

            //TODO add weight
            graphService.addEdge(new GraphEdge(edgeId, firstVertexId, secondVertexId, 1, edgeUI));
        }
    }

    public void runDfs() {
        graphService.runDfs();
    }
}