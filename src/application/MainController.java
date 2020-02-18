package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML Pane mainPane;
    @FXML Pane editPane;
    @FXML RadioButton edge;
    @FXML RadioButton vertex;
    @FXML Label consoleLabel;

    private UiCircle startCircle = null;
    private UiCircle endCircle = null;

    /**
     * All lines that shown on screen
     */
    Graph graph = new Graph();
    HashMap<StackPane, Integer> map = new HashMap();

    DragAndClickHandler dragAndClickHandler = new DragAndClickHandler(
            /**
             * No dragging for editPane available
             */
            event -> {},
            /**
             * Clicking
             */
            event -> {

                if (edge.isSelected()) {
                    return;
                }

                Circle circle = new Circle();
                circle.setRadius(30.0f);

                final Random random = new Random();
                circle.setFill(Color.rgb(random.nextInt(250), random.nextInt(250), random.nextInt(250)));

                StackPane stackCircle = new StackPane();

                graph.addVertex();

                Text text = new Text(graph.getNumberOfVertices().toString());

                stackCircle.setLayoutX(event.getX() - 25);
                stackCircle.setLayoutY(event.getY() - 25);

                stackCircle.getChildren().addAll(circle, text);

                /**
                 *  Connect StackPane to corresponding VertexId
                 */
                map.put(stackCircle, graph.getNumberOfVertices());

                stackCircle.addEventHandler(MouseEvent.ANY, new DragAndClickHandler(
                        /**
                         * Dragging (works only when we add vertexes)
                         */
                        circleEvent -> {
                            if (vertex.isSelected()) {
                                stackCircle.setLayoutX(circleEvent.getSceneX() - 35);
                                stackCircle.setLayoutY(circleEvent.getSceneY() - 35);
                            }
                        },
                        /**
                         * Clicking (works differently in edge and vertex mode)
                         */
                        circleEvent -> {
                            if (vertex.isSelected()) {

                            } else {
                                if (startCircle == null) {
                                    /**
                                     * Create new UiCircle with stackCircle (StackPane) and VertexId of stackCircle (get from map)
                                     */
                                    startCircle = new UiCircle(stackCircle, map.get(stackCircle));
                                } else {
                                    /**
                                     * Create new UiCircle with stackCircle (StackPane) and VertexId of stackCircle (get from map)
                                     */
                                    endCircle = new UiCircle(stackCircle, map.get(stackCircle));

                                    UiCircle startUiCircle = startCircle;
                                    UiCircle endUiCircle = endCircle;

                                    UiLine uiLine = new UiLine(
                                            startUiCircle,
                                            endUiCircle
                                    );

                                    Line line = uiLine.getLine();

                                    editPane.getChildren().add(line);

                                    GraphEdge edge = new GraphEdge(startCircle.getVertexId(), endCircle.getVertexId(), 0);
                                    graph.addEdge(edge);

                                    startCircle = null;
                                    endCircle = null;

                                    System.out.println(stackCircle.getLayoutX());
                                }
                            }
                        }
                ));

                editPane.getChildren().add(stackCircle);
            }
    );

    public void printToLabel() {

        String graphString = graph.toString();

        consoleLabel.setText(graphString);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editPane.addEventHandler(MouseEvent.ANY, dragAndClickHandler);
    }
}
