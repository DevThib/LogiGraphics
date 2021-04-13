package sample;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class CrossCreator {

    public void createCross(double posX,double posY){

        Line horizontal = new Line();
        Line vertical = new Line();

        horizontal.setStartY(posY);
        horizontal.setStartX(posX-7);
        horizontal.setEndY(posY);
        horizontal.setEndX(posX+7);

        vertical.setStartY(posY-7);
        vertical.setStartX(posX);
        vertical.setEndY(posY+7);
        vertical.setEndX(posX);

        Shape shape = Shape.union(horizontal,vertical);
        shape.setId(posX+","+posY);

        Main.group.getChildren().addAll(shape);

    }

}
