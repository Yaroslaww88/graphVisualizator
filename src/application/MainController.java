package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML Pane mainPane;
    @FXML Pane editPane;
    @FXML RadioButton edge;
    @FXML RadioButton vertex;
    @FXML Label consoleLabel;

    private Circle startCircle = null;
    private Circle endCircle = null;

    /**
     * All lines that shown on screen
     */
    ArrayList<UiLine> uiLines = new ArrayList<>();
    Graph graph = new Graph();

    DragAndClickHandler dragAndClickHandler = new DragAndClickHandler(
            /**
             * No dragging for editPane available
             */
            event -> {},
            /**
             * Clicking
             */
            event -> {
                Circle circle = new Circle();
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
                circle.setRadius(30.0f);

                final Random random = new Random();
                circle.setFill(Color.rgb(random.nextInt(250), random.nextInt(250), random.nextInt(250)));

                circle.addEventHandler(MouseEvent.ANY, new DragAndClickHandler(
                        /**
                         * Dragging (works only when we add vertexes)
                         */
                        circleEvent -> {
                            if (vertex.isSelected()) {
                                circle.setCenterX(circleEvent.getX());
                                circle.setCenterY(circleEvent.getY());
                            }
                        },
                        /**
                         * Clicking (works differently in edge and vertex mode)
                         */
                        circleEvent -> {
                            if (vertex.isSelected()) {

                            } else {
                                if (startCircle == null) {
                                    startCircle = circle;
                                } else {
                                    endCircle = circle;

                                    UiLine uiLine = new UiLine(startCircle, endCircle);
                                    Line line = uiLine.getLine();

                                    uiLines.add(uiLine);
                                    editPane.getChildren().add(line);
                                    /*TODO add Model instead of directly communicate with Graph*/
                                    /*GraphEdge edge = new GraphEdge()*/

                                    startCircle = null;
                                    endCircle = null;
                                }
                            }
                        }
                ));

                /*TODO add Model instead of directly communicate with Graph*/
                graph.addVertex();
                editPane.getChildren().add(circle);
            }
    );

    public void printToLabel() {

        String graphString = graph.toString();

        consoleLabel.setText(graphString);
    }

    public void radioSelect(ActionEvent actionEvent) {
        if (vertex.isSelected()) {
            editPane.addEventHandler(MouseEvent.ANY, dragAndClickHandler);
        }
        if (edge.isSelected()) {
            editPane.removeEventHandler(MouseEvent.ANY, dragAndClickHandler);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
