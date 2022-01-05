package sample.logigraphics;

import fr.devthib.databaseapi.Location;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.logigraphics.creation.*;
import sample.logigraphics.creation.Shape;
import sample.logigraphics.interfaces.TopBar;
import sample.logigraphics.interfaces.TopMenuBar;
import sample.logigraphics.keyboard.KeyShort;
import sample.logigraphics.keyboard.KeyShortEvent;
import fr.devthib.databaseapi.DataBase;
import sample.logigraphics.projects.ProjectChooser;
import sample.logigraphics.themes.Theme;
import sample.logigraphics.windows.ErrorWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Logiciel {

    Group group = new Group();
    Stage stage = new Stage();
    Scene scene = new Scene(group, 1500, 800);

    ShapeCreator shapeCreator = new ShapeCreator(this);

    ArrayList<KeyShort> keyShorts = new ArrayList<>();

    Shape shapeSelected;

    boolean free = false;

    Color backgroundColor = Color.GREY;

    TopBar topBar = new TopBar(this);

    TopMenuBar topMenuBar = new TopMenuBar(this);

    DataBase dataBase = new DataBase(".logiGraphics", Location.APPDATA);

    Creation creation = Creation.SHAPES;

    Line mirrorAxe = new Line(750,135,750,4000);
    boolean followAxe = false;

    Theme theme = new Theme(this);

    /*
    idées :

        -enregistrer le thème dans les settings et le load au lancement du logiciel
        -différents thèmes : light,dark etc...et dev tout un système de création de thèmes
        -faire une bordure autour des objets sélectionnés
        -possibilité de ctrl c et v les objets sélectionnés
        -déplacer avec les flèches
        -zoom avec les touches +  et -
        -ctrl + p -> menu config pour changer les couleur
        -quand on passe devant le menu item rectangle,ca ouvre un sous menu et on choisit entre plein ou juste un contour (ez le contour on met le cercle de la meme couleur que le background et la bordure de la couleur souhaitée)
        -une fenetre s'ouvre pour le rgb pour les couleurs

        -pouvoir changer l'orientation du miroir
        -système de miroir : on doit sélectionner une ligne et tout ce qu'on créer derrière la ligne se reproduit de l'autre coté

    features autres:
        -opti indicateur de couleur

        Aide : http://remy-manu.no-ip.biz/Java/Tutoriels/JavaFX/PDF/ihm1_fx_08_man.pdf
     */

    /*
    Quand on ajoute une forme :
    -switch de la shape
    -switch du startCreation
    -switch du endCreation
    -setX et setY de la Shape
     */

    VBox menus = new VBox();

    ErrorWindow errorWindow = new ErrorWindow();

    Image image;

    ProjectChooser projectChooser = new ProjectChooser();

    public Logiciel() {

        menus.getChildren().addAll(topMenuBar.build(),topBar.build());

        checkDataBase();
        updateTheme();

        mirrorAxe.setScaleX(1.2);
        mirrorAxe.setVisible(false);
        mirrorAxe.setOnMouseClicked(event -> {
            if(!shapeCreator.isCreating()) {
                if (!followAxe) {
                    followAxe = true;
                    scene.setOnMouseMoved(event1 -> {
                        mirrorAxe.setStartX(event1.getX());
                        mirrorAxe.setEndX(event1.getX());
                    });
                } else {
                    followAxe = false;
                    scene.setOnMouseMoved(event1 -> {
                    });
                }
            }
        });


        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD0, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 0));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD1, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 1));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD2, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 2));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD3, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 3));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD4, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 4));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD5, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 5));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD6, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 6));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD7, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 7));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD8, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 8));
        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.NUMPAD9, KeyCombination.CONTROL_DOWN)), () -> changeColor(true, 9));

        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN)), this::unSelectAll);

        addKeyShort(new KeyShort(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN)), () -> {
            if(shapeCreator.getShapes().size() != 0) {
                if (shapeCreator.getShapes().size() > 1) {

                    if (shapeSelected == shapeCreator.getLastShape()) {
                        shapeSelected = null;
                    }
                    group.getChildren().remove(shapeCreator.getShapes().size());
                    shapeCreator.removeShape(shapeCreator.getLastShape());
                    shapeCreator.setLastShape(shapeCreator.getShapes().get(shapeCreator.getShapes().size() - 1));

                } else {
                    shapeSelected = null;
                    shapeCreator.removeShape(0);
                    group.getChildren().remove(1);
                    shapeCreator.setLastShape(null);
                }
            }
        });

        scene.setOnMouseClicked(event -> {

            switch (event.getButton()) {

                case PRIMARY:

                    if (!free) {
                        if (shapeCreator.isCreating()) {
                            shapeCreator.stopCreating();
                        } else {
                            if (event.getY() > 140) {
                                if(shapeCreator.getShapeType() == ShapeType.IMAGE){
                                    try {
                                        shapeCreator.startCreating(event.getX(), event.getY(), image);
                                    }catch (NullPointerException e){
                                        try { image = new Image(new FileInputStream(projectChooser.openAndGetFile())); } catch (FileNotFoundException fileNotFoundException) {}
                                    }
                                }else{ shapeCreator.startCreating(event.getX(), event.getY(),null);}
                            }
                        }
                    }
                    break;

                case SECONDARY:
                    getMenu().show(group, event.getScreenX(), event.getScreenY());
                    break;

            }

        });

        scene.setOnKeyPressed(event -> {

            for (KeyShort keyShort1 : keyShorts) {
                if (keyShort1.getKeyCombination().match(event)) {
                    keyShort1.execute();
                }
            }

            switch (event.getCode()) {

                case RIGHT:
                    if(creation == Creation.MIRROR && !shapeCreator.isCreating()){
                        mirrorAxe.setEndX(mirrorAxe.getEndX()+10);
                        mirrorAxe.setStartX(mirrorAxe.getStartX()+10);
                    }else{
                        if (hasShapeSelected()) shapeSelected.setX(shapeSelected.getX() + 10);
                    }
                    break;

                case LEFT:
                    if(creation == Creation.MIRROR && !shapeCreator.isCreating()){
                        mirrorAxe.setEndX(mirrorAxe.getEndX()-10);
                        mirrorAxe.setStartX(mirrorAxe.getStartX()-10);
                    }else{
                        if (hasShapeSelected()) shapeSelected.setX(shapeSelected.getX() - 10);
                    }
                    break;

                case UP:
                    if (hasShapeSelected()) shapeSelected.setY(shapeSelected.getY() - 10);
                    break;

                case DOWN:
                    if (hasShapeSelected()) shapeSelected.setY(shapeSelected.getY() + 10);
                    break;

                case ADD:
                    for(int i = 2; i < group.getChildren().size(); i++){
                        Node node = group.getChildren().get(i);
                        node.setScaleX(node.getScaleX()+0.1);
                        node.setScaleY(node.getScaleY()+0.1);
                    }
                    break;

                case SUBTRACT:
                    for(int i = 2; i < group.getChildren().size(); i++){
                        Node node = group.getChildren().get(i);
                        if(node.getScaleX() > 0.11) node.setScaleX(node.getScaleX()-0.1);
                        if(node.getScaleY() > 0.11)node.setScaleY(node.getScaleY()-0.1);
                    }

                    break;

            }

        });

        group.getChildren().addAll(menus,mirrorAxe);
    }

    public void addElement(Node node) {
        group.getChildren().add(node);
    }

    public Group getGroup() {
        return group;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void start() {
        scene.setFill(backgroundColor);

        stage.setTitle("LogiGraphics");
        stage.setScene(scene);
        stage.show();
    }

    public void stop() {
        stage.close();
    }

    public void setShapeSelected(Shape shapeSelected) {
        this.shapeSelected = shapeSelected;
        for (Shape shape : shapeCreator.getShapes()) {
            if (shape != shapeSelected) {
                shape.unSelect();
            }
        }
    }

    private void addKeyShort(KeyShort keyShort, KeyShortEvent keyShortEvent) {
        keyShort.setOnExecuted(keyShortEvent);
        keyShorts.add(keyShort);
    }

    public ContextMenu getMenu() {

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
                errorWindow.setContent("Image non trouvée !");
                errorWindow.show();
            }

        });

        menu.getItems().addAll(fre,rectangle,unFilledRectangle,circle,nonFilledCircle,ellipse,line,image);

        return menu;
    }

    public boolean isFree() {
        return free;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        scene.setFill(backgroundColor);
    }

    public void changeColor(boolean background, int code) {

        switch (code) {

            case 0:
                if (background) setBackgroundColor(Color.WHITE);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.WHITE);
                    }
                    shapeCreator.setSelectedColor(Color.WHITE);
                }

                break;
            case 1:
                if (background) setBackgroundColor(Color.BLACK);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.BLACK);
                    }
                    shapeCreator.setSelectedColor(Color.BLACK);
                }
                break;
            case 2:
                if (background) setBackgroundColor(Color.RED);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.RED);
                    }
                    shapeCreator.setSelectedColor(Color.RED);
                }
                break;
            case 3:
                if (background) setBackgroundColor(Color.BLUE);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.BLUE);
                    }
                    shapeCreator.setSelectedColor(Color.BLUE);
                }
                break;
            case 4:
                if (background) setBackgroundColor(Color.ORANGE);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.ORANGE);
                    }
                    shapeCreator.setSelectedColor(Color.ORANGE);
                }
                break;
            case 5:
                if (background) setBackgroundColor(Color.PURPLE);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.PURPLE);
                    }
                    shapeCreator.setSelectedColor(Color.PURPLE);
                }
                break;
            case 6:
                if (background) setBackgroundColor(Color.GREEN);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.GREEN);
                    }
                    shapeCreator.setSelectedColor(Color.GREEN);
                }
                break;
            case 7:
                if (background) setBackgroundColor(Color.GREY);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.GREY);
                    }
                    shapeCreator.setSelectedColor(Color.GREY);
                }
                break;
            case 8:
                if (background) setBackgroundColor(Color.BROWN);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.BROWN);
                    }
                    shapeCreator.setSelectedColor(Color.BROWN);
                }
                break;
            case 9:
                if (background) setBackgroundColor(Color.YELLOW);
                else {
                    if(shapeSelected != null){
                        shapeSelected.changeColor(Color.YELLOW);
                    }
                    shapeCreator.setSelectedColor(Color.YELLOW);
                }
                break;
        }

    }

    private void unSelectAll() {

        for (Shape shape : shapeCreator.getShapes()) {
            shape.unSelect();
        }
        this.shapeSelected = null;
    }

    public ShapeCreator getShapeCreator() {
        return shapeCreator;
    }

    public boolean hasShapeSelected() {
        return shapeSelected != null;
    }

    public TopBar getTopBar() {
        return topBar;
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

    public TopMenuBar getTopMenuBar() {
        return topMenuBar;
    }

    public Theme getTheme() {
        return theme;
    }

    public VBox getMenus() {
        return menus;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void changeThemeInDatabase(String theme){
       dataBase.getDirectoryByName("cache").removeInFile(dataBase.getDirectoryByName("cache").getFileByName("theme.txt"), dataBase.getDirectoryByName("cache").readLineFile("theme.txt",0));
       dataBase.getDirectoryByName("cache").saveInFile("theme.txt",theme);
    }

    private void updateTheme(){

        String[] values = dataBase.getDirectoryByName("cache").readLineFile("theme.txt",0).split("/");

        switch (values[0]){

            case "dark":
                theme.setCustomColors(Color.rgb(64,64,64), Color.rgb(96,96,96));
                theme.apply();
                break;

            case "light":
                theme.setCustomColors(Color.rgb(230,230,230),Color.rgb(216,216,216));
                theme.apply();
                break;

            case "custom":
                theme.setCustomColors(theme.getCustomThemes().get(Integer.parseInt(values[1])).getColor1(),theme.getCustomThemes().get(Integer.parseInt(values[1])).getColor2());
                theme.apply();
                break;
        }












    }
}
