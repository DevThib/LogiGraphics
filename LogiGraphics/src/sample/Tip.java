package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class Tip {

    Stage stage = new Stage();
    VBox box = new VBox();
    Label label = new Label();

    public void showTip(){
        label.setText(randomTip());
        label.setFont(new Font("Trebuchet MS", 15));
        box.getChildren().addAll(label);
        box.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(box, 500, 225));
        stage.setTitle("Astuce du jour !");
        stage.show();
    }

    public String randomTip(){
        Random r = new Random();
        int nb = r.nextInt(2);

        switch (nb){

            case 0:
                return "Vous pouvez changer de sélection en appuyant sur p !";

            case 1:
                return "Vous pouvez déplacer les éléments avec z q s et d !";


        }
        return "null";
    }

}
