package application;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static javafx.scene.paint.Color.rgb;

class UiLine {

    private UiCircle startCircle;
    private UiCircle endCircle;

    public UiLine(Circle startCircle, Integer startId, Circle endCircle, Integer endId) {
        this.startCircle = new UiCircle(startCircle, startId);
        this.endCircle = new UiCircle(endCircle, endId);
    }

    public Line getLine() {
        Line line = new Line();
        line.setStroke(rgb(200, 200, 200));
        line.setStrokeWidth(10.0);

        line.startXProperty().bind(startCircle.getCircle().centerXProperty());
        line.startYProperty().bind(startCircle.getCircle().centerYProperty());

        line.endXProperty().bind(endCircle.getCircle().centerXProperty());
        line.endYProperty().bind(endCircle.getCircle().centerYProperty());

        return line;
    }
}

class UiCircle {

    private Circle circle;
    private Integer vertexId;

    UiCircle() {
        this.circle = null;
        this.vertexId = -1;
    }

    UiCircle(Circle circle) {
        this.circle = circle;
        this.vertexId = -1;
    }

    UiCircle(Circle circle, Integer id) {
        this.circle = circle;
        this.vertexId = id;
    }

    public Integer getVertexId() {
        return this.vertexId;
    }

    public Circle getCircle() {
        return this.circle;
    }
}