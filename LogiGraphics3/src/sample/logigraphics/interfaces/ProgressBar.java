package sample.logigraphics.interfaces;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sample.logigraphics.events.Event;

public class ProgressBar {

    Canvas canvas = new Canvas(600,50);

    FlowPane flowPane = new FlowPane();

    Event event;

    public ProgressBar(){
        flowPane.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(3))));
        flowPane.setAlignment(Pos.CENTER);
        flowPane.getChildren().add(canvas);
        canvas.getGraphicsContext2D().setFill(Color.GREEN);
    }

    public void setProgress(double percentage){
        double toDraw = percentage/100 * canvas.getWidth();
        canvas.getGraphicsContext2D().fillRect(0,0,toDraw,canvas.getHeight());
        if(percentage == 100){
            event.run();
        }
    }

    public FlowPane get(){
        return flowPane;
    }

    public void setOnFinished(Event event){
       this.event = event;
    }


}
