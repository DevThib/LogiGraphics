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

    /* selector :

    1 = rectangle
    2 = cercle
    3 = ligne
    4 = polyline
    5 = libre

     */

         /*
        A DEV :

        -pouvoir changer la rotation, l'id et la rotation des objets dans les paramètres des objets et pouvoir faire des copies d'un autre objet (on met les mêmes width et height d'un autre)
        -pouvoir avoir la liste des éléments du projet
        -bouton paramètres
        -le 3d (ROMAINPC) vidéo dessus
        -mettre un icone en haut qui indique ce qu'on a choisi (rectangle,cercle,libre,gomme etc...)
         */

    @Override
    public void start(Stage primaryStage){

        try {

            AllMenus menus = new AllMenus();
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

            create.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

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

                    } catch (IOException e) {
                        System.out.println("error");
                    }

                }
            });
            open.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    try {

                        FileChooser chooser = new FileChooser();
                        workspace = chooser.showOpenDialog(primaryStage);
                        startAll(primaryStage);
                        choose.close();

                    } catch (NullPointerException e) {
                        choose.close();
                    }

                }
            });

        }catch (NullPointerException e){
            Stage stage = new Stage();
            VBox box = new VBox();
            Scene scene = new Scene(box,600,450);

            Label label = new Label("Une erreur s'est produite,LogiGraphics va se relancer");

            start(new Stage());

            box.getChildren().addAll(label);

            stage.setScene(scene);
            primaryStage.close();
            stage.show();

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
                        pline = new Polyline();
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

                            rectangle = new Rectangle();

                            rectangle.setOpacity(0.3);
                            rectangle.setY(event.getY());
                            rectangle.setX(event.getX());
                            rectangle.setWidth(6);
                            rectangle.setHeight(6);
                            nodeAction = rectangle;
                            group.getChildren().add(rectangle);
                        }

                        if (selector == 2) {
                            circle = new Circle();

                            circle.setOpacity(0.3);
                            circle.setCenterX(event.getX());
                            circle.setCenterY(event.getY());
                            circle.setRadius(15);
                            nodeAction = circle;
                            group.getChildren().add(circle);
                        }

                        if (selector == 3) {
                            line = new Line();

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
                    }
                    Main.isOnAction = false;
                    Main.nodeAction = null;
                    Main.selector = 1;
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
        icon.setTranslateY(25);
        icon.setFont(new Font("Trebuchet MS",50));

        group.getChildren().addAll(bar,indicator,icon);

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
            for (int i = 3; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateY(group.getChildren().get(i).getTranslateY()-10);
            }
        }else{
            for (int i = 3; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateY(group.getChildren().get(i).getTranslateY()+10);
            }
        }
    }

    public void moveX(boolean right){
        if(!right) {
            for (int i = 3; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateX(group.getChildren().get(i).getTranslateX()-10);
            }
        }else{
            for (int i = 3; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateX(group.getChildren().get(i).getTranslateX()+10);
            }
        }
    }

    public static void back(){
        if(group.getChildren().size() > 3) {
            group.getChildren().remove(group.getChildren().size() - 1);
        }
        save();
    }

    public static void openProject(File fileToOpen){

        try {

            FileReader fr = new FileReader(fileToOpen);
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
                if(properties[0].equalsIgnoreCase("Rectangle")){

                    double posX = Double.parseDouble(properties[1]);
                    double posY = Double.parseDouble(properties[2]);
                    String[] pr = properties[3].split(",");

                    Rectangle r = new Rectangle();
                    r.setY(posY);
                    r.setX(posX);
                    r.setWidth(Double.parseDouble(pr[0]));
                    r.setHeight(Double.parseDouble(pr[1]));
                    r.setId(properties[4]);
                    group.getChildren().add(r);
                    numberOfRectangle++;

                }
                if(properties[0].equalsIgnoreCase("Circle")){

                    double posX = Double.parseDouble(properties[1]);
                    double posY = Double.parseDouble(properties[2]);
                    double radius = Double.parseDouble(properties[3]);

                    Circle c = new Circle();
                    c.setCenterY(posY);
                    c.setCenterX(posX);
                    c.setRadius(radius);
                    c.setId(properties[4]);
                    group.getChildren().add(c);
                    numberOfCircle++;

                }
                if(properties[0].equalsIgnoreCase("Line")){

                    String[] start = properties[1].split(",");
                    String[] end = properties[2].split(",");

                    Line l = new Line();
                    l.setStartX(Double.parseDouble(start[0]));
                    l.setStartY(Double.parseDouble(start[1]));
                    l.setEndX(Double.parseDouble(end[0]));
                    l.setEndY(Double.parseDouble(end[1]));
                    l.setId(properties[3]);
                    group.getChildren().add(l);
                    numberOfLine++;

                }
                if(properties[0].equalsIgnoreCase("Polyline")){

                    String[] val = properties[1].split(",");

                    Polyline l = new Polyline();

                    for(int a = 0; a < val.length; a++){
                        l.getPoints().addAll(Double.valueOf(val[a]));
                    }

                    l.setId(properties[2]);

                    group.getChildren().add(l);
                    numberOfPolyLine++;

                }

            }
            for(int i = 0; i < values.length; i++){
                String[] properties = values[i].split("/");
                if(properties[0].equalsIgnoreCase("Shape")){

                    if(properties[1].equalsIgnoreCase("substract")){
                        Shape shape1 = (Shape) search(properties[2]);
                        Shape shape2 = (Shape) search(properties[3]);
                        Shape shape = Shape.subtract(shape1,shape2);

                        for(int a = 0; a < group.getChildren().size(); a++){
                            if(properties[2].equalsIgnoreCase(group.getChildren().get(a).getId()) || properties[3].equalsIgnoreCase(group.getChildren().get(a).getId())){
                                group.getChildren().get(a).setVisible(false);
                            }
                        }
                        group.getChildren().addAll(shape);
                    }

                }
            }


        }catch (IOException e){
            System.out.println("error");
        }


    }

    /*
    C'est sous la forme :

    -Type
    -posX
    -PosY
    (pour les rectangles,width et height mais pour les cercles uniquement le Radius)
    -id

     */


    public static void save(){

        try {

            FileWriter fw = new FileWriter(workspace);
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i = 3; i < group.getChildren().size();i++){

                System.out.println(group.getChildren().get(i).getTypeSelector());

                bw.newLine();
                bw.write(":");
                bw.newLine();

                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Rectangle")){
                    Rectangle circle = (Rectangle) group.getChildren().get(i);
                    bw.write("Rectangle/");
                    bw.newLine();
                    bw.write(String.valueOf(circle.getX()+"/"));
                    bw.newLine();
                    bw.write(String.valueOf(circle.getY()+"/"));
                    bw.newLine();
                    bw.write(circle.getWidth() + "," + circle.getHeight()+"/");
                    bw.newLine();
                    if(circle.getId() == null){
                        bw.write("null");
                    }else{
                        bw.write(circle.getId());
                    }

                }
                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Line")){
                    Line circle = (Line) group.getChildren().get(i);
                    bw.write("Line/");
                    bw.newLine();
                    bw.write(circle.getStartX()+","+circle.getStartY()+"/");
                    bw.newLine();
                    bw.write(circle.getEndX()+","+circle.getEndY()+"/");
                    bw.newLine();
                    if(circle.getId() == null){
                        bw.write("null");
                    }else{
                        bw.write(circle.getId());
                    }

                }
                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")) {
                    Circle circle = (Circle) group.getChildren().get(i);
                    bw.write("Circle/");
                    bw.newLine();
                    bw.write(String.valueOf(circle.getCenterX()+"/"));
                    bw.newLine();
                    bw.write(String.valueOf(circle.getCenterY()+"/"));
                    bw.newLine();
                    bw.write(String.valueOf(circle.getRadius())+"/");
                    bw.newLine();
                    if(circle.getId() == null){
                        bw.write("null");
                    }else{
                        bw.write(circle.getId());
                    }

                }
                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Path")){

                    String[] types = group.getChildren().get(i).getId().split(",");

                    bw.write("Shape/");
                    bw.newLine();
                    bw.write(types[0]+"/");
                    bw.newLine();
                    bw.write(types[1]+"/");
                    bw.newLine();
                    bw.write(types[2]+"/");
                    bw.newLine();
                    bw.write(":");

                }
                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Polyline")){

                    Polyline line = (Polyline) group.getChildren().get(i);

                    String d = String.valueOf(line.getPoints());
                    char[] dd = d.toCharArray();
                    String toWrite = "";
                    for(int a = 1; a < dd.length-1; a++){
                        toWrite += dd[a];
                    }

                    bw.write("PolyLine/");
                    bw.newLine();
                    bw.write(toWrite+"/");
                    System.out.println(line.getPoints()+"/");
                    bw.newLine();
                    bw.write(line.getId()+"/");
                    bw.newLine();
                    bw.write(":");

                }
            }
            bw.newLine();
            bw.write(":");
            bw.close();
            fw.close();

        }catch (IOException e){
            System.out.println("error");
        }

    }

    public static void openProperties(){

        VBox general = new VBox();

        Stage stage = new Stage();
        Scene scene = new Scene(general,600,400);

        TextArea id = new TextArea();
        id.setMaxWidth(150);
        id.setMaxHeight(10);
        id.setPromptText("Recherchez par l'id");

        Button search = new Button();
        search.setText("Chercher");

        Label result = new Label("Conseil : les ids de base sont sous la forme : initiale de l'objet + nombre d'objet\nPar exmple,si vous avez 3 Cercles,ce sera c0,c1 et c2 comme ids de base pour ces cercles");

        FlowPane one = new FlowPane(id,search);

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node element = search(id.getText());
                if(element != null){
                    stage.setTitle("Paramètres de l'élément : "+element.getId());
                    general.getChildren().removeAll(one,result);

                    Label title = new Label("Propriétés de l'élément : "+element.getId());
                    Label none = new Label(" ");

                    general.getChildren().addAll(title,none);

                    if(element.getTypeSelector().equalsIgnoreCase("Rectangle")){
                        Rectangle el = (Rectangle) element;
                        Label with = new Label("Largeur :");
                        TextField wi = new TextField();
                        wi.setMaxWidth(150);
                        wi.setMaxHeight(10);
                        wi.setText(String.valueOf(el.getWidth()));
                        Label height = new Label("Hauteur :");
                        TextField hi = new TextField();
                        hi.setMaxWidth(150);
                        hi.setMaxHeight(10);
                        hi.setText(String.valueOf(el.getHeight()));
                        Label x = new Label("Position X :");
                        TextField xi = new TextField();
                        xi.setMaxWidth(150);
                        xi.setMaxHeight(10);
                        xi.setText(String.valueOf(el.getX()));
                        Label y = new Label("Position Y :");
                        TextField yi = new TextField();
                        yi.setMaxWidth(150);
                        yi.setMaxHeight(10);
                        yi.setText(String.valueOf(el.getY()));

                        Button soumettre = new Button("Modifier");
                        soumettre.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    el.setWidth(Double.parseDouble(wi.getText()));
                                    el.setHeight(Double.parseDouble(hi.getText()));
                                    el.setX(Double.parseDouble(xi.getText()));
                                    el.setY(Double.parseDouble(yi.getText()));
                                    stage.close();
                                    save();
                                }catch (NumberFormatException e){
                                    soumettre.setPadding(new Insets(5));
                                    soumettre.setText("Un nombre est invalide !");
                                }
                            }
                        });




                        general.getChildren().addAll(with,wi,height,hi,x,xi,y,yi,soumettre);
                    }
                    if(element.getTypeSelector().equalsIgnoreCase("Circle")){
                        Circle el = (Circle) element;
                        Label with = new Label("Rayon :");
                        TextField wi = new TextField();
                        wi.setMaxWidth(150);
                        wi.setMaxHeight(10);
                        wi.setText(String.valueOf(el.getRadius()));
                        Label x = new Label("Position X :");
                        TextField xi = new TextField();
                        xi.setMaxWidth(150);
                        xi.setMaxHeight(10);
                        xi.setText(String.valueOf(el.getCenterX()));
                        Label y = new Label("Position Y :");
                        TextField yi = new TextField();
                        yi.setMaxWidth(150);
                        yi.setMaxHeight(10);
                        yi.setText(String.valueOf(el.getCenterY()));

                        Button soumettre = new Button("Modifier");
                        soumettre.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    el.setRadius(Double.parseDouble(wi.getText()));
                                    el.setCenterY(Double.parseDouble(yi.getText()));
                                    el.setCenterX(Double.parseDouble(xi.getText()));
                                    stage.close();
                                }catch (NumberFormatException e){
                                    soumettre.setPadding(new Insets(5));
                                    soumettre.setText("Un nombre est invalide !");
                                }
                            }
                        });




                        general.getChildren().addAll(with,wi,x,xi,y,yi,soumettre);
                    }
                    if(element.getTypeSelector().equalsIgnoreCase("Line")){
                        Line el = (Line) element;
                        Label with = new Label("Position X du début :");
                        TextField wi = new TextField();
                        wi.setMaxWidth(150);
                        wi.setMaxHeight(10);
                        wi.setText(String.valueOf(el.getStartX()));
                        Label height = new Label("Position Y du début :");
                        TextField hi = new TextField();
                        hi.setMaxWidth(150);
                        hi.setMaxHeight(10);
                        hi.setText(String.valueOf(el.getStartY()));
                        Label x = new Label("Position X de la fin :");
                        TextField xi = new TextField();
                        xi.setMaxWidth(150);
                        xi.setMaxHeight(10);
                        xi.setText(String.valueOf(el.getEndX()));
                        Label y = new Label("Position Y de la fin :");
                        TextField yi = new TextField();
                        yi.setMaxWidth(150);
                        yi.setMaxHeight(10);
                        yi.setText(String.valueOf(el.getEndY()));

                        Button soumettre = new Button("Modifier");
                        soumettre.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    el.setStartX(Double.parseDouble(wi.getText()));
                                    el.setStartY(Double.parseDouble(hi.getText()));
                                    el.setEndX(Double.parseDouble(xi.getText()));
                                    el.setEndY(Double.parseDouble(yi.getText()));
                                    stage.close();
                                }catch (NumberFormatException e){
                                    soumettre.setPadding(new Insets(5));
                                    soumettre.setText("Un nombre est invalide !");
                                }
                            }
                        });

                        general.getChildren().addAll(with,wi,height,hi,x,xi,y,yi,soumettre);
                    }
                    if(element.getTypeSelector().equalsIgnoreCase("Polyline")) {
                        title.setText("Les multilignes ne sont pas encore modifiables pour l'instant");
                    }
                }else{
                    search.setText("Aucun élément");
                }
            }
        });

        general.getChildren().addAll(one,result);
        general.setAlignment(Pos.CENTER);
        one.setAlignment(Pos.CENTER);

        stage.setTitle("Paramètres d'un élément");
        stage.setScene(scene);
        stage.show();


    }

    public static Node search(String id){
        for(int i = 2; i < group.getChildren().size(); i++){
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
            icon.setTranslateY(25);
        }
        if(selector == 2){
            icon.setText("⭕");
            icon.setTranslateY(25);
        }
        if(selector == 3){
            icon.setText("➖");
            icon.setTranslateY(15);
        }
        if(selector == 4){
            icon.setText("〰");
            icon.setTranslateY(15);
        }
        if(selector == 5){
            icon.setText("🖱");
            icon.setTranslateY(25);
        }
    }

    }
