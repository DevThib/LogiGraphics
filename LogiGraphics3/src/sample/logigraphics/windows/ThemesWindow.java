package sample.logigraphics.windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.logigraphics.Logiciel;
import sample.logigraphics.themes.Theme;
import fr.devthib.windowsapi.Notifications;

import java.awt.*;
import java.util.ArrayList;

public class ThemesWindow {

    Stage stage = new Stage();

    Group group = new Group();
    VBox vBox = new VBox();

    Group group2 = new Group();
    VBox vBox2 = new VBox();

    Scene scene1 = new Scene(group,600,300);
    Scene scene2 = new Scene(group2,600,300);

    Background grey = new Background(new BackgroundFill(Color.rgb(200,200,200),new CornerRadii(0),new Insets(0)));
    Background lessGrey = new Background(new BackgroundFill(Color.rgb(160,160,160),new CornerRadii(0),new Insets(0)));

    Font font = new Font("Trebuchet MS",15);

    Logiciel logiciel;

    public void open(Logiciel logiciel){

        this.logiciel = logiciel;

        setScene1();
        setScene2();

        stage.setScene(scene1);
        stage.setTitle("Ouvrir un thème custom");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.show();
    }

    private FlowPane getBar(){

        FlowPane bar = new FlowPane();
        bar.setMinHeight(45);
        bar.setMaxHeight(45);
        bar.setMinWidth(600);
        bar.setMaxWidth(600);
        bar.setAlignment(Pos.TOP_CENTER);
        bar.setBackground(lessGrey);

        Button create = new Button("Créer");
        create.setTranslateY(5);
        create.setMinWidth(120);
        create.setMaxWidth(120);
        create.setMinHeight(40);
        create.setMaxHeight(40);
        create.setTextFill(Color.WHITE);
        create.setBackground(grey);
        create.setFont(font);
        create.setOnMouseEntered(event -> create.setTextFill(Color.GREY));
        create.setOnMouseExited(event -> create.setTextFill(Color.BLACK));
        create.setOnAction(event -> {
            stage.setScene(scene2);
            stage.setTitle("Créer un thème custom");
        });

        Button open = new Button("Ouvrir");
        open.setTranslateY(5);
        open.setMinWidth(120);
        open.setMaxWidth(120);
        open.setMinHeight(40);
        open.setMaxHeight(40);
        open.setTextFill(Color.WHITE);
        open.setBackground(grey);
        open.setFont(font);
        open.setOnMouseEntered(event -> open.setTextFill(Color.GREY));
        open.setOnMouseExited(event -> open.setTextFill(Color.BLACK));
        open.setOnAction(event -> {
            stage.setScene(scene1);
            stage.setTitle("Ouvrir un thème custom");
        });

        Rectangle rectangle = new Rectangle(100,40,Color.TRANSPARENT);

        bar.getChildren().addAll(open,rectangle,create);

        return bar;
    }

    private Rectangle getSeparator(){
        return new Rectangle(10,10,Color.TRANSPARENT);
    }

    private void setScene1(){

        ComboBox<String> comboBox = new ComboBox<>();
        ArrayList<Theme> themes = new ArrayList<>();

        for(int i = 0; i < logiciel.getTheme().getCustomThemes().size(); i++){
            comboBox.getItems().add("Thème "+i);
            themes.add(logiciel.getTheme().getCustomThemes().get(i));
        }

        if(logiciel.getTheme().getCustomThemes().size() != 0)comboBox.getSelectionModel().select(0);

        Button select = new Button("Sélectionner");
        select.setFont(font);
        select.setOnAction(event -> {
            if(logiciel.getTheme().getCustomThemes().size() != 0) {
                logiciel.getTheme().setCustomColors(themes.get(Integer.parseInt(comboBox.getValue().split(" ")[1])).getColor1(),themes.get(Integer.parseInt(comboBox.getValue().split(" ")[1])).getColor2());
                logiciel.changeThemeInDatabase("custom/"+Integer.parseInt(comboBox.getValue().split(" ")[1]));
                logiciel.getTheme().apply();
                stage.close();
            }
        });

        scene1.setFill(Color.rgb(200,200,200));

        vBox.getChildren().addAll(comboBox,select);
        vBox.setAlignment(Pos.CENTER);
        vBox.setTranslateX(250);
        vBox.setTranslateY(100);

        group.getChildren().addAll(getBar(),vBox);
    }

