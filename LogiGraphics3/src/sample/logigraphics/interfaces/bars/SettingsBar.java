package sample.logigraphics.interfaces.bars;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import sample.logigraphics.interfaces.LogicielColors;
import sample.logigraphics.interfaces.LogicielStructure;

public class SettingsBar {

    LogicielStructure logicielStructure;

    Background grey = new Background(new BackgroundFill(LogicielColors.getTopBarColor(),new CornerRadii(0),new Insets(0)));

    Font trebuchet = new Font("Trebuchet MS",20);

    FlowPane flowPane = new FlowPane();

    public SettingsBar(LogicielStructure logicielStructure){
        this.logicielStructure = logicielStructure;
    }

    public FlowPane get(){

        flowPane.setMinHeight(30);
        flowPane.setMinWidth(logicielStructure.getScene().getWidth());
        flowPane.setMaxHeight(30);
        flowPane.setMaxWidth(logicielStructure.getScene().getWidth());
        flowPane.setBackground(grey);
        flowPane.setHgap(10);
        flowPane.setAlignment(Pos.CENTER_LEFT);

        Rectangle sepa = new Rectangle(0,0);
        sepa.setFill(Color.TRANSPARENT);

        Label save = getButton("💾", event -> logicielStructure.getLogiciel().save(true));
        Label create = getButton("📝", event -> logicielStructure.openNewPaper());
        Label charts = getButton("📊", event -> logicielStructure.getRightBar().getChartWindow().show());

        flowPane.getChildren().addAll(sepa,save,create,charts);

        return flowPane;
    }

    private Label getButton(String text,EventHandler<MouseEvent> event){
        Label label = new Label(text);
        label.setFont(trebuchet);
        label.setTextFill(Color.WHITE);
        label.setOnMouseClicked(event);
        label.setOnMouseEntered(event1 -> label.setTextFill(Color.GREY));
        label.setOnMouseExited(event1 -> label.setTextFill(Color.WHITE));

        return label;
    }

    public void adapt(double width){
        flowPane.setMinWidth(width);
    }


}
