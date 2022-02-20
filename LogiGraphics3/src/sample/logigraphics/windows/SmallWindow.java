package sample.logigraphics.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.logigraphics.Logiciel;
import sample.logigraphics.interfaces.LogicielColors;

public class SmallWindow {

    Group group = new Group();
    Stage stage = new Stage();
    Scene scene = new Scene(group,750,400);

    Background grey = new Background(new BackgroundFill(LogicielColors.getTopBarColor(),new CornerRadii(0),new Insets(0)));
    Background red = new Background(new BackgroundFill(Color.RED,new CornerRadii(0),new Insets(0)));
    Background lessRed = new Background(new BackgroundFill(Color.rgb(255,71,71),new CornerRadii(0),new Insets(0)));

    Font font = new Font("Trebuchet MS",16);

    Rectangle rectangle = new Rectangle();

    String title;

    Background buttonBackground = new Background(new BackgroundFill(Color.rgb(0,169,242),new CornerRadii(5),new Insets(0)));
    Background buttonOnHover = new Background(new BackgroundFill(Color.rgb(0,163,222),new CornerRadii(5),new Insets(0)));

    Border buttonBorder = new Border(new BorderStroke(Color.WHITE,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2)));

    DropShadow ds = new DropShadow();

    VBox vBox = new VBox();

    Button cross = new Button();

    public SmallWindow(String title){
        this.title = title;

        vBox.setMinWidth(scene.getWidth());
        vBox.setMinHeight(scene.getHeight());
        vBox.setAlignment(Pos.CENTER);

        group.getChildren().addAll(vBox,get());

        scene.setFill(LogicielColors.getSmallWindowBackground());

        stage.setTitle(title);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
    }

    public void show(){
        stage.show();
    }

    public void add(Node node){
        vBox.getChildren().add(node);
    }

    private FlowPane get(){

        FlowPane padded = new FlowPane();

        FlowPane flowPane = new FlowPane();
        flowPane.setMinWidth(scene.getWidth());
        flowPane.setMinHeight(40);
        flowPane.setMaxHeight(40);
        flowPane.setMaxWidth(scene.getWidth());
        flowPane.setBackground(grey);

        cross.setMinWidth(50);
        cross.setMinHeight(30);
        cross.setMaxHeight(30);
        cross.setMaxWidth(50);
        cross.setBackground(red);
        cross.setTextFill(Color.WHITE);
        cross.setText("❌");
        cross.setOnMouseEntered(event -> {
            cross.setBackground(lessRed);
            cross.setTextFill(Color.GREY);
        });
        cross.setOnMouseExited(event -> {
            cross.setBackground(red);
            cross.setTextFill(Color.WHITE);
        });
        cross.setOnAction(event -> stage.close());
        cross.setFont(font);

        Rectangle imageView = new Rectangle();
        imageView.setWidth(20);
        imageView.setHeight(20);

        Label title = new Label(this.title);
        title.setFont(font);
        title.setTextFill(Color.WHITE);
        title.setMaxWidth(120);
        title.setMinWidth(120);
        title.setTranslateX(5);
        title.setStyle("-fx-font-weight: bold");

        rectangle.setFill(Color.TRANSPARENT);
        autoCalcRectangle();

        padded.getChildren().addAll(imageView,title,rectangle,cross);
        padded.setPadding(new Insets(5));
        padded.setMinWidth(flowPane.getMinWidth());

        flowPane.getChildren().add(padded);

        return flowPane;
    }

    private void autoCalcRectangle(){
        rectangle.setWidth(scene.getWidth()-200);
    }

    public Button getStyliziedButton(String text){

        Button button = new Button(text);
        button.setBackground(buttonBackground);
        button.setTextFill(Color.WHITE);
        button.setBorder(buttonBorder);
        button.setOnMouseEntered(event -> {
            button.setEffect(ds);
            button.setBackground(buttonOnHover);
        });
        button.setOnMouseExited(event -> {
            button.setEffect(null);
            button.setBackground(buttonBackground);
        });
        button.setStyle("-fx-font-weight: bold");

        return button;
    }

    public double getWidth(){
        return scene.getWidth();
    }

    public double getHeight(){
        return scene.getHeight();
    }

    public void close(){
        stage.close();
    }

    public Font getFont() {
        return font;
    }

    public void setClosable(boolean closable){
        cross.setVisible(closable);
    }

    public void setSpacing(double value){
        vBox.setSpacing(value);
    }
}
