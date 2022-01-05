package sample.logigraphics.creation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sample.logigraphics.Logiciel;

import java.util.ArrayList;

public class ShapeCreator {

    ShapeType actual = ShapeType.RECTANGLE;
    Logiciel logiciel;

    boolean creating = false;

    ArrayList<Shape> shapes = new ArrayList<>();

    Color selectedColor = Color.BLACK;

    Shape lastShape;
    Shape mirror;

    public ShapeCreator(Logiciel logiciel){
        this.logiciel = logiciel;
    }

    public ShapeType getShapeType() {
        return actual;
    }

    public void setShapeType(ShapeType shapeType) {
        if (!isCreating()) {
            this.actual = shapeType;
            stopCreating();
            logiciel.setShapeSelected(null);
            logiciel.setFree(false);
        }
    }

    public boolean isCreating(){
        return creating;
    }

    public void startCreating(double posX, double posY, Image image){
        this.creating = true;

        Shape shape;

        if(actual == ShapeType.IMAGE){
            shape = new Shape(actual, posX, posY, selectedColor,image);
        }else{
           shape = new Shape(actual, posX, posY, selectedColor,null);
        }

        if(logiciel.getCreation() == Creation.MIRROR) {

            Shape shape2;

            if(actual == ShapeType.IMAGE){
                shape2 = new Shape(actual, posX, posY, selectedColor,image);
            }else{
                shape2 = new Shape(actual, posX, posY, selectedColor,null);
            }

            double distance = Math.abs(logiciel.getMirrorAxe().getEndX() - posX);

            if(posX > logiciel.getMirrorAxe().getEndX()){
                shape2.setX(posX - distance*2);
            }else{
                shape2.setX(posX + distance*2);
            }

            shape.setOnClicked(event -> {
                if (logiciel.isFree()) {
                    logiciel.setShapeSelected(shape);
                    shape.select();
                } else {
                    if (shape.getShapeType() == ShapeType.NONFILLEDRECTANGLE) {
                        if (isCreating()) {
                            stopCreating();
                        } else {
                            logiciel.setShapeSelected(shape);
                        }
                    }
                }
            });
            shape2.setOnClicked(event -> {
                if (logiciel.isFree()) {
                    logiciel.setShapeSelected(shape2);
                    shape2.select();
                } else {
                    if (shape.getShapeType() == ShapeType.NONFILLEDRECTANGLE) {
                        if (isCreating()) {
                            stopCreating();
                        } else {
                            logiciel.setShapeSelected(shape2);
                        }
                    }
                }
            });

            logiciel.getScene().setOnMouseMoved(event -> {

                switch (actual) {

                    case RECTANGLE:
                        Rectangle r = (Rectangle) shape.get();
                        r.setWidth(event.getX() - r.getX());
                        r.setHeight(event.getY() - r.getY());

                        Rectangle r2 = (Rectangle) shape2.get();
                        r2.setWidth(r.getWidth());
                        r2.setHeight(r.getHeight());
                        break;

                    case CIRCLE:
                        Circle c = (Circle) shape.get();
                        c.setRadius((Math.abs(event.getX() - c.getCenterX()) + Math.abs(event.getY() - c.getCenterY())) / 2);

                        Circle c2 = (Circle) shape2.get();
                        c2.setRadius(((Math.abs(event.getX() - c.getCenterX()) + Math.abs(event.getY() - c.getCenterY())) / 2));
                        break;

                    case LINE:
                        Line l = (Line) shape.get();
                        l.setEndX(event.getX());
                        l.setEndY(event.getY());

                        Line l2 = (Line) shape2.get();

                        if(posX > logiciel.getMirrorAxe().getEndX()){
                            l2.setEndX(event.getX() - distance *2);
                        }else{
                            l2.setEndX(event.getX() + distance*2);
                        }
                        l2.setEndY(event.getY());

                        break;

                    case NONFILLEDRECTANGLE:
                        Button b = (Button) shape.get();
                        b.setMinWidth(event.getX() - b.getTranslateX());
                        b.setMaxWidth(event.getX() - b.getTranslateX());
                        b.setMinHeight(event.getY() - b.getTranslateY());
                        b.setMaxHeight(event.getY() - b.getTranslateY());

                        Button b2 = (Button) shape2.get();
                        b2.setMinWidth(event.getX() - b.getTranslateX());
                        b2.setMaxWidth(event.getX() - b.getTranslateX());
                        b2.setMinHeight(event.getY() - b.getTranslateY());
                        b2.setMaxHeight(event.getY() - b.getTranslateY());
                        break;

                    case ELLIPSE:
                        Ellipse ellipse = (Ellipse) shape.get();
                        ellipse.setRadiusX(Math.abs(event.getX() - ellipse.getCenterX()));
                        ellipse.setRadiusY(Math.abs(event.getY() - ellipse.getCenterY()));

                        Ellipse ellipse2 = (Ellipse) shape2.get();
                        ellipse2.setRadiusX(Math.abs(event.getX() - ellipse.getCenterX()));
                        ellipse2.setRadiusY(Math.abs(event.getY() - ellipse.getCenterY()));
                        break;

                    case IMAGE:
                        ImageView imageView = (ImageView) shape.get();
                        imageView.setFitWidth(event.getX() - imageView.getX());
                        imageView.setFitHeight(event.getY() - imageView.getY());

                        ImageView imageView2 = (ImageView) shape2.get();
                        imageView2.setFitWidth(event.getX() - imageView.getX());
                        imageView2.setFitHeight(event.getY() - imageView.getY());
                        break;

                }


            });

            lastShape = shape;
            mirror = shape2;

            shapes.add(shape);
            shapes.add(shape2);

        }else{

            shape.setOnClicked(event -> {
                if (logiciel.isFree()) {
                    logiciel.setShapeSelected(shape);
                    shape.select();
                } else {
                    if (shape.getShapeType() == ShapeType.NONFILLEDRECTANGLE) {
                        if (isCreating()) {
                            stopCreating();
                        } else {
                            logiciel.setShapeSelected(shape);
                        }
                    }
                }
            });

            logiciel.getScene().setOnMouseMoved(event -> {

                switch (actual) {

                    case RECTANGLE:
                        Rectangle r = (Rectangle) shape.get();
                        r.setWidth(event.getX() - r.getX());
                        r.setHeight(event.getY() - r.getY());
                        break;

                    case CIRCLE:
                        Circle c = (Circle) shape.get();
                        c.setRadius((Math.abs(event.getX() - c.getCenterX()) + Math.abs(event.getY() - c.getCenterY())) / 2);
                        break;

                    case LINE:
                        Line l = (Line) shape.get();
                        l.setEndX(event.getX());
                        l.setEndY(event.getY());
                        break;

                    case NONFILLEDRECTANGLE:
                        Button b = (Button) shape.get();
                        b.setMinWidth(event.getX() - b.getTranslateX());
                        b.setMaxWidth(event.getX() - b.getTranslateX());
                        b.setMinHeight(event.getY() - b.getTranslateY());
                        b.setMaxHeight(event.getY() - b.getTranslateY());
                        break;

                    case ELLIPSE:
                        Ellipse ellipse = (Ellipse) shape.get();
                        ellipse.setRadiusX(Math.abs(event.getX() - ellipse.getCenterX()));
                        ellipse.setRadiusY(Math.abs(event.getY() - ellipse.getCenterY()));
                        break;

                    case IMAGE:
                        ImageView imageView = (ImageView) shape.get();
                        imageView.setFitWidth(event.getX() - imageView.getX());
                        imageView.setFitHeight(event.getY() - imageView.getY());
                        break;

                }


            });
            lastShape = shape;
            shapes.add(shape);
        }

        updateToScreen();
    }

