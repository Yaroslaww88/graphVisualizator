package application;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import javax.swing.event.ChangeListener;
import java.net.URL;
import java.util.*;

import static java.lang.System.exit;

public class MainController implements Initializable {

    @FXML Pane mainPane;
    @FXML Pane editPane;
    @FXML RadioButton edge;
    @FXML RadioButton vertex;
    @FXML Label consoleLabel;
    @FXML TextField sourceInputField;
    @FXML TextField sinkInputField;

    @FXML Button calculateMaxFlowButton;
    @FXML Button calculateMinFlowButton;
    @FXML Button resetGraphButton;

    private double firstPositionX = -1;
    private double firstPositionY = -1;

    private double secondPositionX = -1;
    private double secondPositionY = -1;

    GraphicService graphicService;
    GraphService graphService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicService = new GraphicService(editPane);
        graphService = new GraphService(graphicService);

        DragAndClickHandler dragAndClickHandler = new DragAndClickHandler(
                /**
                 * No dragging for editPane available
                 */
                event -> {},
                /**
                 * Clicking
                 */
                event -> {
                    //add edge
                    if (edge.isSelected()) {
                        double positionX = event.getX();
                        double positionY = event.getY();

                        if (graphicService.isVertexOnCoordinates(positionX, positionY)) {

                            if (firstPositionX == -1 && firstPositionY == -1) {
                                firstPositionX = positionX;
                                firstPositionY = positionY;

                                int firstVertexId = graphicService.getGraphVertexIdByCoordinates(firstPositionX, firstPositionY);
                                this.printToLabel("Vertex " + firstVertexId + " selected");

                                return;
                            }

                            if (secondPositionX == -1 && secondPositionY == -1) {
                                secondPositionX = positionX;
                                secondPositionY = positionY;
                            }

                            int edgeId = graphService.getEdgesCount() + 1;

                            int firstVertexId = graphicService.getGraphVertexIdByCoordinates(firstPositionX, firstPositionY);
                            int secondVertexId = graphicService.getGraphVertexIdByCoordinates(secondPositionX, secondPositionY);

                            this.printToLabel("Vertex " + secondVertexId + " selected");

                            GraphVertexUI firstVertexUI = graphicService.getGraphVertexUIbyId(firstVertexId);
                            GraphVertexUI secondVertexUI = graphicService.getGraphVertexUIbyId(secondVertexId);

                            GraphEdgeUI edgeUI = graphicService.addGraphEdgeUI(edgeId, firstVertexUI.getCenterX(), firstVertexUI.getCenterY(),
                                    secondVertexUI.getCenterX(), secondVertexUI.getCenterY());

                            //Set listener to input weight
//                            TextField inputWeight = ;
                            edgeUI.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                                try {
                                    int newValueInt = Integer.parseInt(newValue);
                                    graphService.updateEdgeWeightById(edgeId, newValueInt);
                                } catch (NumberFormatException ex) {
                                    //we get exception here because we have not-int value like "*int* / *int*"
                                }
                            });

                            firstPositionX = firstPositionY = secondPositionX = secondPositionY = -1;

                            try {
                                GraphVertex firstVertex = graphService.getGraphVertexById(firstVertexId);
                                GraphVertex secondVertex = graphService.getGraphVertexById(secondVertexId);
                                graphService.addEdge(new GraphEdge(edgeId, firstVertex, secondVertex, 1, edgeUI));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                exit(1);
                            }

                            this.printToLabel("Edge " + edgeId + " selected");
                        }
                    } else {
                        //add vertex
                        double positionX = event.getX();
                        double positionY = event.getY();

                        int vertexId = graphService.getVerticesCount() + 1;

                        GraphVertexUI vertexUI = graphicService.addGraphVertexUI(vertexId, String.valueOf(vertexId), positionX, positionY);

                        graphService.addVertex(new GraphVertex(vertexId, vertexUI));

                        this.printToLabel("Vertex " + vertexId + " created");
                    }
                }
        );

        editPane.addEventHandler(MouseEvent.ANY, dragAndClickHandler);
    }

    public void calculateMaxFlow() {
        int source = Integer.parseInt(this.sourceInputField.getText());
        int sink = Integer.parseInt(this.sinkInputField.getText());
        graphService.runMaxFlow(source, sink);
//        graphicService.unlockEdgesInputs();
    }

    public void calculateMinFlow() {

    }

    public void resetGraph() {
        graphService.resetGraph();
    }

    public void printToLabel(String text) {
//        String graphString = graph.toString();
//
//        graph.dfs(1);
//
//        graphString += "Dfs route: \n";
//        for (Pair<String, Pair<Integer, Integer>> element : graph.dfsRoute) {
//            if (element.getKey().equals("Vertex")) {
//                graphString += "Visited vertex: " + element.getValue().getKey() + " \n";
//            } else {
//                graphString += "Visited edge: " + element.getValue().getKey() + " -> " + element.getValue().getValue() + "\n";
//            }
//        }
//
        consoleLabel.setText(consoleLabel.getText() + "\n" + text);
    }

    public void radioSelect(ActionEvent actionEvent) {
        if (vertex.isSelected()) {
            //ToDo add dialog menu
            //editPane.addEventHandler(MouseEvent.ANY, dragAndClickHandler);
        }
        if (edge.isSelected()) {
            //ToDo add dialog menu
            //editPane.removeEventHandler(MouseEvent.ANY, dragAndClickHandler);
        }
    }
}
