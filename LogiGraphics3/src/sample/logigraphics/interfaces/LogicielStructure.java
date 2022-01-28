package sample.logigraphics.interfaces;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.logigraphics.Logiciel;
import sample.logigraphics.creation.Point;
import sample.logigraphics.creation.ShapeType;
import sample.logigraphics.interfaces.bars.LogicielBar;
import sample.logigraphics.interfaces.bars.RightBar;
import sample.logigraphics.interfaces.bars.SettingsBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class LogicielStructure {

    BorderPane borderPane = new BorderPane();
    Stage stage = new Stage();
    Scene scene = new Scene(borderPane, 1650, 900);

    LogicielBar logicielBar;

    SettingsBar settingsBar = new SettingsBar(this);

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
        group.getChildren().addAll(canvas,logiciel.getMirrorAxe(),logiciel.getIndicator(),logiciel.getShow().getRectangle(),logiciel.getShow().getCircle(),logiciel.getShow().getLine());

        VBox bars = new VBox();
        bars.getChildren().addAll(logicielBar.get(),settingsBar.get());

        borderPane.setMinWidth(scene.getWidth());
        borderPane.setMinHeight(scene.getHeight());
        borderPane.setRight(rightBar.get());
        borderPane.setTop(bars);
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

        loadBackgroundImage();

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void loadBackgroundImage(){

        try {

            File file = null;

            for (File file1 : logiciel.getDataBase().getDirectoryByName("cache").getFiles()) {
                if (file1.getName().split("\\.")[0].equals("pp")) {
                    file = file1;
                }
            }

            if (file != null) {

                Image img = new Image(new FileInputStream(file));
                BackgroundImage bImg = new BackgroundImage(
                        img,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT);

                Background bGround = new Background(bImg);
                borderPane.setBackground(bGround);
                canvas.setOpacity(0.5);
            }

        }catch (FileNotFoundException e){}

    }

    public void setExtend(boolean extend){

    }

    public void changeTheme(){
        
    }

    private void buildCanvas(){

        canvas.setOnMouseClicked(event -> {

            switch(logiciel.getShapeType()){

                case TRIANGLE:
                    if(logiciel.getShapesPoints().size() == 2){
                        logiciel.getShapesPoints().add(new Point(event.getX(),event.getY()));
                        logiciel.stopCreating();
                        for(Point point : logiciel.getShapesPoints()){
                            logiciel.getPoints().add(point);
                        }
                        logiciel.setShapesPoints(new ArrayList<>());
                    }else{
                        logiciel.getShapesPoints().add(new Point(event.getX(),event.getY()));
                    }
                    break;

                case HEXAGON:
                    if(logiciel.getShapesPoints().size() == 6){
                        logiciel.getShapesPoints().add(new Point(event.getX(),event.getY()));
                        logiciel.stopCreating();
                        for(Point point : logiciel.getShapesPoints()){
                            logiciel.getPoints().add(point);
                        }
                        logiciel.setShapesPoints(new ArrayList<>());
                    }else{
                        logiciel.getShapesPoints().add(new Point(event.getX(),event.getY()));
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

                /*    if(logiciel.isCreating()){
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

                    }*/

                    break;

            }




        });

    /*    canvas.setOnMouseMoved(event -> {

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

        });*/

        AtomicBoolean pointPLaced = new AtomicBoolean(false);

        canvas.setOnMouseMoved(event -> {
            pointPLaced.set(false);
            for(Point point : logiciel.getPoints()){
                if(point.getX() > event.getX()-1 && point.getX() < event.getX()+1 || point.getY() > event.getY()-1 && point.getY() < event.getY()+1){
                    logiciel.getIndicator().setStartX(point.getX());
                    logiciel.getIndicator().setStartY(point.getY());
                    logiciel.getIndicator().setEndX(event.getX()-1);
                    logiciel.getIndicator().setEndY(event.getY()-1);
                    pointPLaced.set(true);
                }
            }
            logiciel.getIndicator().setVisible(pointPLaced.get());
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
        canvas.setHeight(scene.getHeight()-90);
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
