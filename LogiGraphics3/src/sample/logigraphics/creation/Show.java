package sample.logigraphics.creation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Show {

    Rectangle rectangle = new Rectangle(0,0,0,0);
    Circle circle = new Circle(0,0,0);
    Line line = new Line(0,0,0,0);

    double initialX = 0.0;
    double initialY = 0.0;

    public Show(){
        rectangle.setOpacity(0.5);
        rectangle.setVisible(false);

        circle.setOpacity(0.5);
        circle.setVisible(false);

        line.setOpacity(0.5);
        line.setVisible(false);
    }


    public void showRectangle(double x, double y, Color color){
        rectangle.setY(y);
        rectangle.setX(x);
        rectangle.setFill(color);
        rectangle.setVisible(true);
        initialX = x;
        initialY = y;
    }

    public void showCircle(double x, double y, Color color){
        circle.setCenterY(y);
        circle.setCenterX(x);
        circle.setFill(color);
        circle.setVisible(true);
    }

    public void showLine(double x, double y, Color color){
        line.setStartY(y);
        line.setStartX(x);
        line.setFill(color);
        line.setVisible(true);
    }

    public void hideRectangle(){
        rectangle.setVisible(false);
        rectangle.setWidth(0);
        rectangle.setHeight(0);
    }

    public void hideCircle(){
        circle.setVisible(false);
        circle.setRadius(0);
    }

    public void hideLine(){
        line.setVisible(false);
        line.setEndY(0);
        line.setEndX(0);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Circle getCircle() {
        return circle;
    }

    public Line getLine() {
        return line;
    }

    public double getInitialX() {
        return initialX;
    }

    public double getInitialY() {
        return initialY;
    }
}
