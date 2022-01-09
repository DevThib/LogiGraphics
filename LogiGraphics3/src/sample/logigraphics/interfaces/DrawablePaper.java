package sample.logigraphics.interfaces;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import sample.logigraphics.Logiciel;

public class DrawablePaper {

    VBox paper = new VBox();
    FlowPane flow = new FlowPane();
    Rectangle surface = new Rectangle(0,0,1400,550);

    Border border = new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.DASHED,new CornerRadii(0),new BorderWidths(5)));
    Background transparent = new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0)));

    Button button = new Button("▶");
    Button button2 = new Button("🔽");

    Logiciel logiciel;

    public DrawablePaper(Logiciel logiciel){
        this.logiciel = logiciel;
    }

    public VBox build(){

        flow.setMinWidth(1500);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3);
        ds.setOffsetX(3);
        ds.setRadius(2);

        surface.setFill(Color.WHITE);
        surface.setEffect(ds);
        surface.setTranslateY(5);
        surface.setTranslateX(5);

        flow.getChildren().addAll(surface,getSeparator());

        paper.getChildren().addAll(flow,getSeparator());
        paper.setMinWidth(flow.getMinWidth());
        paper.setTranslateY(130);

        addButtons();

        return paper;
    }

   /* private Circle getButton(boolean horizontal){

        Circle circle = new Circle(10);

        if(horizontal)circle.setCenterX(495);

        circle.setOnMouseDragged(event -> {
            double calcMore = event.getX() - circle.getCenterX();
            double calcLess = circle.getCenterX() - event.getX();

            if(!horizontal) {
                if (event.getX() > circle.getCenterX()) {
                    flow.setMinWidth(flow.getMinWidth() + calcMore);
                    surface.setWidth(surface.getWidth() + calcMore);
                    circle.setCenterX(circle.getCenterX() + calcMore);
                } else if (event.getX() < circle.getCenterX()) {
                    flow.setMinWidth(flow.getMinWidth() - calcLess);
                    surface.setWidth(surface.getWidth() - calcLess);
                    circle.setCenterX(circle.getCenterX() - calcLess);
                }
            }else{
                if (event.getY() > circle.getCenterY()) {
                    surface.setHeight(surface.getHeight() + calcMore);
                    circle.setCenterY(circle.getCenterY() + calcMore);
                } else if (event.getY() < circle.getCenterY()) {
                    surface.setHeight(surface.getHeight() - calcLess);
                    circle.setCenterY(circle.getCenterY() - calcLess);
                }
            }

        });
        paper.setMinWidth(flow.getMinWidth());

        return circle;
    }

    */

    private void addButtons(){
        button.setBorder(border);
        button.setBackground(transparent);
        button.setMinHeight(surface.getHeight()-10);
        button.setTextFill(Color.BLACK);
        button.setOnAction(event -> {
            flow.setMinWidth(flow.getMinWidth()+30);
            surface.setWidth(surface.getWidth()+30);
            button2.setMinWidth(surface.getWidth());
        });

        button2.setBorder(border);
        button2.setBackground(transparent);
        button2.setMinWidth(surface.getWidth());
        button2.setTextFill(Color.BLACK);
        button2.setOnAction(event -> {
            surface.setHeight(surface.getHeight()+30);
            button.setMinHeight(surface.getHeight()-10);
            logiciel.getMirrorAxe().setEndY(surface.getHeight());
        });

        flow.getChildren().add(button);
        paper.getChildren().add(button2);
    }

    private Rectangle getSeparator(){
        Rectangle sepa = new Rectangle();
        sepa.setWidth(10);
        sepa.setHeight(10);
        sepa.setFill(Color.TRANSPARENT);
        return sepa;
    }

    public Rectangle getSurface() {
        return surface;
    }
}
