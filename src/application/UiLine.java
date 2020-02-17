package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import static javafx.scene.paint.Color.rgb;

public class UiLine {

    private Circle startCircle;

    private Circle endCircle;

    public UiLine(Circle startCircle, Circle endCircle) {
        this.startCircle = startCircle;
        this.endCircle = endCircle;
    }

    public Line getLine() {
        Line line = new Line();
        line.setStroke(rgb(200, 200, 200));
        line.setStrokeWidth(10.0);

        line.startXProperty().bind(startCircle.centerXProperty());
        line.startYProperty().bind(startCircle.centerYProperty());

        line.endXProperty().bind(endCircle.centerXProperty());
        line.endYProperty().bind(endCircle.centerYProperty());

        return line;
    }

}
