package application;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.util.ArrayList;

import static javafx.scene.paint.Color.rgb;

public class GraphEdgeUI {

    private double firstPositionX;
    private double firstPositionY;

    private double secondPositionX;
    private double secondPositionY;

    private final double RADIUS = 20.0;

    private TextField textField;

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
                20.0, 40.0,
                40.0, 20.0
        });
        arrowTriangle.setLayoutX(secondPositionX);
        arrowTriangle.setLayoutY(secondPositionY);

        double lineAngle = Math.round(Math.toDegrees(Math.acos(cos)));

        System.out.println("a: " + firstPositionX + " " + firstPositionY + " " + secondPositionX + " " + secondPositionY);

        if (firstPositionX <= secondPositionX && firstPositionY >= secondPositionY) {
            double angle = 180 - 45 - lineAngle;
            arrowTriangle.getTransforms().addAll(new Rotate(angle));
        }

        if (firstPositionX <= secondPositionX && firstPositionY <= secondPositionY) {
            double angle = 0;
            System.out.println("line angle: " + lineAngle);
            if (lineAngle <= 45) {
                angle = lineAngle + 135;
            } else {
                angle = 180 + lineAngle - 45;
            }
            arrowTriangle.getTransforms().addAll(new Rotate(angle));
        }

        if (firstPositionX >= secondPositionX && firstPositionY >= secondPositionY) {
            double angle = 180 - 45 - lineAngle;
            arrowTriangle.getTransforms().addAll(new Rotate(angle));
        }

        if (firstPositionX >= secondPositionX && firstPositionY <= secondPositionY) {
            double angle = 0;
            System.out.println("line angle: " + lineAngle);
            if (lineAngle <= 45) {
                angle = lineAngle + 135;
            } else {
                angle = 180 + lineAngle - 45;
            }
            arrowTriangle.getTransforms().addAll(new Rotate(angle));
        }

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

        this.textField = new TextField();
        this.textField.setLayoutX(secondPositionX);
        this.textField.setLayoutY(secondPositionY);
        this.textField.setMaxWidth(80);

        lineWithArrow.getChildren().addAll(line, arrowTriangle, this.textField);
        return lineWithArrow;
    }

    public TextField getTextField() {
        return this.textField;
    }

    public void lockWeightInputField() {
        this.textField.setEditable(false);
    }

    public void unlockWeightInputField() {
        this.textField.setEditable(true);
    }

    public void setText(String text) {
        this.textField.setText(text);
    }
}
