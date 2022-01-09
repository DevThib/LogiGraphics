package sample.logigraphics.interfaces;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import sample.logigraphics.Logiciel;
import sample.logigraphics.creation.Creation;
import sample.logigraphics.creation.Shape;
import sample.logigraphics.creation.ShapeType;

public class TopBar {

    Logiciel logiciel;

    FlowPane flowPane = new FlowPane();

    Background background = new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0)));

    Insets insets = new Insets(10);
    Font font = new Font("Trebuchet MS",10);
    Border buttonBorder = new Border(new BorderStroke(Color.WHITE,BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1)));
    Border buttonBorder2 = new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1)));
    Border buttonBorderOnHover = new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1)));

    Button indicator = new Button();

    Rectangle separator1 = new Rectangle(1,90,Color.BLACK);
    Rectangle separator2 = new Rectangle(1,90,Color.BLACK);

    public TopBar(Logiciel logiciel){
        this.logiciel = logiciel;
    }

    public FlowPane build(){

        separator1.setTranslateY(5);
        separator2.setTranslateY(5);

        indicator.setBackground(new Background(new BackgroundFill(Color.BLACK,new CornerRadii(0),new Insets(0))));
        indicator.setMaxHeight(30);
        indicator.setMinHeight(30);
        indicator.setMinWidth(30);
        indicator.setMaxWidth(30);
        indicator.setPadding(new Insets(5));

        flowPane.setMinWidth(8000);
        flowPane.setMinWidth(8000);
        flowPane.setMinHeight(100);
        flowPane.setMaxHeight(100);
        flowPane.setBackground(new Background(new BackgroundFill(Color.rgb(216,216,216),new CornerRadii(0),new Insets(0))));

        ChoiceBox<String> modes = new ChoiceBox<>();

        modes.setOnAction(event -> {

            switch (modes.getValue()){

                case "Formes":
                    logiciel.setCreation(Creation.SHAPES);
                    logiciel.getMirrorAxe().setVisible(false);
                    break;

                case "Miroir":
                    logiciel.setCreation(Creation.MIRROR);
                    logiciel.setFree(true);
                    break;

            }


        });

        modes.getItems().addAll("Formes","Miroir");
        modes.getSelectionModel().select(0);

        flowPane.getChildren().addAll(getShapeSelector(),separator1,getInvisibleSeparator(),indicator,getColorSelector(),getInvisibleSeparator(),separator2,getInvisibleSeparator(),modes);

        return flowPane;
    }

    private VBox getShapeSelector(){

        VBox vBox = new VBox(5);

        FlowPane flowPane = new FlowPane();
        FlowPane flowPane1 = new FlowPane();
        FlowPane flowPane2 = new FlowPane();

        flowPane.getChildren().addAll(
                getElement(ShapeType.RECTANGLE),
                getButtonSeparation(),
                getElement(ShapeType.CIRCLE),
                getButtonSeparation(),
                getElement(ShapeType.NONFILLEDRECTANGLE),
                getButtonSeparation(),
                getElement(ShapeType.ELLIPSE)
                );

        flowPane1.getChildren().addAll(
                getElement(ShapeType.RECTANGLE),
                getButtonSeparation(),
                getElement(ShapeType.CIRCLE),
                getButtonSeparation(),
                getElement(ShapeType.NONFILLEDRECTANGLE),
                getButtonSeparation(),
                getElement(ShapeType.ELLIPSE)
                );

        flowPane2.getChildren().addAll(
                getElement(ShapeType.RECTANGLE),
                getButtonSeparation(),
                getElement(ShapeType.CIRCLE),
                getButtonSeparation(),
                getElement(ShapeType.NONFILLEDRECTANGLE),
                getButtonSeparation(),
                getElement(ShapeType.ELLIPSE)
        );

        vBox.setBackground(background);
        vBox.getChildren().addAll(flowPane,flowPane1,flowPane2);
        vBox.setPadding(insets);

        return vBox;
    }

    private VBox getColorSelector(){

        VBox vBox = new VBox(5);

        FlowPane flowPane = new FlowPane();
        FlowPane flowPane1 = new FlowPane();
        FlowPane flowPane2 = new FlowPane();

        flowPane.getChildren().addAll(
                getButton(event -> logiciel.changeColor(false,0),Color.WHITE),
                getButtonSeparation(),
                getButton(event -> logiciel.changeColor(false,1),Color.BLACK),
                getButtonSeparation(),
                getButton(event -> logiciel.changeColor(false,2),Color.RED)
        );

        flowPane1.getChildren().addAll(
                getButton(event -> logiciel.changeColor(false,3),Color.BLUE),
                getButtonSeparation(),
                getButton(event -> logiciel.changeColor(false,4),Color.ORANGE),
                getButtonSeparation(),
                getButton(event -> logiciel.changeColor(false,5),Color.PURPLE)
        );

        flowPane2.getChildren().addAll(
                getButton(event -> logiciel.changeColor(false,6),Color.GREEN),
                getButtonSeparation(),
                getButton(event -> logiciel.changeColor(false,7),Color.GREY),
                getButtonSeparation(),
                getButton(event -> logiciel.changeColor(false,8),Color.BROWN)
        );

        vBox.setBackground(background);
        vBox.getChildren().addAll(flowPane,flowPane1,flowPane2);
        vBox.setPadding(insets);

        return vBox;
    }

    private Node getElement(ShapeType shapeType){

        switch (shapeType){

            case RECTANGLE:
                Rectangle rectangle = new Rectangle(20,20);
                rectangle.setOnMouseClicked(event -> logiciel.getShapeCreator().setShapeType(ShapeType.RECTANGLE));
                rectangle.setOnMouseEntered(event1 -> rectangle.setOpacity(0.6));
                rectangle.setOnMouseExited(event1 -> rectangle.setOpacity(1));
                return rectangle;

            case CIRCLE:
                Circle circle = new Circle(10);
                circle.setOnMouseClicked(event -> logiciel.getShapeCreator().setShapeType(ShapeType.CIRCLE));
                circle.setOnMouseEntered(event1 -> circle.setOpacity(0.6));
                circle.setOnMouseExited(event1 -> circle.setOpacity(1));
                return circle;

            case LINE:
                break;

            case NONFILLEDRECTANGLE:
                Button button = new Button();
                button.setMinWidth(19);
                button.setMaxWidth(19);
                button.setMinHeight(19);
                button.setMaxHeight(19);
                button.setBorder(buttonBorder2);
                button.setBackground(background);
                button.setOnMouseEntered(event1 -> button.setOpacity(0.6));
                button.setOnMouseExited(event1 -> button.setOpacity(1));
                button.setOnAction(event -> logiciel.getShapeCreator().setShapeType(ShapeType.NONFILLEDRECTANGLE));
                return button;

            case NONFILLEDCIRCLE:
                break;

            case ELLIPSE:
                Ellipse ellipse = new Ellipse(10,7);
                ellipse.setOnMouseEntered(event1 -> ellipse.setOpacity(0.6));
                ellipse.setOnMouseExited(event1 -> ellipse.setOpacity(1));
                ellipse.setOnMouseClicked(event -> logiciel.getShapeCreator().setShapeType(ShapeType.ELLIPSE));
                return ellipse;

            case HEXAGON:
                break;
            case OCTOGON:
                break;
            case NONFILLEDHEXAGON:
                break;
            case NONFILLEDOCTOGON:
                break;
            case IMAGE:
                break;
        }
        return null;
    }

    private Button getButton(EventHandler<ActionEvent> event, Color fontColor){
        Button button = new Button();
        button.setBackground(new Background(new BackgroundFill(fontColor,new CornerRadii(0),new Insets(0))));
        button.setMaxHeight(20);
        button.setMinHeight(20);
        button.setMinWidth(20);
        button.setMaxWidth(20);
        button.setOnAction(event);
        button.setBorder(buttonBorder);
        button.setOnMouseEntered(event1 -> button.setBorder(buttonBorderOnHover));
        button.setOnMouseExited(event12 -> button.setBorder(buttonBorder));

        return button;
    }

    private Rectangle getButtonSeparation(){
       return new Rectangle(5,20,Color.TRANSPARENT);
    }

    private Rectangle getInvisibleSeparator(){

        Rectangle rectangle = new Rectangle(5,90,Color.GREY);
        rectangle.setTranslateY(5);
        rectangle.setVisible(false);

        return rectangle;
    }

    public Button getIndicator() {
        return indicator;
    }

    public void changeColor(Color color){
        flowPane.setBackground(new Background(new BackgroundFill(color,new CornerRadii(0),new Insets(0))));
        System.out.println(color.getBlue());
        if(color.getRed() == 0.3764705955982208 && color.getBlue() == 0.3764705955982208 && color.getGreen() == 0.3764705955982208){
            separator1.setFill(Color.WHITE);
            separator2.setFill(Color.WHITE);
        }else{
            separator1.setFill(Color.BLACK);
            separator2.setFill(Color.BLACK);
        }
    }

}
