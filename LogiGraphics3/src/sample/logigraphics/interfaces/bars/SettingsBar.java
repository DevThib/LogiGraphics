package sample.logigraphics.interfaces.bars;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import sample.logigraphics.interfaces.LogicielColors;
import sample.logigraphics.interfaces.LogicielStructure;

public class SettingsBar {

    LogicielStructure logicielStructure;

    Background grey = new Background(new BackgroundFill(LogicielColors.getTopBarColor(),new CornerRadii(0),new Insets(0)));

    public SettingsBar(LogicielStructure logicielStructure){
        this.logicielStructure = logicielStructure;
    }

    public FlowPane get(){

        FlowPane flowPane = new FlowPane();
        flowPane.setMinHeight(30);
        flowPane.setMinWidth(logicielStructure.getScene().getWidth());
        flowPane.setMaxHeight(30);
        flowPane.setMaxWidth(logicielStructure.getScene().getWidth());
        flowPane.setBackground(grey);
        flowPane.setHgap(5);
        flowPane.setAlignment(Pos.CENTER_LEFT);

        ImageView settings = getButton("https://image.flaticon.com/icons/png/512/93/93643.png", event -> {

        });

        ImageView save = getButton("https://image.flaticon.com/icons/png/512/93/93643.png", event -> logicielStructure.getLogiciel().save(true));


        flowPane.getChildren().addAll(settings,save);

        return flowPane;
    }

    private ImageView getButton(String url, EventHandler<MouseEvent> event){
        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView.setOnMouseClicked(event);
        imageView.setOnMouseEntered(event1 -> imageView.setOpacity(0.5));
        imageView.setOnMouseExited(event1 -> imageView.setOpacity(1));
        return imageView;
    }




}
