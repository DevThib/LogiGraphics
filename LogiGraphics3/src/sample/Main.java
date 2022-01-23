package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.logigraphics.Logiciel;
import sample.logigraphics.creation.Creation;
import sample.logigraphics.interfaces.LogicielStructure;
import sample.logigraphics.keyboard.KeyShort;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){

       Logiciel logiciel = new Logiciel();
       logiciel.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
