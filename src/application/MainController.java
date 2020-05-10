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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML Pane mainPane;
    @FXML Pane editPane;
    @FXML RadioButton edge;
    @FXML RadioButton vertex;
    @FXML Label consoleLabel;

    private GraphModel model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GraphicService graphicService = new GraphicService(editPane);
        GraphService graphService = new GraphService();
        model = new GraphModel(graphicService, graphService);

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
                        model.addEdge(event.getX(), event.getY());
                    } else {
                        model.addVertex(event.getX(), event.getY());
                    }
                }
        );

        editPane.addEventHandler(MouseEvent.ANY, dragAndClickHandler);
    }


    public void printToLabel() {

        model.runDfs();

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
//        consoleLabel.setText(graphString);
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
