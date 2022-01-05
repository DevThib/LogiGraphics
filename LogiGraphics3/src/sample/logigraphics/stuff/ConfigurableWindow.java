package sample.logigraphics.stuff;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfigurableWindow {

    Stage stage = new Stage();
    VBox box = new VBox(5);
    Scene scene;

    double size;
    String title;

    public ConfigurableWindow(double size,String title){
        this.size = size;
        this.title = title;

        scene = new Scene(box,600*size,300*size);

        box.setAlignment(Pos.CENTER);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
    }

    public void show(){
        stage.show();
    }

    public void close(){
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public VBox getBox() {
        return box;
    }

    public Scene getScene() {
        return scene;
    }

    public double getSize() {
        return size;
    }
}
