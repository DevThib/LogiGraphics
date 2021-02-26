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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    Group group = new Group();
    Scene scene = new Scene(group,1300,900, Color.GREY);

    FlowPane menu = new FlowPane();

    boolean isOnAction = false;
    Node nodeAction = null;

    double moveX = 0;
    double moveY = 0;

    Node selected;

    int selector = 1;

    Circle circle;
    Rectangle rectangle;
    Line line;


    /* selector :

    1 = rectangle
    2 = cercle
    3 = ligne
    4 = libre

     */

    @Override
    public void start(Stage primaryStage){

        Stage stage = new Stage();
        VBox box = new VBox();
        Label label = new Label();
        label.setText(randomTip());
        label.setFont(new Font("Trebuchet MS",15));
        box.getChildren().addAll(label);
        box.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(box,500,225));
        stage.setTitle("Astuce du jour !");

        /*
        A DEV :

        -quand on lance le truc,il nous affiche un "tip of the day" (comme intellig) avec "vous pouvez changer de sélection rapidement avec TAB
        -changer de sélection avec tab
        -changer le menu avec des boutons
        -fixer le bug des menus (se fixe peut être avec les boutons)
        -mettre le système d'alignement
        -système pour enregistrer le projet sous forme de fichiers txt (donc mettre un bouton save)
        -auto save
        -bouton retour en arrière
        -mettre github
        -bouton paramètres

         */

        menu.setPadding(new Insets(3));

        Rectangle rectanglee = new Rectangle();
        rectanglee.setWidth(50);
        rectanglee.setHeight(50);
        rectanglee.setFill(Color.RED);
        rectanglee.setOpacity(0.5);
        rectanglee.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selector = 1;
                menuSelector();
            }
        });

        Circle circlee = new Circle();
        circlee.setRadius(25);
        circlee.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selector = 2;
                menuSelector();
            }
        });

        Line linee = new Line();
        linee.setStartY(0);
        linee.setEndY(50);
        linee.setStrokeWidth(10);
        linee.setStartX(120);
        linee.setEndX(120);
        linee.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selector = 3;
                menuSelector();
            }
        });

        Button button = new Button();
        button.setMinWidth(50);
        button.setMinHeight(50);
        button.setPadding(new Insets(2));
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selector = 4;
                menuSelector();
            }
        });


       menu.getChildren().addAll(rectanglee,circlee,linee,button);

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if(!isOnAction){
                    isOnAction = true;

                    if(selector == 1){

                        rectangle = new Rectangle();

                        rectangle.setY(event.getY());
                        rectangle.setX(event.getX());
                        rectangle.setWidth(6);
                        rectangle.setHeight(6);
                        nodeAction = rectangle;
                      /*  rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                selected = rectangle;
                                select();
                            }
                        });

                       */
                        group.getChildren().add(rectangle);
                    }

                    if(selector == 2){
                        circle = new Circle();

                        circle.setCenterX(event.getX());
                        circle.setCenterY(event.getY());
                        circle.setRadius(15);
                        nodeAction = circle;
                      /*  circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                selected = circle;
                                select();
                            }
                        });

                       */
                        group.getChildren().add(circle);
                    }

                    if(selector == 3){
                        line = new Line();

                        line.setStartX(event.getX());
                        line.setStartY(event.getY());
                        line.setEndY(event.getX());
                        line.setEndY(event.getY());
                        nodeAction = line;

                      /*  circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                selected = circle;
                                select();
                            }
                        });

                       */
                        group.getChildren().add(line);
                    }

                }else{
                   isOnAction = false;
                   nodeAction = null;
                   moveY = 0;
                   moveX = 0;
                }

            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(isOnAction){

                    if(selector == 1){

                        if(moveX < event.getX()) {

                            moveX = event.getX();

                            Rectangle circle1 = (Rectangle) nodeAction;
                            circle1.setWidth(rectangle.getWidth()+2);
                            rectangle = circle1;

                        }

                        if(moveX > event.getX()) {

                            moveY = event.getY();
                            moveX = event.getX();

                            Rectangle circle1 = (Rectangle) nodeAction;
                            circle1.setWidth(rectangle.getWidth()-2);
                            rectangle = circle1;

                        }
                        if(moveY < event.getY()) {

                            moveY = event.getY();

                            Rectangle circle1 = (Rectangle) nodeAction;
                            circle1.setHeight(rectangle.getHeight()+2);
                            rectangle = circle1;

                        }

                        if(moveY > event.getY()) {

                            moveY = event.getY();

                            Rectangle circle1 = (Rectangle) nodeAction;
                            if(rectangle.getHeight() >= 2){
                                circle1.setHeight(rectangle.getHeight()-2);
                            }
                            if(rectangle.getHeight() < 2){
                                rectangle.setHeight(3);
                            }
                            rectangle = circle1;

                        }

                    }

                    if(selector == 2){

                        if(moveX < event.getX() || moveY < event.getY()) {

                            moveY = event.getY();
                            moveX = event.getX();
                            Circle circle1 = (Circle) nodeAction;
                            circle1.setRadius(circle.getRadius() + 1.5);
                            circle = circle1;

                        }

                        if(moveX > event.getX() || moveY > event.getY()) {

                            moveY = event.getY();
                            moveX = event.getX();
                            Circle circle1 = (Circle) nodeAction;
                            circle1.setRadius(circle.getRadius() - 1.5);
                            circle = circle1;

                        }

                    }
                    if(selector == 3){

                        line.setEndX(event.getX());
                        line.setEndY(event.getY());

                    }

                }

            }
        });


        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if(event.getCharacter().equalsIgnoreCase("p")){
                    selector++;
                    if(selector > 4){
                        selector = 1;
                    }
                    menuSelector();
                }

                if(event.getCharacter().equalsIgnoreCase("z")){
                    for(int i = 1; i < group.getChildren().size(); i++){
                        group.getChildren().get(i).setTranslateY(group.getChildren().get(i).getTranslateY()-10);
                    }
                }

                if(event.getCharacter().equalsIgnoreCase("s")){
                    for(int i = 1; i < group.getChildren().size(); i++){
                        group.getChildren().get(i).setTranslateY(group.getChildren().get(i).getTranslateY()+10);
                    }
                }

                if(event.getCharacter().equalsIgnoreCase("d")){
                    for(int i = 1; i < group.getChildren().size(); i++){
                        group.getChildren().get(i).setTranslateX(group.getChildren().get(i).getTranslateX()+10);
                    }
                }

                if(event.getCharacter().equalsIgnoreCase("q")){
                    for(int i = 1; i < group.getChildren().size(); i++){
                        group.getChildren().get(i).setTranslateX(group.getChildren().get(i).getTranslateX()-10);
                    }
                }

            }
        });

        group.getChildren().addAll(menu);

        primaryStage.setTitle("LogiGraphics");
        primaryStage.setScene(scene);
        primaryStage.show();
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void analyseCircles(double posY,double posX){

        //créer une ligne entre la souris et le centre du cercle

        for(int i = 1; i < group.getChildren().size(); i++){
            if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")){
                Circle circle = (Circle) group.getChildren().get(i);
                if(circle.getCenterX() == posX){
                    Line line = new Line();
                    line.setStartX(posX);
                    line.setEndX(posX);
                    line.setStartY(posY);
                    line.setEndY(circle.getCenterY());
                }
            }
        }


    }

    public void select(){
        for(int i = 1; i < group.getChildren().size(); i++){
            if(group.getChildren().get(i) == selected){

                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")){
                    Circle c = (Circle) selected;
                    c.setFill(Color.RED);
                    c.setOpacity(0.5);
                }
                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Rectangle")){
                    Rectangle c = (Rectangle) selected;
                    c.setFill(Color.RED);
                    c.setOpacity(0.5);
                }
                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Line")){
                    Line c = (Line) selected;
                    c.setFill(Color.RED);
                    c.setOpacity(0.5);
                }

            }else{

                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")){
                    Circle c = (Circle) selected;
                    c.setFill(Color.BLACK);
                    c.setOpacity(1);
                }
                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Rectangle")){
                    Rectangle c = (Rectangle) selected;
                    c.setFill(Color.BLACK);
                    c.setOpacity(1);
                }
                if(group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Line")){
                    Line c = (Line) selected;
                    c.setFill(Color.BLACK);
                    c.setOpacity(1);
                }
            }

        }
    }

    public void menuSelector(){

        if(selector == 1){
            Rectangle r = (Rectangle) menu.getChildren().get(0);
            r.setFill(Color.RED);
            r.setOpacity(0.5);

            Circle c = (Circle) menu.getChildren().get(1);
            c.setFill(Color.BLACK);
            c.setOpacity(1);

            Line l = (Line) menu.getChildren().get(2);
            l.setFill(Color.BLACK);
            l.setOpacity(1);
        }
        if(selector == 2){
            Rectangle r = (Rectangle) menu.getChildren().get(0);
            r.setFill(Color.BLACK);
            r.setOpacity(1);

            Circle c = (Circle) menu.getChildren().get(1);
            c.setFill(Color.RED);
            c.setOpacity(0.5);

            Line l = (Line) menu.getChildren().get(2);
            l.setFill(Color.BLACK);
            l.setOpacity(1);
        }
        if(selector == 3){
            Rectangle r = (Rectangle) menu.getChildren().get(0);
            r.setFill(Color.BLACK);
            r.setOpacity(1);

            Circle c = (Circle) menu.getChildren().get(1);
            c.setFill(Color.BLACK);
            c.setOpacity(1);

            Line l = (Line) menu.getChildren().get(2);
            l.setOpacity(0.5);
        }
        if(selector == 4){
            Rectangle r = (Rectangle) menu.getChildren().get(0);
            r.setFill(Color.BLACK);
            r.setOpacity(1);

            Circle c = (Circle) menu.getChildren().get(1);
            c.setFill(Color.BLACK);
            c.setOpacity(1);

            Line l = (Line) menu.getChildren().get(2);
            l.setFill(Color.BLACK);
            l.setOpacity(1);
        }

    }

    public String randomTip(){
        Random r = new Random();
        int nb = r.nextInt(2);

        switch (nb){

            case 0:
                return "Vous pouvez changer de sélection en appuyant sur p !";

            case 1:
                return "Vous pouvez déplacer les éléments avec z q s et d !";


        }
        return "null";
    }

}
