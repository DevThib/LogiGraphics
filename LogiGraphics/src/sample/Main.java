package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    public static Stage primary;

    public static RectangleCreator rectangleCreator = new RectangleCreator();
    public static CircleCreator circleCreator = new CircleCreator();
    public static LineCreator lineCreator = new LineCreator();
    public static PolyLineCreator polyLineCreator = new PolyLineCreator();

    public static Group group = new Group();
    Scene scene = new Scene(group,1300,900, Color.GREY);

    public static boolean isOnAction = false;
    public static Node nodeAction = null;

    double moveX = 0;
    double moveY = 0;

    public static int selector = 1;

    Circle circle;
    Rectangle rectangle;
    Line line;
    Polyline pline;

    public static int numberOfRectangle = 0;
    public static int numberOfCircle = 0;
     public static int numberOfLine = 0;
    public static int numberOfPolyLine = 0;

    Line indicator = new Line();

    public static File workspace;

    MenuBar bar = new MenuBar();

    public static File superpose;

    public static boolean rVis = true;
    public static boolean rCi = true;
    public static boolean rLi = true;
    public static boolean mLi = true;
    public static boolean rSh = true;

    public static Label icon = new Label();

    Label positions = new Label(" X : 0\nY : 0");
    /* selector :

    1 = rectangle
    2 = cercle
    3 = ligne
    4 = polyline
    5 = libre

     */

    AllMenus menus = new AllMenus();

   public static WindowError error = new WindowError();

   public static ProjectSaver saver = new ProjectSaver();

   public static ProjectLoader loader = new ProjectLoader();

    @Override
    public void start(Stage primaryStage){

        try {

            bar = menus.getBar();

            Stage choose = new Stage();
            FlowPane e = new FlowPane();
            Scene sce = new Scene(e, 600, 450);

            VBox b1 = new VBox();
            VBox b2 = new VBox();

            Label lab = new Label("Créer un projet");
            lab.setFont(new Font("Trebuchet MS", 30));

            Label label2 = new Label("Ouvrir un projet");
            label2.setFont(new Font("Trebuchet MS", 30));

            Button create = new Button("Créer");
            Button open = new Button("Ouvrir");

            create.setFont(new Font("Trebuchet MS", 25));
            open.setFont(new Font("Trebuchet MS", 25));

            b1.getChildren().addAll(lab, create);
            b2.getChildren().addAll(label2, open);

            b1.setAlignment(Pos.CENTER);
            b2.setAlignment(Pos.CENTER);
            b1.setPadding(new Insets(8));
            b2.setPadding(new Insets(8));

            e.setAlignment(Pos.CENTER);
            e.getChildren().addAll(b1, b2);

            choose.setScene(sce);
            choose.setTitle("Projet");
            choose.show();

            positions.setTranslateY(30);

            create.setOnAction(event -> {

                choose.close();

                try {

                    File folder = new File(System.getProperty("user.dir"),"projets LogiGraphics");

                    if(!folder.exists()){
                       folder.mkdir();
                    }

                    int number = 1;
                    File file = new File(System.getProperty("user.dir"), "projets LogiGraphics\\projet" + number + ".txt");
                    while (file.exists()) {
                        number++;
                        file = new File(System.getProperty("user.dir"), "projets LogiGraphics\\projet" + number + ".txt");
                    }
                    file.createNewFile();

                    workspace = file;

                    startAll(primaryStage);

                } catch (IOException e1) {
                    error.showError();
                    primaryStage.close();
                }

            });
            open.setOnAction(event -> {

                try {

                    FileChooser chooser = new FileChooser();
                    workspace = chooser.showOpenDialog(primaryStage);
                    startAll(primaryStage);
                    choose.close();

                } catch (NullPointerException e12) {
                    choose.close();
                }

            });

        }catch (NullPointerException e){
            error.showError();
            primaryStage.close();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void startAll(Stage primaryStage){
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if(selector == 4){

                    if(pline == null){

                        polyLineCreator.createPolyLine();
                        menus.updateElements();

                        pline = polyLineCreator.getAction();
                        pline.setId("pl"+numberOfPolyLine);
                        pline.getPoints().addAll(event.getX(),event.getY());
                        group.getChildren().add(pline);
                        save();
                    }else {
                        pline.getPoints().addAll(event.getX(), event.getY());
                        save();
                    }

                }else {


                    if (!isOnAction) {
                        if (selector != 5) {
                            isOnAction = true;
                        }

                        if (selector == 1) {

                            rectangleCreator.createRectangle();
                            menus.updateElements();
                            rectangle = rectangleCreator.getAction();
                            rectangle.setX(event.getX());
                            rectangle.setY(event.getY());
                            rectangle.setOpacity(0.3);

                            nodeAction = rectangle;
                            group.getChildren().add(rectangle);
                        }

                        if (selector == 2) {

                            circleCreator.createCircle();
                            menus.updateElements();
                            circle = circleCreator.getAction();

                            circle.setOpacity(0.3);
                            circle.setCenterX(event.getX());
                            circle.setCenterY(event.getY());
                            circle.setRadius(15);

                            nodeAction = circle;
                            group.getChildren().add(circle);
                        }

                        if (selector == 3) {

                            lineCreator.createLine();
                            menus.updateElements();
                            line = lineCreator.getAction();

                            line.setOpacity(0.3);
                            line.setStartX(event.getX());
                            line.setStartY(event.getY());
                            line.setEndY(event.getX());
                            line.setEndY(event.getY());
                            nodeAction = line;
                            group.getChildren().add(line);
                        }


                    } else {

                        if (nodeAction.getTypeSelector().equalsIgnoreCase("Circle")) {
                            Circle c = (Circle) nodeAction;
                            c.setId("c" + numberOfCircle);
                            numberOfCircle++;
                            c.setOpacity(1);
                            nodeAction = c;
                        }
                        if (nodeAction.getTypeSelector().equalsIgnoreCase("Rectangle")) {
                            Rectangle c = (Rectangle) nodeAction;
                            c.setId("r" + numberOfRectangle);
                            numberOfRectangle++;
                            c.setOpacity(1);
                            nodeAction = c;
                        }
                        if (nodeAction.getTypeSelector().equalsIgnoreCase("Line")) {
                            Line c = (Line) nodeAction;
                            c.setId("l" + numberOfLine);
                            numberOfLine++;
                            c.setOpacity(1);
                            nodeAction = c;
                        }

                        isOnAction = false;
                        nodeAction = null;
                        moveY = 0;
                        moveX = 0;
                        save();
                    }
                }

            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                positions.setText("X : "+event.getX()+"\n Y : "+event.getY());

                if(isOnAction){

                    if(selector == 1){

                        Rectangle circle1 = (Rectangle) nodeAction;
                        circle1.setWidth(event.getX()-circle1.getX());
                        circle1.setHeight(event.getY()-circle1.getY());
                        rectangle = circle1;

                    }

                    if(selector == 2){

                        moveY = event.getY();
                        moveX = event.getX();
                        Circle circle1 = (Circle) nodeAction;
                        circle1.setRadius(event.getX()-circle1.getCenterX());
                        circle = circle1;

                    }
                    if(selector == 3){
                        line.setEndX(event.getX());
                        line.setEndY(event.getY());
                    }

                }
                analyse(event.getY(), event.getX());
            }
        });

        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if(event.getCharacter().equalsIgnoreCase("p")){
                    selector++;
                    if(selector > 5){
                        selector = 1;
                    }
                    if(Main.isOnAction){
                        Main.group.getChildren().remove(Main.group.getChildren().size()-1);
                        Main.isOnAction = false;
                        Main.nodeAction = null;
                        Main.selector = 1;
                    }
                    affChange();
                }

                if(event.getCharacter().equalsIgnoreCase("z")){
                    moveY(true);
                }

                if(event.getCharacter().equalsIgnoreCase("s")){
                    moveY(false);
                }

                if(event.getCharacter().equalsIgnoreCase("d")){
                    moveX(true);
                }

                if(event.getCharacter().equalsIgnoreCase("q")){
                    moveX(false);
                }

            }
        });

        icon.setText("⬛");
        icon.setTranslateY(60);
        icon.setFont(new Font("Trebuchet MS",50));

        group.getChildren().addAll(bar,indicator,positions,icon);

        openProject(workspace);

        char[] na = workspace.getName().toCharArray();
        String name = "";
        for(int i = 0; i < na.length-4;i++){
            name += na[i];
        }

        primaryStage.setTitle("LogiGraphics - "+name);
        primaryStage.setScene(scene);
        primaryStage.show();
        Tip tip = new Tip();
        tip.showTip();
        primary = primaryStage;
    }

    public void analyse(double posY,double posX){

        //créer une ligne entre la souris et le centre du cercle

        for(int i = 3; i < group.getChildren().size(); i++){
            if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")){
                Circle circle = (Circle) group.getChildren().get(i);
                if(circle.getCenterX() > posX-3 && circle.getCenterX() < posX+3 || circle.getCenterY() > posY-3 && circle.getCenterY() < posY+3){
                    indicator.setOpacity(0.5);
                    indicator.setVisible(true);
                    indicator.setStartX(circle.getCenterX());
                    indicator.setStartY(circle.getCenterY());
                    indicator.setEndY(posY);
                    indicator.setEndX(posX);
                }else{
                    indicator.setVisible(false);
                }
            }
            if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Rectangle")){
                Rectangle circle = (Rectangle) group.getChildren().get(i);
                if(circle.getX() == posX || circle.getY() == posY){
                    indicator.setOpacity(0.5);
                    indicator.setVisible(true);
                    indicator.setStartX(circle.getX());
                    indicator.setStartY(circle.getY());
                    indicator.setEndY(posY);
                    indicator.setEndX(posX);
                }else {
                    indicator.setVisible(false);
                }
            }
            if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Line")){
                Line circle = (Line) group.getChildren().get(i);
                if(circle.getStartX() == posX || circle.getStartY() == posY){
                    indicator.setOpacity(0.5);
                    indicator.setVisible(true);
                    indicator.setStartX(circle.getStartX());
                    indicator.setStartY(circle.getStartY());
                    indicator.setEndY(posY);
                    indicator.setEndX(posX);
                }else if(circle.getEndX() == posX || circle.getEndY() == posY){
                    indicator.setOpacity(0.5);
                    indicator.setVisible(true);
                    indicator.setStartX(circle.getEndX());
                    indicator.setStartY(circle.getEndY());
                    indicator.setEndY(posY);
                    indicator.setEndX(posX);
                }
            }
        }


    }

    public void moveY(boolean up){
        if(up) {
            for (int i = 4; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateY(group.getChildren().get(i).getTranslateY()-10);
            }
        }else{
            for (int i = 4; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateY(group.getChildren().get(i).getTranslateY()+10);
            }
        }
    }

    public void moveX(boolean right){
        if(!right) {
            for (int i = 4; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateX(group.getChildren().get(i).getTranslateX()-10);
            }
        }else{
            for (int i = 4; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateX(group.getChildren().get(i).getTranslateX()+10);
            }
        }
    }

    public static void back(){
        if(group.getChildren().size() > 4) {
            group.getChildren().remove(group.getChildren().size() - 1);
        }
        save();
    }

    public static void openProject(File fileToOpen){loader.loadProject(fileToOpen);}

    public static void save(){saver.saveProject();}

    public static Node search(String id){
        for(int i = 4; i < group.getChildren().size(); i++){
            if(group.getChildren().get(i).getId() != null && group.getChildren().get(i).getId().equalsIgnoreCase(id)){
                return group.getChildren().get(i);
            }
        }
        return null;
    }

    public static void setVisibilty(boolean visible, String type){

        String id1 = "";
        String id2 = "";

        try {

            FileReader fr = new FileReader(workspace);
            BufferedReader br = new BufferedReader(fr);

            String all = "";
            String str = "";
            while(str != null){
                str = br.readLine();
                if(str != null){
                    all += str;
                }
            }

            String[] values = all.split(":");

            for(int i = 0; i < values.length; i++){
                String[] properties = values[i].split("/");
                if(properties[0].equalsIgnoreCase("Shape")){

                    if(properties[1].equalsIgnoreCase("substract")){
                        id1 = properties[2];
                        id2 = properties[3];
                    }

                }
            }

        }catch (IOException e){
            System.out.println("error");
        }

        for(int i = 2; i < group.getChildren().size(); i++){
            if(id1.equalsIgnoreCase(group.getChildren().get(i).getId()) || id2.equalsIgnoreCase(group.getChildren().get(i).getId())){
                group.getChildren().get(i).setVisible(false);
            }else {
                if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase(type)) {
                    group.getChildren().get(i).setVisible(visible);
                }
            }
        }
    }

    public static void affChange(){
        if(selector == 1){
            icon.setText("⬛");
            icon.setTranslateY(60);
        }
        if(selector == 2){
            icon.setText("⭕");
            icon.setTranslateY(60);
        }
        if(selector == 3){
            icon.setText("➖");
            icon.setTranslateY(50);
        }
        if(selector == 4){
            icon.setText("〰");
            icon.setTranslateY(50);
        }
        if(selector == 5){
            icon.setText("🖱");
            icon.setTranslateY(60);
        }
    }

    }


