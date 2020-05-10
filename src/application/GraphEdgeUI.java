package application;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

import static javafx.scene.paint.Color.rgb;

public class GraphEdgeUI {

    private double firstPositionX;
    private double firstPositionY;

    private double secondPositionX;
    private double secondPositionY;

    private final double RADIUS = 30.0;

    GraphEdgeUI(double firstPositionX, double firstPositionY,
                double secondPositionX, double secondPositionY) {

        this.firstPositionX = firstPositionX;
        this.firstPositionY = firstPositionY;

        this.secondPositionX = secondPositionX;
        this.secondPositionY = secondPositionY;
    }

    public Group getDraw() {

        Group lineWithArrow = new Group();

        Line line = new Line();
        line.setStroke(rgb(0, 0, 0));
        line.setStrokeWidth(5.0);

        //Copy temp vars
        double _firstPositionX = firstPositionX;
        double _firstPositionY = firstPositionY;

        double _secondPositionX = secondPositionX;
        double _secondPositionY = secondPositionY;

        double hypotenuse = Math.sqrt(
                (_secondPositionY - _firstPositionY) * (_secondPositionY - _firstPositionY) +
                (_secondPositionX - _firstPositionX) * (_secondPositionX - _firstPositionX)
        );

        double cos = (_secondPositionX - _firstPositionX) / hypotenuse;
        double sin = (_secondPositionY - _firstPositionY) / hypotenuse;

        //subtract RADIUS
        double lambda = (_secondPositionX - _firstPositionX - RADIUS * cos) / (RADIUS * cos);
        secondPositionX = (_firstPositionX + lambda * _secondPositionX) / (1.0 + lambda);
        secondPositionY = (_firstPositionY + lambda * _secondPositionY) / (1.0 + lambda);

        //add RADIUS
        lambda = (RADIUS * cos) / (_secondPositionX - _firstPositionX - RADIUS * cos);
        firstPositionX = (_firstPositionX + lambda * _secondPositionX) / (1.0 + lambda);
        firstPositionY = (_firstPositionY + lambda * _secondPositionY) / (1.0 + lambda);

        line.setStartX(firstPositionX);
        line.setStartY(firstPositionY);

        line.setEndX(secondPositionX);
        line.setEndY(secondPositionY);

        Polygon arrowTriangle = new Polygon();
        //Create basic arrow
        arrowTriangle.getPoints().addAll(new Double[] {
                0.0, 0.0,
                40.0, 80.0,
                80.0, 40.0
        });
        arrowTriangle.setLayoutX(secondPositionX);
        arrowTriangle.setLayoutY(secondPositionY);

        if (_firstPositionX > _secondPositionX) {
            cos = (_firstPositionX - _secondPositionX) / hypotenuse;
        }
        double angle = Math.round(Math.toDegrees(Math.acos(cos)));
        if (angle > 90)
            angle = 90 - angle;
        double angle1 = 180 - 45 - angle;
        System.out.println(angle + " " + angle1);
        arrowTriangle.getTransforms().addAll(new Rotate(angle1));

//        //Swap vecX and vecY because we rotate on 90 degree
//        double vecY = (secondPositionX - firstPositionX) / hypotenuse * 30.0;
//        double vecX = (secondPositionY - firstPositionY) / hypotenuse * 30.0;
//
//        //Divide line in proportion
//        lambda = 10.0;
//        double midX = (firstPositionX + lambda * secondPositionX) / (1.0 + lambda);
//        double midY = (firstPositionY + lambda * secondPositionY) / (1.0 + lambda);
//
//        Double[] coords = new Double[] {
//            secondPositionX, secondPositionY,
//            midX + vecX, midY - vecY,
//            midX - vecX, midY + vecY
//        };
//        arrowTriangle.getPoints().addAll(coords);

        lineWithArrow.getChildren().addAll(line, arrowTriangle);
        return lineWithArrow;
    }
}
