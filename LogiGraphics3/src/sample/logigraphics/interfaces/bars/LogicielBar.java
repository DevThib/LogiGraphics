package sample.logigraphics.interfaces.bars;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import sample.logigraphics.Logiciel;
import sample.logigraphics.interfaces.LogicielColors;
import sample.logigraphics.interfaces.LogicielStructure;

import java.io.File;

public class LogicielBar {

    LogicielStructure logicielStructure;

    Background grey = new Background(new BackgroundFill(LogicielColors.getTopBarColor(),new CornerRadii(0),new Insets(0)));
    Background red = new Background(new BackgroundFill(Color.RED,new CornerRadii(0),new Insets(0)));
    Background lessRed = new Background(new BackgroundFill(Color.rgb(255,71,71),new CornerRadii(0),new Insets(0)));
    Background lessGrey = new Background(new BackgroundFill(Color.rgb(117,117,117),new CornerRadii(0),new Insets(0)));

    Font font = new Font("Seoge MDL2 Assets",15);

    File icon = new File("");

    Label title = new Label("Nouveau projet");

    public LogicielBar(LogicielStructure logicielStructure){
        this.logicielStructure = logicielStructure;
    }

    public BorderPane get(){

        BorderPane borderPane = new BorderPane();
        borderPane.setMinWidth(logicielStructure.getScene().getWidth());
        borderPane.setMaxWidth(logicielStructure.getScene().getWidth());
        borderPane.setMinHeight(40);
        borderPane.setMaxHeight(40);
        borderPane.setBackground(grey);

        FlowPane flowPane1 = new FlowPane();
        FlowPane flowPane2 = new FlowPane();

        Button cross = new Button("\uE106");
        cross.setMinWidth(50);
        cross.setMinHeight(30);
        cross.setMaxHeight(30);
        cross.setMaxWidth(50);
        cross.setBackground(red);
        cross.setTextFill(Color.WHITE);
        cross.setOnMouseEntered(event -> {
            cross.setBackground(lessRed);
            cross.setTextFill(Color.GREY);
        });
        cross.setOnMouseExited(event -> {
            cross.setBackground(red);
            cross.setTextFill(Color.WHITE);
        });
        cross.setOnAction(event -> logicielStructure.getStage().close());
        cross.setFont(font);

        Button extend = new Button("🔳");
        extend.setMinWidth(50);
        extend.setMinHeight(30);
        extend.setMaxHeight(30);
        extend.setMaxWidth(50);
        extend.setBackground(grey);
        extend.setTextFill(Color.WHITE);
        extend.setOnMouseEntered(event -> extend.setBackground(lessGrey));
        extend.setOnMouseExited(event -> extend.setBackground(grey));
        extend.setOnAction(event -> {
            if(logicielStructure.getStage().isFullScreen()){
                extend.setText("🔳");
                logicielStructure.getStage().setFullScreen(false);
            }else{
                extend.setText("↙");
                logicielStructure.getStage().setFullScreen(true);
            }
            borderPane.setMinWidth(logicielStructure.getScene().getWidth());
        });
        extend.setFont(font);

        Rectangle imageView = new Rectangle();
        imageView.setWidth(20);
        imageView.setHeight(20);

        title.setFont(font);
        title.setTextFill(Color.WHITE);

        flowPane1.getChildren().addAll(imageView,title);
        flowPane1.setOrientation(Orientation.HORIZONTAL);
        flowPane1.setHgap(5);
        flowPane1.setPadding(new Insets(10,0,10,5));

        flowPane2.getChildren().addAll(extend,cross);
        flowPane2.setOrientation(Orientation.HORIZONTAL);
        flowPane2.setMaxWidth(110);
        flowPane2.setPadding(new Insets(5));

     /*   padded.setOnMouseDragged(event -> {

            if(dragger == null)dragger = new Dragger(event.getX(),event.getY(),padded);

            dragger.setOnXInferior(() -> stage.setX(stage.getX()+(dragger.getX()-event.getX())));
            dragger.setOnXSuperior(() -> stage.setX(stage.getX()-(event.getX()-dragger.getX())));
            dragger.setOnYInferior(() -> stage.setY(stage.getY()+(dragger.getY()-event.getY())));
            dragger.setOnYSuperior(() -> stage.setY(stage.getY()-(event.getY()-dragger.getY())));

        });

      */

        borderPane.setLeft(flowPane1);
        borderPane.setRight(flowPane2);

        return borderPane;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public String getTitle(){
        return title.getText();
    }

}
