package sample.logigraphics.windows;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import sample.logigraphics.charts.PieChart;
import sample.logigraphics.interfaces.LogicielStructure;

import java.util.concurrent.atomic.AtomicReference;

public class ChartWindow {

    SmallWindow smallWindow = new SmallWindow("Graphique");

    ChoiceBox<String> choiceBox = new ChoiceBox<>();

    public ChartWindow(LogicielStructure logicielStructure, PieChart pieChart){
        FlowPane flowPane1 = new FlowPane();
        TextField textField = new TextField();
        TextField textField1 = new TextField();

        textField.setPromptText("nom");
        textField.setMaxWidth(100);
        textField1.setMaxWidth(100);
        textField.setMinWidth(100);
        textField1.setMinWidth(100);
        textField1.setPromptText("valeur");

        Button button = smallWindow.getStyliziedButton("Ajouter");
        button.setOnAction(event -> {
            try {
                pieChart.addValue(textField.getText(), Double.parseDouble(textField1.getText()));
                choiceBox.getItems().add(textField.getText()+" - "+textField1.getText());
                choiceBox.getSelectionModel().select(0);
                textField.setText("");
                textField1.setText("");
            }catch (NumberFormatException e){}
        });

        flowPane1.getChildren().addAll(textField,textField1,button);
        flowPane1.setAlignment(Pos.CENTER);
        flowPane1.setHgap(5);

        Button button1 = smallWindow.getStyliziedButton("Créer");
        button1.setOnAction(event -> {
            if(choiceBox.getItems().size() > 0) {
                logicielStructure.setCanvas(pieChart.build());
                choiceBox = new ChoiceBox<>();
                smallWindow.close();
            }
        });

        smallWindow.add(flowPane1);
        smallWindow.add(choiceBox);
        smallWindow.add(button1);
        smallWindow.setSpacing(10);
    }

    public void show(){
        smallWindow.show();
    }




}