    private void setScene2(){

        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,255));
        spinner.setEditable(true);
        spinner.getEditor().setOnKeyTyped(event -> {
            try {
                if (Integer.parseInt(spinner.getEditor().getText()) < 0) {
                    spinner.getEditor().setText("0");
                } else if (Integer.parseInt(spinner.getEditor().getText()) > 255) {
                    spinner.getEditor().setText("255");
                }
            }catch (NumberFormatException e){
                spinner.getEditor().setText("0");
            }
        });
        Spinner<Integer> spinner1 = new Spinner<>();
        spinner1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,255));
        spinner1.setEditable(true);
        spinner1.getEditor().setOnKeyTyped(event -> {
            try {
                if (Integer.parseInt(spinner1.getEditor().getText()) < 0) {
                    spinner1.getEditor().setText("0");
                } else if (Integer.parseInt(spinner1.getEditor().getText()) > 255) {
                    spinner1.getEditor().setText("255");
                }
            }catch (NumberFormatException e){
                spinner1.getEditor().setText("0");
            }
        });
        Spinner<Integer> spinner2 = new Spinner<>();
        spinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,255));
        spinner2.setEditable(true);
        spinner2.getEditor().setOnKeyTyped(event -> {
            try {
                if (Integer.parseInt(spinner2.getEditor().getText()) < 0) {
                    spinner2.getEditor().setText("0");
                } else if (Integer.parseInt(spinner2.getEditor().getText()) > 255) {
                    spinner2.getEditor().setText("255");
                }
            }catch (NumberFormatException e){
                spinner2.getEditor().setText("0");
            }
        });

        Spinner<Integer> spinner3 = new Spinner<>();
        spinner3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,255));
        spinner3.setEditable(true);
        spinner3.getEditor().setOnKeyTyped(event -> {
            try {
                if (Integer.parseInt(spinner3.getEditor().getText()) < 0) {
                    spinner3.getEditor().setText("0");
                } else if (Integer.parseInt(spinner3.getEditor().getText()) > 255) {
                    spinner3.getEditor().setText("255");
                }
            }catch (NumberFormatException e){
                spinner3.getEditor().setText("0");
            }
        });
        Spinner<Integer> spinner4 = new Spinner<>();
        spinner4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,255));
        spinner4.setEditable(true);
        spinner4.getEditor().setOnKeyTyped(event -> {
            try {
                if (Integer.parseInt(spinner4.getEditor().getText()) < 0) {
                    spinner4.getEditor().setText("0");
                } else if (Integer.parseInt(spinner4.getEditor().getText()) > 255) {
                    spinner4.getEditor().setText("255");
                }
            }catch (NumberFormatException e){
                spinner4.getEditor().setText("0");
            }
        });
        Spinner<Integer> spinner5 = new Spinner<>();
        spinner5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,255));
        spinner5.setEditable(true);
        spinner5.getEditor().setOnKeyTyped(event -> {
            try {
                if (Integer.parseInt(spinner5.getEditor().getText()) < 0) {
                    spinner5.getEditor().setText("0");
                } else if (Integer.parseInt(spinner5.getEditor().getText()) > 255) {
                    spinner5.getEditor().setText("255");
                }
            }catch (NumberFormatException e){
                spinner5.getEditor().setText("0");
            }
        });

        Button cre = new Button("Créer");
        cre.setOnAction(event -> {
            logiciel.getTheme().createCustomTheme(Integer.parseInt(spinner.getEditor().getText()), Integer.parseInt(spinner1.getEditor().getText()),Integer.parseInt(spinner2.getEditor().getText()),Integer.parseInt(spinner3.getEditor().getText()),Integer.parseInt(spinner4.getEditor().getText()),Integer.parseInt(spinner5.getEditor().getText()));
            try {Notifications.sendInfoNotification("", "Création du thème", "Votre thème a bien été créé avec succès"); } catch (AWTException e) {}
            stage.close();
        });
        cre.setFont(font);

        vBox2.getChildren().addAll(getBar(),spinner,spinner1,spinner2,getSeparator(),spinner3,spinner4,spinner5,getSeparator(),cre);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setBackground(grey);

        group2.getChildren().addAll(getBar(),vBox2);
        scene2.setFill(Color.rgb(200,200,200));

    }












}
