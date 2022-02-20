package sample.logigraphics.interfaces;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class Grid {

    double numberOfLines;

    Canvas canvas = new Canvas();

    boolean visible = true;

    public Grid(){
        canvas.setCursor(Cursor.CROSSHAIR);
    }

    public Canvas getGrid() {
        return canvas;
    }

    public void setNumberOfLines(double numberOfLines){
        this.numberOfLines = numberOfLines;

        canvas.getGraphicsContext2D().clearRect(0,0,canvas.getWidth(),canvas.getHeight());

        double distanceWidth = canvas.getWidth()/(numberOfLines+1);
        double distanceHeight = canvas.getHeight()/(numberOfLines+1);

        for(int i = 1; i <= numberOfLines; i++){
            canvas.getGraphicsContext2D().fillRect(distanceWidth*i,0,3,canvas.getHeight());
        }
        for(int i = 1; i <= numberOfLines; i++){
            canvas.getGraphicsContext2D().fillRect(0,distanceHeight*i,canvas.getWidth(),3);
        }

    }

    public double getNumberOfLines() {
        return numberOfLines;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        canvas.setVisible(visible);
    }

    public void setCanvasSize(double width,double height){
        canvas.setHeight(height);
        canvas.setWidth(width);
    }

    public void setOnMouseClicked(EventHandler<MouseEvent> event){
        canvas.setOnMouseClicked(event);
    }

    public void setOnMouseMoved(EventHandler<MouseEvent> event){
        canvas.setOnMouseMoved(event);
    }

    public void setOnMousePressed(EventHandler<MouseEvent> event){
        canvas.setOnMousePressed(event);
    }

    public void setOnMouseReleased(EventHandler<MouseEvent> event){
        canvas.setOnMouseReleased(event);
    }

    public void setOnMouseDragged(EventHandler<MouseEvent> event){
        canvas.setOnMouseDragged(event);
    }

}
