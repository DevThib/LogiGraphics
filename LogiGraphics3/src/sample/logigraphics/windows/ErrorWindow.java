package sample.logigraphics.windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ErrorWindow {

    String content;

    Label label;

    public void show(){

        Stage stage = new Stage();
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox,400,200);

        label.setText(content);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label);

        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.show();
    }

    public void setContent(String content) {
        this.content = content;
        label.setText(content);
    }
}
