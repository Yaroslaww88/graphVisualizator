package application;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class GraphVertexUI {

    private String text = "";
    private double positionX = 0;
    private double positionY = 0;
    private final float RADIUS = 30f;

    StackPane stackCircle;
    Circle circle;

    GraphVertexUI(String text, double positionX, double positionY) {
        this.text = text;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public StackPane getDraw() {
        circle = new Circle();
        circle.setRadius(RADIUS);

        final Random random = new Random();
        circle.setFill(Color.rgb(random.nextInt(250), random.nextInt(250), random.nextInt(250)));

        stackCircle = new StackPane();

        Text text = new Text(this.text);
        text.setFont(Font.font(40));

//        stackCircle.setLayoutX(positionX);
//        stackCircle.setLayoutY(positionY);
        stackCircle.setLayoutX(positionX - 25);
        stackCircle.setLayoutY(positionY - 25);
//        positionY -= 25;
//        positionX -= 25;

        stackCircle.getChildren().addAll(circle, text);

        System.out.println(stackCircle);

        return stackCircle;
    }

    public boolean isCoordinatesInsideVertex(double positionX, double positionY) {
        System.out.println("pos: " + positionX + " " + positionY + " " + this.positionX + " " + this.positionY);
        if (Math.abs(positionX - this.positionX) < RADIUS &&
            Math.abs(positionY - this.positionY) < RADIUS)
            return true;
        else
            return false;
    }

    private void setDragHandler() {

    }

    public void highlight() {
        circle.setFill(Color.rgb(255, 0, 0));
    }

    public double getCenterX() {
        return positionX;
    }

    public double getCenterY() {
        return positionY;
    }
}
