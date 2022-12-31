package sample.logigraphics;

import fr.devthib.databaseapi.Location;
import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.logigraphics.creation.*;
import sample.logigraphics.interfaces.LogicielColors;
import sample.logigraphics.interfaces.LogicielStructure;
import sample.logigraphics.keyboard.KeyShort;
import sample.logigraphics.keyboard.KeyShortEvent;
import fr.devthib.databaseapi.DataBase;
import sample.logigraphics.projects.ProjectChooser;
import sample.logigraphics.stuff.Debug;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Logiciel {

    ArrayList<KeyShort> keyShorts = new ArrayList<>();

    boolean free = false;

    DataBase dataBase = new DataBase(".logiGraphics", Location.APPDATA);

    LogicielStructure logicielStructure;

    Creation creation = Creation.SHAPES;

    ShapeType shapeType = ShapeType.RECTANGLE;

    double pencilSize = 5;

    Line mirrorAxe = new Line(750,0,750,0);
    TextField text = new TextField();

    Line indicator = new Line(0,0,0,0);

    boolean followAxe = false;

    File directoryToSave;

    Show show = new Show();

    boolean treating = false;

    //Faire de LogiGraphics un paint très mathématique : genre créer des tangentes aux cercles etc...
    //Une système pour changer l'hexagone,on demande un nombre de cotés spécifiques et c'est calculer mathématiquement
    //la barre a droite on peut ouvrir différents onglets,un pour la création,un pour les fonctiones etc...
    //dans les paramètres on pourra défnir la taille de base du papier quand on démarre le logiciel
    //dans les paramètres pouvoir choisir entre papier ou transparent (pour faire des trucs sans fond pratique)

    /*
    idées :

        -faire une bordure autour des objets sélectionnés
        -possibilité de ctrl c et v les objets sélectionnés
        -déplacer avec les flèches
        -ctrl + p -> menu config pour changer les couleur
        -quand on passe devant le menu item rectangle,ca ouvre un sous menu et on choisit entre plein ou juste un contour (ez le contour on met le cercle de la meme couleur que le background et la bordure de la couleur souhaitée)
        -une fenetre s'ouvre pour le rgb pour les couleurs
        -pinceau
        -changer la taille du pinceau (affecte aussi l'épaisseur des formes non remplies)

        -pouvoir changer l'orientation du miroir

        Aide : http://remy-manu.no-ip.biz/Java/Tutoriels/JavaFX/PDF/ihm1_fx_08_man.pdf
     */

    /*
    Quand on ajoute une forme :
    -switch de la shape
    -switch du startCreation
    -switch du endCreation
    -setX et setY de la Shape
     */

    Image image;

    ProjectChooser projectChooser = new ProjectChooser();

    Font trebuchet = new Font("Trebuchet MS",20);

    boolean creating = false;

    double currentX;
    double currentY;

    double startX;
    double startY;

    ArrayList<Point> shapesPoints = new ArrayList<>();
    ArrayList<Point> points = new ArrayList<>();

    public Logiciel() {

        checkDataBase();

        logicielStructure = new LogicielStructure(this);

        mirrorAxe.setScaleX(1.2);
        mirrorAxe.setVisible(false);
        mirrorAxe.setEndY(logicielStructure.getCanvas().getHeight());

        indicator.setVisible(false);

        text.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0))));
        text.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.DASHED,new CornerRadii(0),new BorderWidths(2))));
        text.setVisible(false);
        text.setFont(trebuchet);
        text.setText("");

      /*  addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN)), () -> {
            if(shapeCreator.getShapes().size() != 0) {
                if (shapeCreator.getShapes().size() > 1) {

                    if (shapeSelected == shapeCreator.getLastShape()) {
                        shapeSelected = null;
                    }
                    shapeCreator.getShapes().remove(shapeCreator.getShapes().size()-1);
                    getGroup().getChildren().remove(getGroup().getChildren().size()-1);
                    shapeCreator.setLastShape(shapeCreator.getShapes().get(shapeCreator.getShapes().size()-1));

                } else {
                    shapeSelected = null;
                    shapeCreator.removeShape(0);
                    getGroup().getChildren().remove(2);
                    shapeCreator.setLastShape(null);
                }
            }
        });

       */

        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)), () -> save(true));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN)), this::openImage);
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN)), () -> shapeType = ShapeType.PENCIL);
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN)), this::restart);
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.ADD, KeyCombination.CONTROL_DOWN)), () -> {
            if(logicielStructure.getGrid().isVisible())logicielStructure.getGrid().setNumberOfLines(logicielStructure.getGrid().getNumberOfLines()+1);
        });
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.SUBTRACT, KeyCombination.CONTROL_DOWN)), () -> {
            if(logicielStructure.getGrid().isVisible() && logicielStructure.getGrid().getNumberOfLines()-1 != 0)logicielStructure.getGrid().setNumberOfLines(logicielStructure.getGrid().getNumberOfLines()-1);
        });

        getScene().setOnKeyPressed(event -> {

            for (KeyShort keyShort1 : keyShorts) {
                if (keyShort1.getKeyCombination().match(event)) {
                    keyShort1.execute();
                }
            }

            if(creation == Creation.MIRROR) {

                switch (event.getCode()) {

                    case LEFT:
                        mirrorAxe.setStartX(mirrorAxe.getStartX() - 10);
                        mirrorAxe.setEndX(mirrorAxe.getEndX() - 10);
                        break;
                    case RIGHT:
                        mirrorAxe.setStartX(mirrorAxe.getStartX() + 10);
                        mirrorAxe.setEndX(mirrorAxe.getEndX() + 10);
                        break;
                }

            }

        });

    }

    public void addElement(Node node) {
       getGroup().getChildren().add(node);
    }

    public Line getIndicator() {
        return indicator;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Point> getShapesPoints() {
        return shapesPoints;
    }

    public void setShapesPoints(ArrayList<Point> shapesPoints) {
        this.shapesPoints = shapesPoints;
    }

    public Group getGroup() {
        return logicielStructure.getGroup();
    }

    public Stage getStage() {
        return logicielStructure.getStage();
    }

    public Show getShow() {
        return show;
    }

    public Scene getScene() {
        logicielStructure = new LogicielStructure(this);
        return logicielStructure.getScene();
    }

    public double getPencilSize() {
        return pencilSize;
    }

    public void setPencilSize(double pencilSize) {
        this.pencilSize = pencilSize;
    }

    public void start(){
        getStage().show();
    }

    public void stop() {
        getStage().close();
    }

    public void restart(){
        stop();

        Logiciel logiciel = new Logiciel();
        logiciel.start();
    }

    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(double currentY) {
        this.currentY = currentY;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setSelectedColor(Color selectedColor) {
        logicielStructure.getGraphicsContext().setFill(selectedColor);
        logicielStructure.getGraphicsContext().setStroke(selectedColor);
    }

    public boolean isTreating() {
        return treating;
    }

    public void setTreating(boolean treating) {
        this.treating = treating;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    private void addKeyShort(KeyShort keyShort, KeyShortEvent keyShortEvent) {
        keyShort.setOnExecuted(keyShortEvent);
        keyShorts.add(keyShort);
    }

    public boolean isCreating() {
        return creating;
    }

    public boolean isFree() {
        return free;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
        this.shapesPoints = new ArrayList<>();
        creating = false;
        currentY = 0;
        currentX = 0;
        startX = 0;
        startY = 0;
    }

    public void setCreating(boolean creating) {
        this.creating = creating;
    }

    private void checkDataBase(){

        if(!dataBase.exists()){
            dataBase.create();
            dataBase.createDirectories("cache","projects","temporary");
            dataBase.getDirectoryByName("cache").createFile("settings.txt");
        }
        if(!dataBase.containsDirectories("cache","projects","temporary")){
            dataBase.createDirectories("cache","projects");
        }
        if(!dataBase.getDirectoryByName("cache").containsFile("settings.txt")){
            dataBase.getDirectoryByName("cache").createFile("settings.txt");
        }
        if(!dataBase.getDirectoryByName("cache").containsFile("root.txt")){
            dataBase.getDirectoryByName("cache").createFile("root.txt");
            dataBase.getDirectoryByName("cache").saveInFile("root.txt",dataBase.getLocation()+"\\.logiGraphics");
        }
        buildLogo();

    }

    private void buildLogo(){

        if(!dataBase.getDirectoryByName("cache").containsFile("icon.png")){

            Canvas canvas = new Canvas(20,20);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setFill(LogicielColors.getTopBarColor());
            graphicsContext.fillRect(0,0,20,20);
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(5,5,12,12);
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillPolygon(new double[]{0.0,10.0,5.0},new double[]{19.0,19.0,7.0},3);
            graphicsContext.setStroke(Color.BLUE);
            graphicsContext.strokeOval(12,8,10,10);

            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", new File(dataBase.getLocation()+"\\.logiGraphics\\cache\\icon.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(!dataBase.getDirectoryByName("cache").containsFile("graph.png")){
            Canvas canvas = new Canvas(20,20);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(0,0,20,20);
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(2.86,7,2.86,13);
            graphicsContext.setFill(Color.BLUE);
            graphicsContext.fillRect(8.57,15,2.86,5);
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(14.29,11,2.86,9);

            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", new File(dataBase.getLocation()+"\\.logiGraphics\\cache\\graph.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(!dataBase.getDirectoryByName("cache").containsFile("colors.png")){
            Canvas canvas = new Canvas(20,20);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setFill(LogicielColors.getTopBarColor());
            graphicsContext.fillRect(0,0,20,20);
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(0,0,10,10);
            graphicsContext.setFill(Color.BLUE);
            graphicsContext.fillRect(10,0,10,10);
            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(0,10,10,10);
            graphicsContext.setFill(Color.CYAN);
            graphicsContext.fillRect(10,10,10,10);

            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", new File(dataBase.getLocation()+"\\.logiGraphics\\cache\\colors.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void stopCreating(){

        switch(shapeType){

            case RECTANGLE:

                if(currentX < startX && currentY < startY){
                    logicielStructure.getGraphicsContext().fillRect(currentX,currentY,startX-currentX,startY-currentY);
                }else if(currentX < startX && currentY > startY){
                    logicielStructure.getGraphicsContext().fillRect(currentX,startY,startX-currentX,currentY-startY);
                }else if(currentX > startX && currentY < startY){
                    logicielStructure.getGraphicsContext().fillRect(startX,currentY,currentX-startX,startY-currentY);
                }else{
                    logicielStructure.getGraphicsContext().fillRect(startX,startY,currentX-startX,currentY-startY);
                }
                creating = false;
                break;

            case CIRCLE:
                if((currentY-startY) > (currentX-startX)){
                    logicielStructure.getGraphicsContext().fillOval(startX-Math.abs(currentY-startY),startY-Math.abs(currentY-startY),Math.abs(currentY-startY)*2,Math.abs(currentY-startY)*2);
                }else{
                    logicielStructure.getGraphicsContext().fillOval(startX-Math.abs(currentX-startX),startY-Math.abs(currentX-startX),Math.abs(currentX-startX)*2,Math.abs(currentX-startX)*2);
                }
                creating = false;
                break;

            case LINE:
                logicielStructure.getGraphicsContext().strokeLine(startX,startY,currentX,currentY);
                creating = false;
                break;

            case NONFILLEDRECTANGLE:

                if(currentX < startX && currentY < startY){
                    logicielStructure.getGraphicsContext().strokeRect(currentX,currentY,startX-currentX,startY-currentY);
                }else if(currentX < startX && currentY > startY){
                    logicielStructure.getGraphicsContext().strokeRect(currentX,startY,startX-currentX,currentY-startY);
                }else if(currentX > startX && currentY < startY){
                    logicielStructure.getGraphicsContext().strokeRect(startX,currentY,currentX-startX,startY-currentY);
                }else{
                    logicielStructure.getGraphicsContext().strokeRect(startX,startY,currentX-startX,currentY-startY);
                }
                creating = false;
                break;

            case NONFILLEDCIRCLE:
                if((currentY-startY) > (currentX-startX)){
                    logicielStructure.getGraphicsContext().strokeOval(startX-Math.abs(currentY-startY),startY-Math.abs(currentY-startY),Math.abs(currentY-startY)*2,Math.abs(currentY-startY)*2);
                }else{
                    logicielStructure.getGraphicsContext().strokeOval(startX-Math.abs(currentX-startX),startY-Math.abs(currentX-startX),Math.abs(currentX-startX)*2,Math.abs(currentX-startX)*2);
                }
                creating = false;
                break;

            case ELLIPSE:
                logicielStructure.getGraphicsContext().fillOval(startX-Math.abs(currentX-startX),startY-Math.abs(currentY-startY),Math.abs(currentX-startX)*2,Math.abs(currentY-startY)*2);
                creating = false;
                break;

            case TRIANGLE:
                logicielStructure.getGraphicsContext().fillPolygon(new double[]{shapesPoints.get(0).getX(),shapesPoints.get(1).getX(),shapesPoints.get(2).getX()},new double[]{shapesPoints.get(0).getY(),shapesPoints.get(1).getY(),shapesPoints.get(2).getY()},3);
                break;

            case NONFILLEDTRIANGLE:
                logicielStructure.getGraphicsContext().strokePolygon(new double[]{shapesPoints.get(0).getX(),shapesPoints.get(1).getX(),shapesPoints.get(2).getX()},new double[]{shapesPoints.get(0).getY(),shapesPoints.get(1).getY(),shapesPoints.get(2).getY()},3);
                break;

            case HEXAGON:
               logicielStructure.getGraphicsContext().fillPolygon(new double[]{shapesPoints.get(0).getX(),shapesPoints.get(1).getX(),shapesPoints.get(2).getX(),shapesPoints.get(3).getX(),shapesPoints.get(4).getX(),shapesPoints.get(5).getX()},new double[]{shapesPoints.get(0).getY(),shapesPoints.get(1).getY(),shapesPoints.get(2).getY(),shapesPoints.get(3).getY(),shapesPoints.get(4).getY(),shapesPoints.get(5).getY()},6);
                break;

        }
        save(false);
    }

    public void setCreation(Creation creation) {
        this.creation = creation;

        if(creation == Creation.MIRROR){
            mirrorAxe.setVisible(true);
        }
    }

    public Line getMirrorAxe() {
        return mirrorAxe;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Creation getCreation() {
        return creation;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    private void resetText(double x,double y){
        text.setTranslateY(y);
        text.setTranslateX(x);
        text.setVisible(true);
    }

    public LogicielStructure getLogicielStructure() {
        return logicielStructure;
    }

    public void save(boolean volunteer){

        if(directoryToSave == null && volunteer){

            FileChooser savefile = new FileChooser();
            savefile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image (*PNG)", "*.png"));
            savefile.setInitialFileName(logicielStructure.getTitle());
            if(logicielStructure.hasImageOpened()){

                String nam = logicielStructure.getImageOpened().getPath();
                String target = logicielStructure.getImageOpened().getName();
                String name = nam.replace(target,"");

                savefile.setInitialDirectory(new File(name));
            }
            savefile.setTitle("Enregistrer le projet");

            directoryToSave = savefile.showSaveDialog(new Stage());
            if (directoryToSave != null) {
                try {
                    WritableImage writableImage = new WritableImage((int) logicielStructure.getCanvas().getWidth(),(int) logicielStructure.getCanvas().getHeight());
                    logicielStructure.getCanvas().snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage,null);
                    ImageIO.write(renderedImage, "png", directoryToSave);
                    logicielStructure.setTitle(directoryToSave.getName());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }else{
            if(directoryToSave != null) {
                try {

                    WritableImage writableImage = new WritableImage((int) logicielStructure.getCanvas().getWidth(), (int) logicielStructure.getCanvas().getHeight());
                    logicielStructure.getCanvas().snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", directoryToSave);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public void openImage(){

        //fenetre qui demande confirmation

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionnez une image");

        File file = fileChooser.showOpenDialog(new Stage());

        if(file != null){
            String extension = Debug.getExtension(file);
            if(extension.equalsIgnoreCase("jpg"))logicielStructure.openImage(file);
        }

        logicielStructure.getRightBar().unCheckAll();

    }
}
