package sample.logigraphics.creation;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Shape {

    boolean selected;

    double x;
    double y;

    int rotation = 0;

    ShapeType shapeType;

    Node object;

    Color color;

    double opacity = 0.5;

    double scale = 1;

    public Shape(ShapeType shapeType,double x,double y,Color color,Image image){
        this.shapeType = shapeType;
        this.x = x;
        this.y = y;
        this.color = color;

        Border nonFilledBorder = new Border(new BorderStroke(color,BorderStrokeStyle.DASHED,new CornerRadii(0),new BorderWidths(5)));

        switch (shapeType){

            case RECTANGLE:
                Rectangle r = new Rectangle(x,y,1,1);
                r.setFill(color);
                this.object = r;
                break;

            case CIRCLE:
                this.object = new Circle(x,y,1,color);
                break;

            case LINE:
                this.object = new Line(x,y,x,y);
                break;

            case NONFILLEDRECTANGLE:
                Button button = new Button();
                button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
                button.setBorder(nonFilledBorder);
                button.setTranslateX(x);
                button.setTranslateY(y);

                this.object = button;
                break;

            case ELLIPSE:
                Ellipse ellipse = new Ellipse(x,y,1,1);
                ellipse.setFill(color);
                this.object = ellipse;
                break;

            case IMAGE:
                ImageView imageView = new ImageView(image);
                imageView.setX(x);
                imageView.setY(y);
                imageView.setFitHeight(0);
                imageView.setFitWidth(0);
                this.object = imageView;
                break;

            case TRIANGLE:
                Polygon polygon = new Polygon();
                polygon.setFill(color);
                polygon.getPoints().addAll(x,y,x+10,y+20,x+20,y);
                this.object = polygon;
                break;

        }

        if(object != null)object.setOpacity(opacity);

        final double[] differenceX = new double[1];
        final double[] differenceY = new double[1];
        final boolean[] set = {false};

        if(object != null)object.setOnMouseDragged(event -> {
            if(!set[0]){
                set[0] = true;
                differenceX[0] = event.getX()-x;
                differenceY[0] = event.getY()-y;
            }

            setX(event.getX() - differenceX[0]);
            setY(event.getY() - differenceY[0]);
        });
    }

    public void setOnClicked(EventHandler<MouseEvent> event){
        object.setOnMouseClicked(event);
    }

    public void select(){
        this.selected = true;
        object.setOpacity(0.7);
    }

    public void unSelect(){
        this.selected = false;
        object.setOpacity(1);
    }

    public Node get(){
        return this.object;
    }

    public Color getColor() {
        return color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;

        switch (shapeType) {

            case RECTANGLE:
                Rectangle r = (Rectangle) object;
                r.setX(x);
                this.object = r;
                break;

            case CIRCLE:
                Circle c = (Circle) object;
                c.setCenterX(x);
                this.object = c;
                break;

            case LINE:
                Line l = (Line) object;
                l.setStartX(x);
                this.object = l;
                break;

            case NONFILLEDRECTANGLE:

                Button b = (Button) object;
                b.setTranslateX(x);
                this.object = b;
                break;

            case ELLIPSE:

                Ellipse e = (Ellipse) object;
                e.setCenterX(x);
                this.object = e;
                break;

            case IMAGE:

                ImageView image = (ImageView) object;
                image.setX(x);
                this.object = image;
                break;
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;

        switch (shapeType) {

            case RECTANGLE:
                Rectangle r = (Rectangle) object;
                r.setY(y);
                this.object = r;
                break;

            case CIRCLE:
                Circle c = (Circle) object;
                c.setCenterY(y);
                this.object = c;
                break;

            case LINE:
                Line l = (Line) object;
                l.setStartY(y);
                this.object = l;
                break;

            case NONFILLEDRECTANGLE:

                Button b = (Button) object;
                b.setTranslateY(y);
                this.object = b;
                break;

            case ELLIPSE:

                Ellipse e = (Ellipse) object;
                e.setCenterY(y);
                this.object = e;
                break;

            case IMAGE:

                ImageView imageView = (ImageView) object;
                imageView.setY(y);
                this.object = imageView;
                break;
        }

    }

    public boolean isSelected() {
        return selected;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
        object.setOpacity(opacity);
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        object.setRotate(rotation);
    }

    public void changeColor(Color color) {
        switch (shapeType) {

            case RECTANGLE:
                Rectangle r = (Rectangle) object;
                r.setFill(color);
                this.object = r;
                break;

            case CIRCLE:
                Circle c = (Circle) object;
                c.setFill(color);
                this.object = c;
                break;

        }
    }

    public void setScale(double scale) {
        this.scale = scale;
        object.setScaleX(scale);
        object.setScaleY(1);
    }

    public double getScale() {
        return scale;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void set(Node node){
        this.object = node;
    }
}
