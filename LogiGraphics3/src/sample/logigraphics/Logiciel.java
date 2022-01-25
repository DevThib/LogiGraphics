package sample.logigraphics;

import fr.devthib.databaseapi.Location;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.logigraphics.creation.*;
import sample.logigraphics.interfaces.LogicielStructure;
import sample.logigraphics.keyboard.KeyShort;
import sample.logigraphics.keyboard.KeyShortEvent;
import fr.devthib.databaseapi.DataBase;
import sample.logigraphics.projects.ProjectChooser;
import sample.logigraphics.stuff.Debug;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
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

    Line mirrorAxe = new Line(750,0,750,0);
    TextField text = new TextField();

    boolean followAxe = false;

    File directoryToSave;

    //Pour le truc a gauche,un tree de dossiers comme intellij pour ouvrir les projets
    //Faire de LogiGraphics un paint très mathématique : genre créer des tangentes aux cercles etc...
    //Une système pour changer l'hexagone,on demande un nombre de cotés spécifiques et c'est calculer mathématiquement
    //la barre a droite on peut ouvrir différents onglets,un pour la création,un pour les fonctiones etc...
    //dans les paramètres on pourra défnir la taille de base du papier quand on démarre le logiciel

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

    ArrayList<Double> xPoints = new ArrayList<>();
    ArrayList<Double> yPoints = new ArrayList<>();

    public Logiciel() {

        checkDataBase();

        logicielStructure = new LogicielStructure(this);

     /*   mirrorAxe.setScaleX(1.2);
        mirrorAxe.setVisible(false);
        mirrorAxe.setEndY(project.getDrawablePaper().getSurface().getHeight());
        mirrorAxe.setOnMouseClicked(event -> {
            if(!shapeCreator.isCreating()) {
                if (!followAxe) {
                    followAxe = true;
                    getScene().setOnMouseMoved(event1 -> {
                        mirrorAxe.setStartX(event1.getX());
                        mirrorAxe.setEndX(event1.getX());
                    });
                } else {
                    followAxe = false;
                    getScene().setOnMouseMoved(event1 -> {});
                }
            }
        });
        mirrorAxe.setTranslateY(135);

      */

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

        getScene().setOnKeyPressed(event -> {

            for (KeyShort keyShort1 : keyShorts) {
                if (keyShort1.getKeyCombination().match(event)) {
                    keyShort1.execute();
                }
            }

        });
    }

    public void addElement(Node node) {
       getGroup().getChildren().add(node);
    }

    public ArrayList<Double> getxPoints() {
        return xPoints;
    }

    public ArrayList<Double> getyPoints() {
        return yPoints;
    }

    public void setxPoints(ArrayList<Double> xPoints) {
        this.xPoints = xPoints;
    }

    public void setyPoints(ArrayList<Double> yPoints) {
        this.yPoints = yPoints;
    }

    public Group getGroup() {
        return logicielStructure.getGroup();
    }

    public Stage getStage() {
        return logicielStructure.getStage();
    }

    public Scene getScene() {
        logicielStructure = new LogicielStructure(this);
        return logicielStructure.getScene();
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

    /*  public ContextMenu getMenu() {

        ContextMenu menu = new ContextMenu();

        MenuItem fre = new MenuItem("🖱 Libre");
        fre.setOnAction(event -> {
            free = true;
            shapeCreator.stopCreating();
        });

        MenuItem rectangle = new MenuItem("🟫 Rectangle");
        rectangle.setOnAction(event -> shapeCreator.setShapeType(ShapeType.RECTANGLE));

        MenuItem unFilledRectangle = new MenuItem("🔳 Rectangle creux");
        unFilledRectangle.setOnAction(event -> shapeCreator.setShapeType(ShapeType.NONFILLEDRECTANGLE));


        MenuItem circle = new MenuItem("⭕ Cercle");
        circle.setOnAction(event -> shapeCreator.setShapeType(ShapeType.CIRCLE));

        MenuItem nonFilledCircle = new MenuItem("⭕ Cercle creux");
        nonFilledCircle.setOnAction(event -> shapeCreator.setShapeType(ShapeType.NONFILLEDCIRCLE));

        MenuItem ellipse = new MenuItem("🕳 Ellipse");
        ellipse.setOnAction(event -> shapeCreator.setShapeType(ShapeType.ELLIPSE));


        MenuItem line = new MenuItem("➖ Ligne");
        line.setOnAction(event -> shapeCreator.setShapeType(ShapeType.LINE));

        MenuItem image = new MenuItem("🖼 Image");
        image.setOnAction(event -> {
            try {
               this.image = new Image(new FileInputStream(projectChooser.openAndGetFile()));
               shapeCreator.setShapeType(ShapeType.IMAGE);
            } catch (NullPointerException | FileNotFoundException e) {

            }

        });

        MenuItem txt = new MenuItem("🔢 Texte");
        txt.setOnAction(event -> shapeCreator.setShapeType(ShapeType.TEXT));

        menu.getItems().addAll(fre,rectangle,unFilledRectangle,circle,nonFilledCircle,ellipse,line,image,txt);

        return menu;
    }

    */

    public boolean isFree() {
        return free;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
        this.xPoints = new ArrayList<>();
        this.yPoints = new ArrayList<>();
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
            dataBase.createDirectories("cache","projects","themes");
            dataBase.getDirectoryByName("cache").createFile("settings.txt");
            dataBase.getDirectoryByName("cache").createFile("themes.txt");
        }
        if(!dataBase.containsDirectories("cache","projects","themes")){
            dataBase.createDirectories("cache","projects","themes");
        }
        if(!dataBase.getDirectoryByName("cache").containsFile("settings.txt")){
            dataBase.getDirectoryByName("cache").createFile("settings.txt");
        }
        if(!dataBase.getDirectoryByName("cache").containsFile("theme.txt")){
            dataBase.getDirectoryByName("cache").createFile("theme.txt");
            dataBase.getDirectoryByName("cache").saveInFile("theme.txt","light/none");
        }
        if(!dataBase.getDirectoryByName("cache").containsFile("root.txt")){
            dataBase.getDirectoryByName("cache").createFile("root.txt");
            dataBase.getDirectoryByName("cache").saveInFile("root.txt",dataBase.getLocation()+"\\.logiGraphics");
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
                logicielStructure.getGraphicsContext().fillPolygon(new double[]{xPoints.get(0),xPoints.get(1),xPoints.get(2)},new double[]{yPoints.get(0),yPoints.get(1),yPoints.get(2)},3);
                break;

            case HEXAGON:
               logicielStructure.getGraphicsContext().fillPolygon(new double[]{xPoints.get(0),xPoints.get(1),xPoints.get(2),xPoints.get(3),xPoints.get(4),xPoints.get(5)},new double[]{yPoints.get(0),yPoints.get(1),yPoints.get(2),yPoints.get(3),yPoints.get(4),yPoints.get(5)},6);
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

    public void changeThemeInDatabase(String theme){
       dataBase.getDirectoryByName("cache").removeInFile(dataBase.getDirectoryByName("cache").getFileByName("theme.txt"), dataBase.getDirectoryByName("cache").readLineFile("theme.txt",0));
       dataBase.getDirectoryByName("cache").saveInFile("theme.txt",theme);
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
            savefile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image (*JPG)", "*.jpg"));
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
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
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

    }
}
