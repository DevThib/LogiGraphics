package sample.logigraphics.windows;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.logigraphics.Logiciel;
import sample.logigraphics.events.Event;
import sample.logigraphics.interfaces.LogicielColors;

public class SmallWindow {

    Group group = new Group();
    Stage stage = new Stage();
    Scene scene;

    Background grey = new Background(new BackgroundFill(LogicielColors.getTopBarColor(),new CornerRadii(0),new Insets(0)));
    Background red = new Background(new BackgroundFill(Color.RED,new CornerRadii(0),new Insets(0)));
    Background lessRed = new Background(new BackgroundFill(Color.rgb(255,71,71),new CornerRadii(0),new Insets(0)));

    Font font;
    Font labelFont = new Font("Trebuchet MS",20);

    String title;
    ImageView icon;

    Background buttonBackground = new Background(new BackgroundFill(Color.rgb(0,169,242),new CornerRadii(5),new Insets(0)));
    Background buttonOnHover = new Background(new BackgroundFill(Color.rgb(0,163,222),new CornerRadii(5),new Insets(0)));
    Background redBackground = new Background(new BackgroundFill(Color.RED,new CornerRadii(5),new Insets(0)));
    Background redOnHover = new Background(new BackgroundFill(Color.rgb(255,71,71),new CornerRadii(5),new Insets(0)));

    Border buttonBorder = new Border(new BorderStroke(Color.WHITE,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(2)));

    DropShadow ds = new DropShadow();

    VBox vBox = new VBox();

    Button cross = new Button("❌");

    double size;

    Event event;

    Border indic = new Border(new BorderStroke(Color.WHITE,BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1)));

    public SmallWindow(String title,double size,ImageView icon){
        this.title = title;
        this.size = size;
        this.font = new Font("Trebuchet MS",16*size);
        this.icon = icon;

        this.scene = new Scene(group,750*size,400*size);

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void add(Node node){
        vBox.getChildren().add(node);
    }

    private BorderPane get(){

        BorderPane padd = new BorderPane();
        FlowPane left = new FlowPane();
        FlowPane right = new FlowPane();

        final double[] initialX = {0};
        final double[] initialY = {0};

        cross.setMinWidth(50*size);
        cross.setMinHeight(30*size);
        cross.setMaxHeight(30*size);
        cross.setMaxWidth(50*size);
        cross.setBackground(red);
        cross.setTextFill(Color.WHITE);
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

        Label title = new Label(this.title);
        title.setFont(font);
        title.setTextFill(Color.WHITE);
        title.setTranslateX(5*size);

        left.getChildren().addAll(icon,title);
        left.setOrientation(Orientation.HORIZONTAL);
        left.setHgap(5);
        left.setPadding(new Insets(10*size,0,10*size,5*size));

        right.getChildren().addAll(cross);
        right.setOrientation(Orientation.HORIZONTAL);
        right.setPadding(new Insets(5*size,0,5*size,-5*size));
        right.setMinWidth(cross.getMinWidth());
        right.setMaxWidth(cross.getMaxWidth());
        right.setMinHeight(cross.getMinHeight());
        right.setMaxHeight(cross.getMaxHeight());

        padd.setLeft(left);
        padd.setRight(right);
        padd.setMinWidth(scene.getWidth());
        padd.setMaxWidth(scene.getWidth());
        padd.setMinHeight(40*size);
        padd.setMaxHeight(40*size);
        padd.setOnMousePressed(event -> {
            initialX[0] = event.getX();
            initialY[0] = event.getY();
        });
        padd.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX()-initialX[0]);
            stage.setY(event.getScreenY()-initialY[0]);
        });
        padd.setBackground(grey);

        return padd;
    }

    public Button getStyliziedButton(String text,boolean red){

        Button button = new Button(text);
        button.setBackground(getButtonBackground(red,false));
        button.setTextFill(Color.WHITE);
        button.setBorder(buttonBorder);
        button.setOnMouseEntered(event -> {
            button.setEffect(ds);
            button.setBackground(getButtonBackground(red,true));
        });
        button.setOnMouseExited(event -> {
            button.setEffect(null);
            button.setBackground(getButtonBackground(red,false));
        });
        button.setStyle("-fx-font-weight: bold");

        return button;
    }

    private Background getButtonBackground(boolean red,boolean hover){
        if(red){
            if(hover)return redOnHover; else return redBackground;
        }else{
            if(hover)return buttonOnHover;else return buttonBackground;
        }
    }

    public double getWidth(){
        return scene.getWidth();
    }

    public double getHeight(){
        return scene.getHeight();
    }

    public Font getLabelFont() {
        return labelFont;
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

    public void addLabel(String text){
        Label label = new Label(text);
        label.setFont(labelFont);
        label.setTextFill(Color.WHITE);
        vBox.getChildren().add(label);
    }
}
