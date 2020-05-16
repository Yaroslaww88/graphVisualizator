package application;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GraphicService {

    Pane graphPane;

    //contain UI of vertex/edge and id (get from the model which got from the graphService)
    ArrayList < Pair<Integer, GraphVertexUI> > vertices = new ArrayList<>();
    ArrayList < Pair<Integer, GraphEdgeUI> > edges = new ArrayList<>();

    GraphicService(Pane graphPane) {
        this.graphPane = graphPane;
    }

    /**
     * @param vertexId id of vertexUI (to find it later)
     * @return GraphVertexUI
     */
    public GraphVertexUI addGraphVertexUI(int vertexId, String label, double positionX, double positionY) {
        GraphVertexUI graphVertexUI = new GraphVertexUI(label, positionX, positionY);
        vertices.add(new Pair<>(vertexId, graphVertexUI));

        StackPane node = graphVertexUI.getDraw();
        graphPane.getChildren().add(node);

        return graphVertexUI;
    }

    /**
     * Add edge with given coords and label to Pane
     * @param edgeId id of edgeId (to find it later)
     * @return GraphEdgeUI
     */
    public GraphEdgeUI addGraphEdgeUI(int edgeId, double firstPositionX, double firstPositionY,
                               double secondPositionX, double secondPositionY) {

        GraphEdgeUI graphEdgeUI = new GraphEdgeUI(firstPositionX, firstPositionY,
                                                    secondPositionX, secondPositionY);
        edges.add(new Pair<>(edgeId, graphEdgeUI));

        Group node = graphEdgeUI.getDraw();
        graphPane.getChildren().add(node);

        return graphEdgeUI;
    }

    /**
     * Check if some vertexUI on given coordinates
     * @return boolean
     */
    public boolean isVertexOnCoordinates(double positionX, double positionY) {
        for (Pair<Integer, GraphVertexUI> vertexUI : vertices) {
            if (vertexUI.getValue().isCoordinatesInsideVertex(positionX, positionY))
                return true;
        }

        return false;
    }

    public int getGraphVertexIdByCoordinates(double positionX, double positionY) {
        for (Pair<Integer, GraphVertexUI> vertexUI : vertices) {
            if (vertexUI.getValue().isCoordinatesInsideVertex(positionX, positionY)) {
                return vertexUI.getKey();
            }
        }

        return -1;
    }

    public GraphVertexUI getGraphVertexUIbyId(int id) {
        for (Pair<Integer, GraphVertexUI> vertexUI : vertices) {
            if (vertexUI.getKey() == id) {
                return vertexUI.getValue();
            }
        }

        return new GraphVertexUI("", 0, 0);
    }
}

