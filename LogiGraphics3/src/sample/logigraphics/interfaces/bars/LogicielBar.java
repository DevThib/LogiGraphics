package sample.logigraphics.interfaces.bars;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.logigraphics.interfaces.LogicielColors;
import sample.logigraphics.interfaces.LogicielStructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LogicielBar {

    LogicielStructure logicielStructure;

    Background grey = new Background(new BackgroundFill(LogicielColors.getTopBarColor(),new CornerRadii(0),new Insets(0)));
    Background red = new Background(new BackgroundFill(Color.RED,new CornerRadii(0),new Insets(0)));
    Background lessRed = new Background(new BackgroundFill(Color.rgb(255,71,71),new CornerRadii(0),new Insets(0)));
    Background lessGrey = new Background(new BackgroundFill(Color.rgb(117,117,117),new CornerRadii(0),new Insets(0)));

    Font font = new Font("Trebuchet MS",15);

    Label title = new Label("Nouveau projet");

    Button extend = new Button("🔳");

    BorderPane borderPane = new BorderPane();

    public LogicielBar(LogicielStructure logicielStructure){
        this.logicielStructure = logicielStructure;
    }

    public BorderPane get(){

        borderPane.setMinWidth(logicielStructure.getScene().getWidth());
        borderPane.setMaxWidth(logicielStructure.getScene().getWidth());
        borderPane.setMinHeight(40);
        borderPane.setMaxHeight(40);
        borderPane.setBackground(grey);

        final double[] initialX = {0};
        final double[] initialY = {0};

        borderPane.setOnMousePressed(event -> {
            initialX[0] = event.getX();
            initialY[0] = event.getY();
        });

        borderPane.setOnMouseDragged(event -> {
            logicielStructure.getStage().setX(event.getScreenX()-initialX[0]);
            logicielStructure.getStage().setY(event.getScreenY()-initialY[0]);
            if(logicielStructure.getStage().isMaximized() && event.getScreenY() > 0) unExtend();
        });
        borderPane.setOnMouseReleased(event -> {if(event.getScreenY() == 0) extend();});

        FlowPane flowPane1 = new FlowPane();
        FlowPane flowPane2 = new FlowPane();

        Button cross = new Button("❌");
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

        extend.setMinWidth(50);
        extend.setMinHeight(30);
        extend.setMaxHeight(30);
        extend.setMaxWidth(50);
        extend.setBackground(grey);
        extend.setTextFill(Color.WHITE);
        extend.setOnMouseEntered(event -> extend.setBackground(lessGrey));
        extend.setOnMouseExited(event -> extend.setBackground(grey));
        extend.setOnAction(event -> {if(logicielStructure.getStage().isMaximized()) unExtend(); else extend();});
        extend.setFont(font);

        ImageView imageView = null;
        try {
            imageView = new ImageView(new Image(new FileInputStream(logicielStructure.getLogiciel().getDataBase().getDirectoryByName("cache").getFileByName("icon.png"))));
        } catch (FileNotFoundException e) {e.printStackTrace();}
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

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

    private void extend(){
        extend.setText("↙");
        logicielStructure.getStage().setX(0);
        logicielStructure.getStage().setY(0);
        logicielStructure.getStage().setMaximized(true);
        borderPane.setMinWidth(logicielStructure.getScene().getWidth());
        borderPane.setMaxWidth(logicielStructure.getScene().getWidth());
        logicielStructure.getSettingsBar().adapt(logicielStructure.getScene().getWidth());
    }

    private void unExtend(){
        extend.setText("🔳");
        logicielStructure.getStage().setMaximized(false);
        borderPane.setMinWidth(logicielStructure.getScene().getWidth());
        borderPane.setMaxWidth(logicielStructure.getScene().getWidth());
        logicielStructure.getSettingsBar().adapt(logicielStructure.getScene().getWidth());
    }

}
