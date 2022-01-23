package sample.logigraphics.interfaces;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TreeView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.logigraphics.Logiciel;
import sample.logigraphics.interfaces.bars.LogicielBar;
import sample.logigraphics.interfaces.bars.RightBar;

import java.io.File;
import java.util.ArrayList;

public class LogicielStructure {

    BorderPane borderPane = new BorderPane();
    Stage stage = new Stage();
    Scene scene = new Scene(borderPane, 1650, 900);

    LogicielBar logicielBar;

    Logiciel logiciel;

    TreeBuilder treeBuilder = new TreeBuilder(new File("C:\\Users\\thiba\\Desktop\\ee"),borderPane,scene);

    Background background = new Background(new BackgroundFill(LogicielColors.getBackgroundColor(),new CornerRadii(0),new Insets(0)));

    RightBar rightBar = new RightBar(this);

    Canvas canvas = new Canvas();
    GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    public LogicielStructure(Logiciel logiciel){
        this.logiciel = logiciel;
        logicielBar = new LogicielBar(this);

        borderPane.setMinWidth(scene.getWidth());
        borderPane.setMinHeight(scene.getHeight());
        borderPane.setRight(rightBar.get());
        borderPane.setTop(logicielBar.get());
        borderPane.setCenter(canvas);
        borderPane.setLeft(treeBuilder.get());
        borderPane.setBackground(background);

        adaptCanvasSize();
        buildCanvas();

        DropShadow ds = new DropShadow();
        ds.setOffsetY(5);
        ds.setOffsetX(5);
        ds.setRadius(10);

        canvas.setEffect(ds);
        canvas.setCursor(Cursor.CROSSHAIR);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        graphicsContext.setFill(Color.BLACK);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void changeTheme(){
        
    }

    private void buildCanvas(){

        canvas.setOnMouseClicked(event -> {

            switch(logiciel.getShapeType()){

                case TRIANGLE:
                    if(logiciel.getxPoints().size() == 2){
                        logiciel.getxPoints().add(event.getX());
                        logiciel.getyPoints().add(event.getY());
                        logiciel.stopCreating();
                        logiciel.setxPoints(new ArrayList<>());
                        logiciel.setyPoints(new ArrayList<>());

                    }else{
                        logiciel.getxPoints().add(event.getX());
                        logiciel.getyPoints().add(event.getY());
                    }
                    break;

                case HEXAGON:
                    if(logiciel.getxPoints().size() == 6){
                        logiciel.getxPoints().add(event.getX());
                        logiciel.getyPoints().add(event.getY());
                        logiciel.stopCreating();
                        logiciel.setxPoints(new ArrayList<>());
                        logiciel.setyPoints(new ArrayList<>());

                    }else{
                        logiciel.getxPoints().add(event.getX());
                        logiciel.getyPoints().add(event.getY());
                    }
                    break;

                default:
                    if(!logiciel.isCreating()){
                        logiciel.setStartX(event.getX());
                        logiciel.setStartY(event.getY());
                        logiciel.setCreating(true);
                    }else{
                        logiciel.setCurrentX(event.getX());
                        logiciel.setCurrentY(event.getY());
                        logiciel.stopCreating();
                    }
                    break;

            }




        });

    }

    public Logiciel getLogiciel() {
        return logiciel;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public Group getGroup() {
        return null;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void adaptCanvasSize(){
        canvas.setWidth(scene.getWidth()-380);
        canvas.setHeight(scene.getHeight()-60);
    }

    public void setTitle(String title){
        logicielBar.setTitle(title);
    }
}
