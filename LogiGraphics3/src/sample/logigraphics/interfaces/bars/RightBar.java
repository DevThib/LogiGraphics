package sample.logigraphics.interfaces.bars;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import sample.logigraphics.creation.Creation;
import sample.logigraphics.creation.ShapeType;
import sample.logigraphics.interfaces.LogicielStructure;

public class RightBar {

    Background background = new Background(new BackgroundFill(Color.rgb(60,60,60),new CornerRadii(0),new Insets(0)));

    FlowPane principal = new FlowPane();

    Font trebuchet = new Font("Trebuchet MS",20);

    Border indic = new Border(new BorderStroke(Color.WHITE,BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1)));

    Background choiceBack = new Background(new BackgroundFill(Color.rgb(40,40,40),new CornerRadii(5),new Insets(0)));

    Color backgroundC = Color.rgb(60,60,60);
    Color choiceC = Color.BLACK;

    LogicielStructure logicielStructure;

    public RightBar(LogicielStructure logicielStructure){
        this.logicielStructure = logicielStructure;
    }

    public FlowPane get(){

        principal.setMinWidth(200);
        principal.setMaxWidth(200);

        principal.getChildren().addAll(getButtons(),getCreation());

        return principal;
    }

    public FlowPane getButtons(){

        FlowPane cre = getCreation();
        FlowPane ma = new FlowPane();

        FlowPane flowPane = new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setMinWidth(30);
        flowPane.setMaxWidth(30);

        Ellipse creation = new Ellipse(20,50);
        Ellipse maths = new Ellipse(20,50);

        creation.setOnMouseClicked(event -> {
            creation.setFill(backgroundC);
            maths.setFill(choiceC);
            principal.getChildren().set(1,cre);
        });
        creation.setFill(backgroundC);
        creation.setTranslateX(10);

        maths.setOnMouseClicked(event -> {
            maths.setFill(backgroundC);
            creation.setFill(choiceC);
            principal.getChildren().set(1,ma);
        });
        maths.setFill(choiceC);
        maths.setTranslateX(10);

        flowPane.getChildren().addAll(creation,maths);

        return flowPane;
    }

    public FlowPane getCreation(){

        FlowPane flowPane = new FlowPane();

        flowPane.setMinWidth(170);
        flowPane.setMaxWidth(170);
        flowPane.setMinHeight(8000);
        flowPane.setBackground(background);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.setVgap(15);
        flowPane.getChildren().addAll(new Rectangle(0,0,Color.TRANSPARENT),getFirst(),getChoiceSelector(),getSeparator(),getColorSelector(),getSeparator(),getFunctionSelector());

        return flowPane;
    }

    public Rectangle getSeparator(){
        Rectangle rectangle = new Rectangle(150,1);
        rectangle.setFill(Color.WHITE);
        return rectangle;
    }

    private VBox getFirst(){

        VBox first = new VBox(10);
        first.setAlignment(Pos.CENTER);

        Label title = new Label("Création");
        title.setTextFill(Color.WHITE);
        title.setFont(trebuchet);

        first.getChildren().addAll(title,getSeparator());
        return first;
    }

    private VBox getChoiceSelector(){

        VBox vBox = new VBox();
        vBox.setMinWidth(160);
        vBox.setMaxWidth(160);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(10);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(5);
        flowPane.setTranslateX(5);
        FlowPane flowPane1 = new FlowPane();
        flowPane1.setHgap(5);
        flowPane1.setTranslateX(5);
        FlowPane flowPane2 = new FlowPane();
        flowPane2.setHgap(5);
        flowPane2.setTranslateX(5);

        Rectangle rectangle = new Rectangle(33.75,33.75,Color.RED);
        Circle circle = new Circle(16.875,Color.WHITE);
        Polygon triangle = new Polygon();
        Polygon hexagon = new Polygon();
        Ellipse ellipse = new Ellipse(16.875,13);

        Group n1 = getNonFilled("Rectangle");
        Group n2 = getNonFilled("Circle");
        Group n3 = getNonFilled("Line");
        Group n4 = getNonFilled("Triangle");

        Rectangle nonfilled = (Rectangle) n1.getChildren().get(0);
        Rectangle nonfilled2 = (Rectangle) n1.getChildren().get(1);
        Circle nonfilledC = (Circle) n2.getChildren().get(0);
        Circle nonfilledC2 = (Circle) n2.getChildren().get(1);
        Rectangle line = (Rectangle) n3.getChildren().get(1);
        Rectangle line2 = (Rectangle) n3.getChildren().get(0);

        rectangle.setOnMouseExited(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.RECTANGLE){
                rectangle.setFill(Color.WHITE);
            }
        });
        rectangle.setOnMouseEntered(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.RECTANGLE){
                rectangle.setFill(Color.GREY);
            }
        });
        rectangle.setCursor(Cursor.HAND);
        rectangle.setOnMouseClicked(event -> {
            rectangle.setFill(Color.RED);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.RECTANGLE);
        });

        circle.setOnMouseExited(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.CIRCLE){
                circle.setFill(Color.WHITE);
            }
        });
        circle.setOnMouseEntered(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.CIRCLE){
                circle.setFill(Color.GREY);
            }
        });
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.RED);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.CIRCLE);
        });

        triangle.getPoints().addAll(0.0,0.0,33.75,0.0,16.875,-33.75);
        triangle.setFill(Color.WHITE);
        triangle.setOnMouseExited(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.TRIANGLE){
                triangle.setFill(Color.WHITE);
            }
        });
        triangle.setOnMouseEntered(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.TRIANGLE){
                triangle.setFill(Color.GREY);
            }
        });
        triangle.setCursor(Cursor.HAND);
        triangle.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.RED);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.TRIANGLE);
        });

        hexagon.getPoints().addAll(16.875,0.0,0.0,-8.4375,0.0,-25.3125,16.875,-33.75,33.75,-25.3125,33.75,-8.4375);
        hexagon.setFill(Color.WHITE);
        hexagon.setOnMouseExited(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.HEXAGON){
                hexagon.setFill(Color.WHITE);
            }
        });
        hexagon.setOnMouseEntered(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.HEXAGON){
                hexagon.setFill(Color.GREY);
            }
        });
        hexagon.setCursor(Cursor.HAND);
        hexagon.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.RED);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.HEXAGON);
        });

        ellipse.setFill(Color.WHITE);
        ellipse.setOnMouseExited(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.ELLIPSE){
                ellipse.setFill(Color.WHITE);
            }
        });
        ellipse.setOnMouseEntered(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.ELLIPSE){
                ellipse.setFill(Color.GREY);
            }
        });
        ellipse.setCursor(Cursor.HAND);
        ellipse.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.RED);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.ELLIPSE);
        });
        nonfilled.setOnMouseExited(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDRECTANGLE){
                nonfilled.setFill(Color.WHITE);
            }
        });
        nonfilled.setOnMouseEntered(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDRECTANGLE){
                nonfilled.setFill(Color.GREY);
            }
        });
        nonfilled.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.RED);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.NONFILLEDRECTANGLE);
        });
        nonfilledC.setOnMouseExited(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDCIRCLE){
                nonfilledC.setFill(Color.WHITE);
            }
        });
        nonfilledC.setOnMouseEntered(event -> {
            if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDCIRCLE){
                nonfilledC.setFill(Color.GREY);
            }
        });
        nonfilledC.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.RED);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.NONFILLEDCIRCLE);
        });
        nonfilled2.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.RED);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.NONFILLEDRECTANGLE);
        });
        nonfilledC2.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.RED);
            line.setFill(Color.WHITE);
            logicielStructure.getLogiciel().setShapeType(ShapeType.NONFILLEDCIRCLE);
        });
        line.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.RED);
            logicielStructure.getLogiciel().setShapeType(ShapeType.LINE);
        });
        line2.setOnMouseClicked(event -> {
            rectangle.setFill(Color.WHITE);
            circle.setFill(Color.WHITE);
            triangle.setFill(Color.WHITE);
            hexagon.setFill(Color.WHITE);
            ellipse.setFill(Color.WHITE);
            nonfilled.setFill(Color.WHITE);
            nonfilledC.setFill(Color.WHITE);
            line.setFill(Color.RED);
            logicielStructure.getLogiciel().setShapeType(ShapeType.LINE);
        });

        n1.getChildren().set(0,nonfilled);
        n2.getChildren().set(0,nonfilledC);
        n3.getChildren().set(1,line);

        flowPane.getChildren().addAll(rectangle,circle,triangle,hexagon);
        flowPane1.getChildren().addAll(n1,n2,ellipse,n3);
        flowPane2.getChildren().addAll(n4);

        vBox.getChildren().addAll(flowPane,flowPane1,flowPane2);

        return vBox;
    }

    private Group getNonFilled(String node){

        switch (node){

            case "Rectangle":
                Rectangle r1 = new Rectangle(33.75,33.75,Color.WHITE);
                Rectangle r2 = new Rectangle(28.75,28.75,Color.rgb(60,60,60));
                r2.setTranslateX(2.5);
                r2.setTranslateY(2.5);

                Group group = new Group();

                r1.setOnMouseEntered(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDRECTANGLE){
                        r1.setFill(Color.WHITE);
                    }
                });
                r2.setOnMouseEntered(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDRECTANGLE){
                        r1.setFill(Color.GREY);
                    }
                });
                r1.setOnMouseExited(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDRECTANGLE){
                        r1.setFill(Color.WHITE);
                    }
                });
                r2.setOnMouseExited(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDRECTANGLE){
                        r1.setFill(Color.WHITE);
                    }
                });
                r1.setCursor(Cursor.HAND);
                r2.setCursor(Cursor.HAND);

                group.getChildren().addAll(r1,r2);

                return group;

            case "Circle":
                Circle c1 = new Circle(16.845,Color.WHITE);
                Circle c2 = new Circle(14.345,Color.rgb(60,60,60));

                Group group2 = new Group();

                c1.setOnMouseEntered(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDCIRCLE){
                        c1.setFill(Color.WHITE);
                    }
                });
                c2.setOnMouseEntered(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDCIRCLE){
                        c1.setFill(Color.GREY);
                    }
                });
                c1.setOnMouseExited(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDCIRCLE){
                        c1.setFill(Color.WHITE);
                    }
                });
                c2.setOnMouseExited(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.NONFILLEDCIRCLE){
                        c1.setFill(Color.WHITE);
                    }
                });
                c1.setCursor(Cursor.HAND);
                c2.setCursor(Cursor.HAND);

                group2.getChildren().addAll(c1,c2);

                return group2;

            case "Line":
                Rectangle back = new Rectangle(33.75,33.75,Color.rgb(60,60,60));
                Rectangle rectangle = new Rectangle(33.75,4,Color.WHITE);
                rectangle.setRotate(45);
                rectangle.setTranslateY(16.875);

                Group group3 = new Group();

                back.setOnMouseEntered(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.LINE){
                        rectangle.setFill(Color.GREY);
                    }
                });
                back.setOnMouseExited(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.LINE){
                        rectangle.setFill(Color.WHITE);
                    }
                });
                rectangle.setOnMouseEntered(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.LINE){
                        rectangle.setFill(Color.GREY);
                    }
                });
                rectangle.setCursor(Cursor.HAND);
                back.setCursor(Cursor.HAND);

                group3.getChildren().addAll(back,rectangle);

                return group3;

            case "Triangle":

                Polygon triangle = new Polygon();
                Polygon black = new Polygon();

                triangle.getPoints().addAll(0.0,0.0,33.75,0.0,16.875,-33.75);
                triangle.setFill(Color.WHITE);
                triangle.setOnMouseEntered(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.TRIANGLE){
                        triangle.setFill(Color.GREY);
                    }
                });
                triangle.setCursor(Cursor.HAND);

                black.getPoints().addAll(0.0,0.0,33.75,0.0,16.875,-33.75);
                black.setFill(Color.rgb(60,60,60));
                black.setOnMouseExited(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.TRIANGLE){
                        triangle.setFill(Color.WHITE);
                    }
                });
                black.setOnMouseEntered(event -> {
                    if(logicielStructure.getLogiciel().getShapeType() != ShapeType.TRIANGLE){
                        triangle.setFill(Color.GREY);
                    }
                });
                black.setCursor(Cursor.HAND);

                Group group4 = new Group();


                group4.getChildren().addAll(triangle,black);

                return group4;

        }

        return null;
    }

    private VBox getColorSelector(){

        Rectangle indicator = new Rectangle(100,35,Color.BLACK);

        VBox vBox = new VBox();
        vBox.setMinWidth(160);
        vBox.setMaxWidth(160);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(10);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(5);
        flowPane.setTranslateX(5);
        FlowPane flowPane1 = new FlowPane();
        flowPane1.setHgap(5);
        flowPane1.setTranslateX(5);
        FlowPane flowPane2 = new FlowPane();
        flowPane2.setHgap(5);
        flowPane2.setTranslateX(5);

        flowPane.getChildren().addAll(
                getButton(indicator,Color.WHITE),
                getButton(indicator,Color.BLACK),
                getButton(indicator,Color.RED),
                getButton(indicator,Color.AQUA)
        );

        flowPane1.getChildren().addAll(
                getButton(indicator,Color.BLUE),
                getButton(indicator,Color.ORANGE),
                getButton(indicator,Color.PURPLE),
                getButton(indicator,Color.CHOCOLATE)
        );

        flowPane2.getChildren().addAll(
                getButton(indicator,Color.GREEN),
                getButton(indicator,Color.GREY),
                getButton(indicator,Color.BROWN),
                getButton(indicator,Color.INDIGO)
        );

        VBox flowPane3 = new VBox();
        flowPane3.setMaxWidth(130);
        flowPane3.setMinWidth(130);
        flowPane3.setMinHeight(50);
        flowPane3.setMaxHeight(50);
        flowPane3.setBorder(indic);
        flowPane3.getChildren().add(indicator);
        flowPane3.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(flowPane,flowPane1,flowPane2,flowPane3);

        return vBox;
    }

    private Rectangle getButton(Rectangle indicator, Color fontColor){

        Rectangle rectangle = new Rectangle(33.75,33.75,fontColor);
        rectangle.setCursor(Cursor.HAND);
        rectangle.setOnMouseEntered(event12 -> {
            rectangle.setOpacity(0.8);
            rectangle.setScaleX(1.1);
            rectangle.setScaleY(1.1);
        });
        rectangle.setOnMouseExited(event13 -> {
            rectangle.setOpacity(1);
            rectangle.setScaleX(1);
            rectangle.setScaleY(1);
        });
        rectangle.setOnMouseClicked(event1 -> {
            indicator.setFill(fontColor);
            logicielStructure.getLogiciel().setSelectedColor(fontColor);
        });

        return rectangle;
    }

    private ChoiceBox<String> getFunctionSelector(){

        ChoiceBox<String> modes = new ChoiceBox<>();

        modes.setOnAction(event -> {

            switch (modes.getValue()){

                case "Formes":
                 //   logicielStructure.getLogiciel().setCreation(Creation.SHAPES);
                  //  logicielStructure.getLogiciel().getMirrorAxe().setVisible(false);
                    break;

                case "Miroir":
                   // logicielStructure.getLogiciel().setCreation(Creation.MIRROR);
                   // logicielStructure.getLogiciel().setFree(true);
                    break;

            }


        });

        modes.getItems().addAll("Formes","Miroir");
        modes.getSelectionModel().select(0);
        modes.setMinWidth(150);
        modes.setStyle(" -fx-background-color: black;\n" +
                "  -fx-mark-color: orange;");
        modes.setTooltip(new Tooltip("Changer de mode de création"));


        return modes;
    }

}
