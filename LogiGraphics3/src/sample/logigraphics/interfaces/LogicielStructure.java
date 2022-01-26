package sample.logigraphics.interfaces;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.logigraphics.Logiciel;
import sample.logigraphics.creation.Creation;
import sample.logigraphics.creation.ShapeType;
import sample.logigraphics.interfaces.bars.LogicielBar;
import sample.logigraphics.interfaces.bars.RightBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LogicielStructure {

    BorderPane borderPane = new BorderPane();
    Stage stage = new Stage();
    Scene scene = new Scene(borderPane, 1650, 900);

    LogicielBar logicielBar;

    Logiciel logiciel;

    TreeBuilder treeBuilder;

    Background background = new Background(new BackgroundFill(LogicielColors.getBackgroundColor(),new CornerRadii(0),new Insets(0)));

    RightBar rightBar = new RightBar(this);

    Canvas canvas = new Canvas();
    GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    File imageOpened;

    boolean pressing = false;

    public LogicielStructure(Logiciel logiciel){
        this.logiciel = logiciel;
        logicielBar = new LogicielBar(this);

        treeBuilder = new TreeBuilder(new File(logiciel.getDataBase().getDirectoryByName("cache").readLineFile("root.txt",0)),borderPane,scene,this);

        adaptCanvasSize();
        buildCanvas();

        Group group = new Group();
        group.getChildren().addAll(canvas,logiciel.getMirrorAxe(),logiciel.getShow().getRectangle(),logiciel.getShow().getCircle(),logiciel.getShow().getLine());

        borderPane.setMinWidth(scene.getWidth());
        borderPane.setMinHeight(scene.getHeight());
        borderPane.setRight(rightBar.get());
        borderPane.setTop(logicielBar.get());
        borderPane.setCenter(group);
        borderPane.setLeft(treeBuilder.get());
        borderPane.setBackground(background);

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

    public void setExtend(boolean extend){

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

                    if(logiciel.isCreating()){
                        if(logiciel.getShapeType() == ShapeType.CIRCLE){
                            logiciel.getShow().showCircle(event.getX(),event.getY(),graphicsContext.getStroke());
                        }
                        if(logiciel.getShapeType() == ShapeType.RECTANGLE){
                            logiciel.getShow().showRectangle(event.getX(),event.getY(),graphicsContext.getStroke());
                        }
                        if(logiciel.getShapeType() == ShapeType.LINE){
                            logiciel.getShow().showLine(event.getX(),event.getY(),graphicsContext.getStroke());
                        }

                    }else{

                        if(logiciel.getShapeType() == ShapeType.CIRCLE){
                            logiciel.getShow().hideCircle();
                        }
                        if(logiciel.getShapeType() == ShapeType.RECTANGLE){
                            logiciel.getShow().hideRectangle();
                        }
                        if(logiciel.getShapeType() == ShapeType.LINE){
                            logiciel.getShow().hideLine();
                        }

                    }

                    break;

            }




        });

        canvas.setOnMouseMoved(event -> {

            if(logiciel.isCreating()) {

                switch (logiciel.getShapeType()) {

                    case RECTANGLE:

                        if(event.getX() < logiciel.getShow().getRectangle().getX() && event.getY() < logiciel.getShow().getRectangle().getY()){
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getRectangle().getX());
                            logiciel.getShow().getRectangle().setHeight(logiciel.getShow().getRectangle().getY()-event.getY());
                        }else if(event.getX() < logiciel.getShow().getRectangle().getX() && event.getY() > logiciel.getShow().getRectangle().getY()){
                            logiciel.getShow().getRectangle().setWidth(logiciel.getShow().getRectangle().getX()-event.getX());
                            logiciel.getShow().getRectangle().setHeight(event.getY()-logiciel.getShow().getRectangle().getY());
                        }else if(event.getX() > logiciel.getShow().getRectangle().getX() && event.getY() < logiciel.getShow().getRectangle().getY()){
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getRectangle().getX());
                            logiciel.getShow().getRectangle().setHeight(logiciel.getShow().getRectangle().getY()-event.getY());
                        }else{
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getRectangle().getX());
                            logiciel.getShow().getRectangle().setHeight(event.getY()-logiciel.getShow().getRectangle().getY());
                        }
                        break;

                    case CIRCLE:
                        if((event.getY() - logiciel.getShow().getCircle().getCenterY()) > (event.getX() - logiciel.getShow().getCircle().getCenterX())){
                            logiciel.getShow().getCircle().setRadius(Math.abs(event.getY()-logiciel.getShow().getCircle().getCenterY()));
                        }else{
                            logiciel.getShow().getCircle().setRadius(Math.abs(event.getX()-logiciel.getShow().getCircle().getCenterX()));
                        }
                        break;

                    case LINE:
                        logiciel.getShow().getLine().setEndY(event.getY()-1);
                        logiciel.getShow().getLine().setEndX(event.getX()-1);
                        break;

                }

            }

        });

        canvas.setOnMousePressed(event -> pressing = true);
        canvas.setOnMouseReleased(event -> pressing = false);
        canvas.setOnMouseDragged(event -> {
            if(pressing && logiciel.getShapeType() == ShapeType.PENCIL){
                graphicsContext.fillRect(event.getX(),event.getY(),5,5);
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

    public void openImage(File image){

        try {

            canvas = new Canvas();
            adaptCanvasSize();
            buildCanvas();

            Paint color = this.graphicsContext.getFill();
            Paint stroke = this.graphicsContext.getStroke();
            Image image1 = new Image(new FileInputStream(image));

            graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setFill(color);
            graphicsContext.setStroke(stroke);
            graphicsContext.drawImage(image1, 0, 0);

            DropShadow ds = new DropShadow();
            ds.setOffsetY(5);
            ds.setOffsetX(5);
            ds.setRadius(10);

            canvas.setEffect(ds);
            canvas.setCursor(Cursor.CROSSHAIR);
            canvas.setHeight(image1.getHeight());
            canvas.setWidth(image1.getWidth());

            setTitle(image.getName());

            borderPane.setCenter(canvas);

            imageOpened = image;
        }catch (FileNotFoundException e){
            //afficher qu'une erreur s'est produite
        }
    }

    public String getTitle(){
        return logicielBar.getTitle();
    }

    public boolean hasImageOpened() {
        return imageOpened != null;
    }

    public File getImageOpened() {
        return imageOpened;
    }
}
