package sample;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class OnMouseMoved {
    public void onMouseMoved() {
        Main.scene.setOnMouseMoved(event1 -> {
            if (Main.isOnAction) {

                if (Main.selector == 1) {

                    Rectangle circle1 = (Rectangle) Main.nodeAction;
                    circle1.setWidth(event1.getX() - circle1.getX());
                    circle1.setHeight(event1.getY() - circle1.getY());
                    Main.rectangle = circle1;

                }

                if (Main.selector == 2) {

                    Main.moveY = event1.getY();
                    Main.moveX = event1.getX();
                    Circle circle1 = (Circle) Main.nodeAction;
                    circle1.setRadius(event1.getX() - circle1.getCenterX());
                    Main.circle = circle1;

                }
                if (Main.selector == 3) {
                    Main.line.setEndX(event1.getX());
                    Main.line.setEndY(event1.getY());
                }

            }
            Main.analyse(event1.getY(), event1.getX());
        });
    }
}
