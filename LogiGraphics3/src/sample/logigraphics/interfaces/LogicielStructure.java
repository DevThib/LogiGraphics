package sample.logigraphics.interfaces;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.logigraphics.Logiciel;
import sample.logigraphics.creation.Point;
import sample.logigraphics.creation.ShapeType;
import sample.logigraphics.creation.Show;
import sample.logigraphics.interfaces.bars.LogicielBar;
import sample.logigraphics.interfaces.bars.RightBar;
import sample.logigraphics.interfaces.bars.SettingsBar;
import sample.logigraphics.tools.Glass;

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

    ContextMenu menu = new ContextMenu();

    boolean pressing = false;

    Grid grid = new Grid();

    Glass glass = new Glass(this);

    Show show = new Show();

    public LogicielStructure(Logiciel logiciel){
        this.logiciel = logiciel;
        logicielBar = new LogicielBar(this);

        treeBuilder = new TreeBuilder(new File(logiciel.getDataBase().getDirectoryByName("cache").readLineFile("root.txt",0)),borderPane,scene,this);

        adaptCanvasSize();
        buildCanvas();
        buildMenu();

        grid.setCanvasSize(canvas.getWidth(), canvas.getHeight());
        grid.setNumberOfLines(1);
        grid.setVisible(false);

        Group group = new Group();
        group.getChildren().addAll(canvas,logiciel.getMirrorAxe(),grid.getGrid());
        group.getChildren().addAll(logiciel.getShow().getRectangle(),logiciel.getShow().getLine(),logiciel.getShow().getCircle());

        VBox bars = new VBox();
        bars.getChildren().addAll(logicielBar.get(), settingsBar.get());

        borderPane.setMinWidth(scene.getWidth());
        borderPane.setMinHeight(scene.getHeight());
        borderPane.setRight(rightBar.get());
        borderPane.setTop(bars);
        borderPane.setCenter(group);
        borderPane.setLeft(treeBuilder.get());
        borderPane.setBackground(background);
        borderPane.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY)menu.show(borderPane,event.getScreenX(),event.getScreenY());
        });

        DropShadow ds = new DropShadow();
        ds.setOffsetY(5);
        ds.setOffsetX(5);
        ds.setRadius(10);

        canvas.setEffect(ds);
        canvas.setCursor(Cursor.CROSSHAIR);

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        graphicsContext.setFill(Color.BLACK);

        try {
            loadBackgroundImage();
        }catch (FileNotFoundException e){}

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.7),borderPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        stage.setOnShowing(event -> fadeIn.play());
    }

    public void loadBackgroundImage() throws FileNotFoundException {

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

    }

    public Glass getGlass() {
        return glass;
    }

    public SettingsBar getSettingsBar() {
        return settingsBar;
    }

    private void buildCanvas(){

        AtomicBoolean pointPLaced = new AtomicBoolean(false);

        canvas.setOnMouseClicked(event -> {

            if(event.getButton() == MouseButton.PRIMARY) {

                switch (logiciel.getShapeType()) {

                    case TRIANGLE:
                        if (logiciel.getShapesPoints().size() == 2) {
                            logiciel.getShapesPoints().add(new Point(event.getX(), event.getY()));
                            logiciel.stopCreating();
                            for (Point point : logiciel.getShapesPoints()) {
                                logiciel.getPoints().add(point);
                            }
                            logiciel.setShapesPoints(new ArrayList<>());
                        } else {
                            logiciel.getShapesPoints().add(new Point(event.getX(), event.getY()));
                        }
                        break;

                    case HEXAGON:
                        if (logiciel.getShapesPoints().size() == 6) {
                            logiciel.getShapesPoints().add(new Point(event.getX(), event.getY()));
                            logiciel.stopCreating();
                            for (Point point : logiciel.getShapesPoints()) {
                                logiciel.getPoints().add(point);
                            }
                            logiciel.setShapesPoints(new ArrayList<>());
                        } else {
                            logiciel.getShapesPoints().add(new Point(event.getX(), event.getY()));
                        }
                        break;

                    default:
                        if (!logiciel.isCreating()) {
                            logiciel.setStartX(event.getX());
                            logiciel.setStartY(event.getY());
                            logiciel.setCreating(true);
                        } else {
                            logiciel.setCurrentX(event.getX());
                            logiciel.setCurrentY(event.getY());
                            logiciel.stopCreating();
                        }

                    if(logiciel.isCreating()){
                        if(logiciel.getShapeType() == ShapeType.CIRCLE){
                            logiciel.getShow().showCircle(event.getX(),event.getY(),graphicsContext.getFill());
                        }
                        if(logiciel.getShapeType() == ShapeType.RECTANGLE){
                            logiciel.getShow().showRectangle(event.getX(),event.getY(),graphicsContext.getFill());
                        }
                        if(logiciel.getShapeType() == ShapeType.LINE){
                            logiciel.getShow().showLine(event.getX(),event.getY(),graphicsContext.getFill());
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

            }else if(event.getButton() == MouseButton.SECONDARY){
                menu.show(borderPane,event.getScreenX(),event.getScreenY());
            }

        });

        canvas.setOnMouseMoved(event -> {

            if(logiciel.isCreating()) {

                switch (logiciel.getShapeType()) {

                    case RECTANGLE:

                     /*   if(event.getX() < logiciel.getShow().getRectangle().getX() && event.getY() < logiciel.getShow().getRectangle().getY()){
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getRectangle().getX());
                            logiciel.getShow().getRectangle().setHeight(logiciel.getShow().getRectangle().getY()-event.getY());
                        }else if(event.getX() < logiciel.getShow().getRectangle().getX() && event.getY() > logiciel.getShow().getRectangle().getY()){
                            logiciel.getShow().getRectangle().setWidth(logiciel.getShow().getRectangle().getX()-event.getX());
                            logiciel.getShow().getRectangle().setHeight(event.getY()-logiciel.getShow().getRectangle().getY());
                        }else if(event.getX() > logiciel.getShow().getRectangle().getX() && event.getY() < logiciel.getShow().getRectangle().getY()){
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getRectangle().getX());
                            logiciel.getShow().getRectangle().setHeight(logiciel.getShow().getRectangle().getY()-event.getY());
                        }else if(event.getX() > logiciel.getShow().getRectangle().getX() && event.getY() > logiciel.getShow().getRectangle().getY()){
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getRectangle().getX());
                            logiciel.getShow().getRectangle().setHeight(event.getY()-logiciel.getShow().getRectangle().getY());
                        }else{
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getRectangle().getX());
                            logiciel.getShow().getRectangle().setHeight(event.getY()-logiciel.getShow().getRectangle().getY());
                        }

                      */
                        if(event.getX() < logiciel.getShow().getInitialX() && event.getY() < logiciel.getShow().getInitialY()){
                            logiciel.getShow().getRectangle().setX(event.getX());
                            logiciel.getShow().getRectangle().setY(event.getY());
                            logiciel.getShow().getRectangle().setWidth(logiciel.getShow().getInitialX()-event.getX());
                            logiciel.getShow().getRectangle().setHeight(logiciel.getShow().getInitialY()-event.getY());
                        }else if(event.getX() < logiciel.getShow().getInitialX() && event.getY() > logiciel.getShow().getInitialY()){
                            logiciel.getShow().getRectangle().setX(event.getX());
                            logiciel.getShow().getRectangle().setWidth(logiciel.getShow().getInitialX()-event.getX());
                            logiciel.getShow().getRectangle().setHeight(event.getY()-logiciel.getShow().getInitialY());
                        }else if(event.getX() > logiciel.getShow().getInitialX() && event.getY() < logiciel.getShow().getInitialY()){
                            logiciel.getShow().getRectangle().setY(event.getY());
                            logiciel.getShow().getRectangle().setHeight(logiciel.getShow().getInitialY()-event.getY());
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getInitialX());
                        }else{
                            logiciel.getShow().getRectangle().setWidth(event.getX()-logiciel.getShow().getInitialX());
                            logiciel.getShow().getRectangle().setHeight(event.getY()-logiciel.getShow().getInitialY());
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
        canvas.setOnMouseExited(event -> glass.stopZoom());

        scene.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));
        scene.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            File file = db.getFiles().get(0);
            if(file.isDirectory()) treeBuilder.setDirectory(file); else openImage(file);
        });

        grid.setOnMouseClicked(event -> {

            if(event.getButton() == MouseButton.PRIMARY) {

                switch (logiciel.getShapeType()) {

                    case TRIANGLE:
                        if (logiciel.getShapesPoints().size() == 2) {
                            logiciel.getShapesPoints().add(new Point(event.getX(), event.getY()));
                            logiciel.stopCreating();
                            for (Point point : logiciel.getShapesPoints()) {
                                logiciel.getPoints().add(point);
                            }
                            logiciel.setShapesPoints(new ArrayList<>());
                        } else {
                            logiciel.getShapesPoints().add(new Point(event.getX(), event.getY()));
                        }
                        break;

                    case HEXAGON:
                        if (logiciel.getShapesPoints().size() == 6) {
                            logiciel.getShapesPoints().add(new Point(event.getX(), event.getY()));
                            logiciel.stopCreating();
                            for (Point point : logiciel.getShapesPoints()) {
                                logiciel.getPoints().add(point);
                            }
                            logiciel.setShapesPoints(new ArrayList<>());
                        } else {
                            logiciel.getShapesPoints().add(new Point(event.getX(), event.getY()));
                        }
                        break;

                    default:
                        System.out.println("ee");
                        if (!logiciel.isCreating()) {
                            logiciel.setStartX(event.getX());
                            logiciel.setStartY(event.getY());
                            logiciel.setCreating(true);
                        } else {
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

            }else if(event.getButton() == MouseButton.SECONDARY){
                menu.show(borderPane,event.getScreenX(),event.getScreenY());
            }

        });

        grid.setOnMouseMoved(event -> {
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

        grid.setOnMousePressed(event -> pressing = true);
        grid.setOnMouseReleased(event -> pressing = false);
        grid.setOnMouseDragged(event -> {
            if(pressing && logiciel.getShapeType() == ShapeType.PENCIL){
                graphicsContext.fillRect(event.getX(),event.getY(),5,5);
            }
        });
    }

    private void buildMenu(){
        CheckMenuItem checkMenuItem = new CheckMenuItem("➕ Grilles");
        checkMenuItem.setSelected(false);
        checkMenuItem.setOnAction(event -> grid.setVisible(checkMenuItem.isSelected()));

        CheckMenuItem glass = new CheckMenuItem("🔎 Loupe");
        glass.setSelected(false);
        glass.setOnAction(event -> this.glass.setActivated(glass.isSelected()));

        menu.getItems().addAll(checkMenuItem);
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
        canvas.setWidth(scene.getWidth()-420);
        canvas.setHeight(scene.getHeight()-90);
    }

    public void setTitle(String title){
        logicielBar.setTitle(title);
    }

    public void openImage(File image){

        try {

            Image image1 = new Image(new FileInputStream(image));

            canvas.getGraphicsContext2D().clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            adaptCanvasSize();
            graphicsContext.drawImage(image1, 0, 0);
            canvas.setHeight(image1.getHeight());
            canvas.setWidth(image1.getWidth());
            grid.setCanvasSize(canvas.getWidth(), canvas.getHeight());
            setTitle(image.getName());
            rightBar.unCheckAll();

            imageOpened = image;
        }catch (FileNotFoundException e){}
    }

    public void openNewPaper(){
        logiciel.save(true);
        adaptCanvasSize();
        logicielBar.setTitle("Nouveau projet");
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        canvas.getGraphicsContext2D().fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        canvas.getGraphicsContext2D().setFill(rightBar.getIndicator().getFill());
        grid.setCanvasSize(canvas.getWidth(), canvas.getHeight());
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

    public Grid getGrid() {
        return grid;
    }

    public RightBar getRightBar() {
        return rightBar;
    }
}