    public void stopCreating(){
        this.creating = false;

        Border filledBorder = new Border(new BorderStroke(selectedColor,BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(5)));

        if(lastShape != null) {

            switch (lastShape.getShapeType()) {

                case NONFILLEDRECTANGLE:
                    Button button = (Button) lastShape.get();
                    button.setBorder(filledBorder);

                    if(mirror != null){
                        Button button2 = (Button) mirror.get();
                        button2.setBorder(filledBorder);
                    }

                    break;

                default:
                    lastShape.setOpacity(1);
                    if(mirror != null)mirror.setOpacity(1);
                    break;
            }


        }
        logiciel.getScene().setOnMouseMoved(event -> {});
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void removeShape(Shape shape){
        this.shapes.remove(shape);
        updateToScreen();
    }

    public void removeShape(int index){
        this.shapes.remove(index);
        updateToScreen();
    }

    public void changeSelectedColor(Color color){
        this.selectedColor = color;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public Shape getLastShape() {
        return lastShape;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        logiciel.getTopBar().getIndicator().setBackground(new Background(new BackgroundFill(selectedColor,new CornerRadii(0),new Insets(0))));
        //a opti mieux plutot que d'en recreer un a chaque fois on a une liste et on pioche
    }

    private void updateToScreen(){

        for (Shape shape : shapes) {
            if (!logiciel.getGroup().getChildren().contains(shape.get())) {
                logiciel.addElement(shape.get());
            }
        }
    }

    public void setLastShape(Shape lastShape) {
        this.lastShape = lastShape;
    }

    public Shape getMirror() {
        return mirror;
    }
}
