package sample.logigraphics.windows;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.logigraphics.charts.Chart;
import sample.logigraphics.charts.PieChart;
import sample.logigraphics.interfaces.LogicielStructure;
import sample.logigraphics.stuff.Debug;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChartWindow {

    SmallWindow smallWindow;

    Border test = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DOTTED,new CornerRadii(0),new BorderWidths(1)));
    Border white = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,new CornerRadii(3),new BorderWidths(1)));
    Border invisible = new Border(new BorderStroke(Color.TRANSPARENT,BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1)));

    Font font = new Font("Trebuchet MS",15);

    Chart chart;

    public ChartWindow(LogicielStructure logicielStructure, Chart chatr){

        this.chart = chatr;

        smallWindow = new SmallWindow("Graphique",1,Debug.getIcon("chart",logicielStructure,1));

        FlowPane central = new FlowPane();
        central.setAlignment(Pos.CENTER);
        central.setHgap(100);
        central.setMinWidth(650);
        central.setMaxWidth(650);
        central.setMinHeight(290);
        central.setMaxHeight(290);

        FlowPane left = new FlowPane();
        left.setVgap(10);
        left.setAlignment(Pos.CENTER);
        left.setBorder(test);
        left.setOrientation(Orientation.VERTICAL);
        left.setColumnHalignment(HPos.CENTER);
        left.setMinWidth(225);
        left.setMaxWidth(225);
        left.setMinHeight(260);
        left.setMaxHeight(260);

        VBox right = new VBox();
        right.setSpacing(10);
        right.setMinWidth(225);
        right.setMaxWidth(225);
        right.setMinHeight(260);
        right.setMaxHeight(260);

        TextField name = new TextField();
        name.setPromptText("Nom de la valeur");
        name.setBorder(invisible);
        name.setStyle("-fx-base: #242424;" +
                "-fx-background-color: derive(-fx-base,20%);" +
                "-fx-prompt-text-fill: white;" +
                "-fx-text-fill:white;");
        name.setOnMouseEntered(event -> name.setBorder(white));
        name.setOnMouseExited(event -> name.setBorder(invisible));
        name.setFont(font);

        ListView<String> listView = new ListView<>();

        TextField value = new TextField();
        value.setBorder(invisible);
        value.setPromptText("Valeur");
        value.setStyle("-fx-base: #242424;" +
                "-fx-background-color: derive(-fx-base,20%);" +
                "-fx-prompt-text-fill: white;" +
                "-fx-text-fill:white;");
        value.setOnMouseEntered(event -> value.setBorder(white));
        value.setOnMouseExited(event -> value.setBorder(invisible));
        ChangeListener<String> numericTextFieldListener = (observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")) {
                value.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
        value.textProperty().addListener(numericTextFieldListener);
        value.setFont(font);
        value.setOnKeyPressed(event -> {
            if(event.getCode().getName().equalsIgnoreCase("Enter")){
                if(!name.getText().equalsIgnoreCase("") && !value.getText().equalsIgnoreCase("")) {
                    if (listView.getItems().size() == 1 && listView.getItems().get(0).equalsIgnoreCase("Aucune valeur entrée")) {
                        listView.getItems().remove(0);
                    }
                    chart.addValue(name.getText(), Double.parseDouble(value.getText()));
                    listView.getItems().add(name.getText() + " - " + value.getText());
                    name.setText("");
                    value.setText("");
                }
            }
        });

        listView.setBorder(test);
        listView.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY && !listView.getSelectionModel().getSelectedItem().equalsIgnoreCase("Aucune valeur entrée")){
                listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
            }
        });
        listView.setStyle(
                "  -fx-base: #242424 ;\n" +
                        "  -fx-control-inner-background: derive(-fx-base,20%);\n" +
                        "  -fx-control-inner-background-alt: derive(-fx-control-inner-background,-10%);\n" +
                        "  -fx-background: grey;\n" +
                        "  -fx-accent: #006689;\n" +
                        "  -fx-focus-color: #036E83;\n" +
                        "  -fx-faint-focus-color: #036E8322;" +
                        "  -fx-font-family: \"Trebuchet MS\";");
        listView.getItems().add("Aucune valeur entrée");

        Button confirm = smallWindow.getStyliziedButton("Ajouter",false);
        confirm.setOnAction(event -> {
            if(!name.getText().equalsIgnoreCase("") && !value.getText().equalsIgnoreCase("")) {
                if (listView.getItems().size() == 1 && listView.getItems().get(0).equalsIgnoreCase("Aucune valeur entrée")) {
                    listView.getItems().remove(0);
                }
                chart.addValue(name.getText(), Double.parseDouble(value.getText()));
                listView.getItems().add(name.getText() + " - " + value.getText());
                name.setText("");
                value.setText("");
            }
        });

        Button create = smallWindow.getStyliziedButton("Créer",false);
        create.setOnAction(event -> {
            if(chart.getValues().size() > 0) {
                logicielStructure.getCanvas().getGraphicsContext2D().clearRect(0,0,logicielStructure.getCanvas().getWidth(),logicielStructure.getCanvas().getHeight());
                logicielStructure.setCanvas(chart.build());
                listView.getItems().removeAll(listView.getItems());
                chart.init();
                smallWindow.close();
            }
        });

        left.getChildren().addAll(name,value,confirm);
        right.getChildren().addAll(listView);
        central.getChildren().addAll(left,right);

        smallWindow.add(central);
        smallWindow.add(create);
    }

    public void show(){
        smallWindow.show();
    }

    public void setChart(Chart chart){
        chart.init();
        this.chart = chart;
    }

}
