package application;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.Stack;

import static javafx.scene.paint.Color.rgb;

class UiLine {

    private UiCircle startCircle;
    private UiCircle endCircle;

    public UiLine(UiCircle startCircle, UiCircle endCircle) {
        this.startCircle = startCircle;
        this.endCircle = endCircle;
    }

    public Line getLine() {
        Line line = new Line();
        line.setStroke(rgb(200, 200, 200));
        line.setStrokeWidth(10.0);

        StackPane start = startCircle.getPane();

        line.startXProperty().bind(start.layoutXProperty().add(25));
        line.startYProperty().bind(start.layoutYProperty().add(25));

        StackPane end = endCircle.getPane();

        line.endXProperty().bind(end.layoutXProperty().add(25));
        line.endYProperty().bind(end.layoutYProperty().add(25));

        return line;
    }
}

class UiCircle {

    private Integer vertexId = -1;
    private StackPane pane;

    UiCircle(StackPane pane, Integer id) {
        this.vertexId = id;
        this.pane = pane;
    }

    public Integer getVertexId() {
        return this.vertexId;
    }

    public StackPane getPane() {
        return this.pane;
    }
}