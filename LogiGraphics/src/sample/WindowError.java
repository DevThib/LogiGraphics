package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WindowError {

    Stage stage = new Stage();
    VBox box = new VBox();
    Scene scene = new Scene(box,700,350);

    public void showError(){

        box.setAlignment(Pos.CENTER);

        Label label = new Label("Une erreur s'est produite,Veuillez relancer LogiGraphics");
        label.setFont(new Font("Trbuchet MS",15));

        if(!box.getChildren().contains(label)){
            box.getChildren().addAll(label);
        }

        stage.setScene(scene);
        stage.show();

    }

    public void showCustomError(String text){
        box.setAlignment(Pos.CENTER);
        Label label = new Label(text);

        label.setFont(new Font("Trbuchet MS",15));

        if(!box.getChildren().contains(label)){
            box.getChildren().addAll(label);
        }

        stage.setScene(scene);
        stage.show();
    }

}
