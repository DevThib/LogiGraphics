package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;

public class Main extends Application {

    Stage primary;

    static Group group = new Group();
    public static Scene scene = new Scene(group, 1300, 900, Color.GREY);

    public static boolean isOnAction = false;
    public static Node nodeAction = null;

    static double moveX = 0;
    static double moveY = 0;

    public static int selector = 1;

    public static Circle circle;
    public static Rectangle rectangle;
    public static Line line;

    int numberOfRectangle = 0;
    int numberOfCircle = 0;
    int numberOfLine = 0;

    static Line indicator = new Line();

    File workspace;

    MenuBar bar = new MenuBar();

    Menu formes = new Menu("Construire");
    Menu functions = new Menu("Fonctions");
    Menu files = new Menu("Fichiers");
    Menu elements = new Menu("Éléments");
    Menu visibilty = new Menu("Visibilité");

    File superpose;

    boolean rVis = true;
    boolean rCi = true;
    boolean rLi = true;
    boolean rSh = true;

    /* selector :

    1 = rectangle
    2 = cercle
    3 = ligne
    4 = libre

     */

    @Override
    public void start(Stage primaryStage) {

        addMenuItems();


        Stage choose = new Stage();
        FlowPane e = new FlowPane();
        e.setAlignment(Pos.CENTER);
        Scene sce = new Scene(e, 600, 450);

        Button create = new Button("Créer");
        Button open = new Button("Ouvrir");

        create.setFont(getFont(25));
        open.setFont(getFont(25));

        VBox b1 = getVBox("Créer un projet", create);
        VBox b2 = getVBox("Ouvrir un projet", open);

        e.getChildren().addAll(b1, b2);

        choose.setScene(sce);
        choose.setTitle("Projet");
        choose.show();

        create.setOnAction(event -> {

            choose.close();

            try {

                int number = 1;
                File file = new File(System.getProperty("user.dir"), "projet" + number + ".txt");
                while (file.exists()) {
                    number++;
                    file = new File(System.getProperty("user.dir"), "projet" + number + ".txt");
                }
                file.createNewFile();

                workspace = file;

                Stage stage = new Stage();
                VBox box = new VBox();
                Label label = new Label();
                label.setText(randomTip());
                label.setFont(getFont(15));
                box.getChildren().addAll(label);
                box.setAlignment(Pos.CENTER);
                stage.setScene(new Scene(box, 500, 225));
                stage.setTitle("Astuce du jour !");

                //mettre les menus et fonctionnels

                scene.setOnMouseClicked(event13 -> {

                    if (!isOnAction) {
                        if (selector != 4) {
                            isOnAction = true;
                        }

                        if (selector == 1) {

                            rectangle = new Rectangle();

                            rectangle.setOpacity(0.3);
                            rectangle.setY(event13.getY());
                            rectangle.setX(event13.getX());
                            rectangle.setWidth(6);
                            rectangle.setHeight(6);
                            nodeAction = rectangle;
                            group.getChildren().add(rectangle);
                        }

                        if (selector == 2) {
                            circle = new Circle();

                            circle.setOpacity(0.3);
                            circle.setCenterX(event13.getX());
                            circle.setCenterY(event13.getY());
                            circle.setRadius(15);
                            nodeAction = circle;
                            group.getChildren().add(circle);
                        }

                        if (selector == 3) {
                            line = new Line();

                            line.setOpacity(0.3);
                            line.setStartX(event13.getX());
                            line.setStartY(event13.getY());
                            line.setEndY(event13.getX());
                            line.setEndY(event13.getY());
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

                });

                scene.setOnKeyTyped(event14 -> {

                    if (event14.getCharacter().equalsIgnoreCase("p")) {
                        selector++;
                        if (selector > 4) {
                            selector = 1;
                        }
                    } else if (event14.getCharacter().equalsIgnoreCase("z")) {
                        moveY(true);
                    } else if (event14.getCharacter().equalsIgnoreCase("s")) {
                        moveY(false);
                    } else if (event14.getCharacter().equalsIgnoreCase("d")) {
                        moveX(true);
                    } else if (event14.getCharacter().equalsIgnoreCase("q")) {
                        moveX(false);
                    }

                });

                bar.getMenus().addAll(files, formes, functions, elements, visibilty);
                group.getChildren().addAll(bar, indicator);

                openProject(workspace);

                char[] na = workspace.getName().toCharArray();
                StringBuilder name = new StringBuilder();
                for (int i = 0; i < na.length - 4; i++) {
                    name.append(na[i]);
                }

                primaryStage.setTitle("LogiGraphics - " + name);
                primaryStage.setScene(scene);
                primaryStage.show();
                primary = primaryStage;
                stage.show();

            } catch (IOException e1) {
                System.out.println("error");
            }

        });

        open.setOnAction(event -> {

            try {

                FileChooser chooser = new FileChooser();
                chooser.setTitle("Sélectionner un projet");
                workspace = chooser.showOpenDialog(primaryStage);

                Stage stage = new Stage();
                VBox box = new VBox();
                Label label = new Label();
                label.setText(randomTip());
                label.setFont(new Font("Trebuchet MS", 15));
                box.getChildren().addAll(label);
                box.setAlignment(Pos.CENTER);
                stage.setScene(new Scene(box, 500, 225));
                stage.setTitle("Astuce du jour !");

                //mettre les menus et fonctionnels

                scene.setOnMouseClicked(event16 -> {

                    if (!isOnAction) {
                        if (selector != 4) {
                            isOnAction = true;
                        }

                        if (selector == 1) {

                            rectangle = new Rectangle();

                            rectangle.setOpacity(0.3);
                            rectangle.setY(event16.getY());
                            rectangle.setX(event16.getX());
                            rectangle.setWidth(6);
                            rectangle.setHeight(6);
                            nodeAction = rectangle;
                            group.getChildren().add(rectangle);
                        }

                        if (selector == 2) {
                            circle = new Circle();

                            circle.setOpacity(0.3);
                            circle.setCenterX(event16.getX());
                            circle.setCenterY(event16.getY());
                            circle.setRadius(15);
                            nodeAction = circle;
                            group.getChildren().add(circle);
                        }

                        if (selector == 3) {
                            line = new Line();

                            line.setOpacity(0.3);
                            line.setStartX(event16.getX());
                            line.setStartY(event16.getY());
                            line.setEndY(event16.getX());
                            line.setEndY(event16.getY());
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

                });

                scene.setOnMouseMoved(event15 -> {

                    if (isOnAction) {

                        if (selector == 1) {

                            Rectangle circle1 = (Rectangle) nodeAction;
                            circle1.setWidth(event15.getX() - circle1.getX());
                            circle1.setHeight(event15.getY() - circle1.getY());
                            rectangle = circle1;

                        }

                        if (selector == 2) {

                            moveY = event15.getY();
                            moveX = event15.getX();
                            Circle circle1 = (Circle) nodeAction;
                            circle1.setRadius(event15.getX() - circle1.getCenterX());
                            circle = circle1;

                        }
                        if (selector == 3) {
                            line.setEndX(event15.getX());
                            line.setEndY(event15.getY());
                        }

                    }
                    analyse(event15.getY(), event15.getX());
                });

                scene.setOnKeyTyped(event12 -> {

                    if (event12.getCharacter().equalsIgnoreCase("p")) {
                        selector++;
                        if (selector > 4) {
                            selector = 1;
                        }
                    }

                    if (event12.getCharacter().equalsIgnoreCase("z")) {
                        moveY(true);
                    }

                    if (event12.getCharacter().equalsIgnoreCase("s")) {
                        moveY(false);
                    }

                    if (event12.getCharacter().equalsIgnoreCase("d")) {
                        moveX(true);
                    }

                    if (event12.getCharacter().equalsIgnoreCase("q")) {
                        moveX(false);
                    }

                });

                bar.getMenus().addAll(files, formes, functions, elements, visibilty);
                group.getChildren().addAll(bar, indicator);

                openProject(workspace);

                char[] na = workspace.getName().toCharArray();
                StringBuilder name = new StringBuilder();
                for (int i = 0; i < na.length - 4; i++) {
                    name.append(na[i]);
                }

                primaryStage.setTitle("LogiGraphics - " + name);
                primaryStage.setScene(scene);
                primaryStage.show();
                primary = primaryStage;
                stage.show();
                choose.close();

            } catch (NullPointerException e12) {
                choose.close();
            }

        });

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void analyse(double posY, double posX) {

        //créer une ligne entre la souris et le centre du cercle

        for (int i = 1; i < group.getChildren().size(); i++) {
            if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")) {
                Circle circle = (Circle) group.getChildren().get(i);
                if (circle.getCenterX() > posX - 3 && circle.getCenterX() < posX + 3 || circle.getCenterY() > posY - 3 && circle.getCenterY() < posY + 3) {
                    setIndicator(circle.getCenterX(), circle.getCenterY(), posX, posY);
                } else {
                    indicator.setVisible(false);
                }
            }
            if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Rectangle")) {
                Rectangle circle = (Rectangle) group.getChildren().get(i);
                if (circle.getX() == posX || circle.getY() == posY) {
                    setIndicator(circle.getX(), circle.getY(), posX, posY);
                } else {
                    indicator.setVisible(false);
                }
            }
            if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Line")) {
                Line circle = (Line) group.getChildren().get(i);
                if (circle.getStartX() == posX || circle.getStartY() == posY) {
                    setIndicator(circle.getStartX(), circle.getStartY(), posX, posY);
                } else if (circle.getEndX() == posX || circle.getEndY() == posY) {
                    setIndicator(circle.getEndX(), circle.getEndY(), posX, posY);
                }
            }
        }
    }

    public static void setIndicator(double startX, double startY, double posX, double posY) {
        indicator.setOpacity(0.5);
        indicator.setVisible(true);
        indicator.setStartX(startX);
        indicator.setStartY(startY);
        indicator.setEndY(posY);
        indicator.setEndX(posX);
    }

    public String randomTip() {
        Random r = new Random();
        int nb = r.nextInt(2);

        switch (nb) {

            case 0:
                return "Vous pouvez changer de sélection en appuyant sur p !";

            case 1:
                return "Vous pouvez déplacer les éléments avec z q s et d !";


        }
        return "null";
    }

    public void moveY(boolean up) {
        if (up) {
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateY(group.getChildren().get(i).getTranslateY() - 10);
            }
        } else {
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateY(group.getChildren().get(i).getTranslateY() + 10);
            }
        }
    }

    public void moveX(boolean right) {
        if (!right) {
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateX(group.getChildren().get(i).getTranslateX() - 10);
            }
        } else {
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().get(i).setTranslateX(group.getChildren().get(i).getTranslateX() + 10);
            }
        }
    }

    public void back() {
        if (group.getChildren().size() > 2) {
            group.getChildren().remove(group.getChildren().size() - 1);
        }
        save();
    }

    public void openProject(File fileToOpen) {

        try {

            FileReader fr = new FileReader(fileToOpen);
            BufferedReader br = new BufferedReader(fr);

            StringBuilder all = new StringBuilder();
            String str = "";
            while (str != null) {
                str = br.readLine();
                if (str != null) {
                    all.append(str);
                }
            }

            String[] values = all.toString().split(":");
            for (String value : values) {

                String[] properties = value.split("/");
                if (properties[0].equalsIgnoreCase("Rectangle")) {

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
                if (properties[0].equalsIgnoreCase("Circle")) {

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
                if (properties[0].equalsIgnoreCase("Line")) {

                    String[] start = properties[1].split(",");
                    String[] end = properties[2].split(",");

                    Line l = new Line();
                    l.setStartX(Double.parseDouble(start[0]));
                    l.setStartY(Double.parseDouble(start[1]));
                    l.setEndX(Double.parseDouble(end[0]));
                    l.setEndY(Double.parseDouble(end[1]));
                    l.setId(properties[4]);
                    group.getChildren().add(l);
                    numberOfLine++;

                }

            }
            for (String value : values) {
                String[] properties = value.split("/");
                if (properties[0].equalsIgnoreCase("Shape")) {

                    if (properties[1].equalsIgnoreCase("substract")) {
                        Shape shape1 = (Shape) search(properties[2]);
                        Shape shape2 = (Shape) search(properties[3]);
                        Shape shape = Shape.subtract(shape1, shape2);

                        for (int a = 0; a < group.getChildren().size(); a++) {
                            if (properties[2].equalsIgnoreCase(group.getChildren().get(a).getId()) || properties[3].equalsIgnoreCase(group.getChildren().get(a).getId())) {
                                group.getChildren().get(a).setVisible(false);
                            }
                        }
                        group.getChildren().addAll(shape);
                    }

                }
            }


        } catch (IOException e) {
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


    public void save() {

        try {

            FileWriter fw = new FileWriter(workspace);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 2; i < group.getChildren().size(); i++) {

                System.out.println(group.getChildren().get(i).getTypeSelector());

                bw.newLine();
                bw.write(":");
                bw.newLine();

                if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Rectangle")) {
                    Rectangle circle = (Rectangle) group.getChildren().get(i);
                    bw.write("Rectangle/");
                    bw.newLine();
                    bw.write(circle.getX() + "/");
                    bw.newLine();
                    bw.write(circle.getY() + "/");
                    bw.newLine();
                    bw.write(circle.getWidth() + "," + circle.getHeight() + "/");
                    bw.newLine();
                    if (circle.getId() == null) {
                        bw.write("null");
                    } else {
                        bw.write(circle.getId());
                    }

                }
                if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Line")) {
                    Line circle = (Line) group.getChildren().get(i);
                    bw.write("Line/");
                    bw.newLine();
                    bw.write(circle.getStartX() + "," + circle.getStartY() + "/");
                    bw.newLine();
                    bw.write(circle.getEndX() + "," + circle.getEndY() + "/");
                    bw.newLine();
                    if (circle.getId() == null) {
                        bw.write("null");
                    } else {
                        bw.write(circle.getId());
                    }

                }
                if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")) {
                    Circle circle = (Circle) group.getChildren().get(i);
                    bw.write("Circle/");
                    bw.newLine();
                    bw.write(circle.getCenterX() + "/");
                    bw.newLine();
                    bw.write(circle.getCenterY() + "/");
                    bw.newLine();
                    bw.write(circle.getRadius() + "/");
                    bw.newLine();
                    if (circle.getId() == null) {
                        bw.write("null");
                    } else {
                        bw.write(circle.getId());
                    }

                }
                if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Path")) {

                    String[] types = group.getChildren().get(i).getId().split(",");

                    bw.write("Shape/");
                    bw.newLine();
                    bw.write(types[0] + "/");
                    bw.newLine();
                    bw.write(types[1] + "/");
                    bw.newLine();
                    bw.write(types[2] + "/");
                    bw.newLine();
                    bw.write(":");

                }
            }
            bw.newLine();
            bw.write(":");
            bw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println("error");
        }

    }

    public void addMenuItems() {
        MenuItem rectangle = new MenuItem("🟫 Rectangle");
        rectangle.setOnAction(event -> selector = 1);

        MenuItem circle = new MenuItem("⭕ Cercle");
        circle.setOnAction(event -> selector = 2);

        MenuItem line = new MenuItem("〰 Ligne");
        line.setOnAction(event -> selector = 3);

        MenuItem free = new MenuItem("🖱 Libre");
        free.setOnAction(event -> selector = 4);

        formes.getItems().addAll(rectangle, circle, line, free);

        MenuItem back = new MenuItem("↩ Retour");
        back.setOnAction(event -> back());

        MenuItem all = new MenuItem("❌ Effacer tout");
        all.setOnAction(event -> {
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            numberOfRectangle = 0;
            numberOfLine = 0;
            numberOfCircle = 0;
        });


        MenuItem eraser = new MenuItem("✂ Découper");
        eraser.setOnAction(event -> {

            VBox general = new VBox();

            Scene scene = new Scene(general, 600, 500);
            Stage stage = new Stage();

            Label text = new Label("Gommer un élément par rapport à un autre");
            FlowPane pane = new FlowPane();
            TextField id1 = new TextField();
            id1.setPromptText("Soustraire l'objet (id)");
            Label te = new Label("à");
            TextField id2 = new TextField();
            id2.setPromptText("l'objet (id)");
            pane.getChildren().addAll(id1, te, id2);

            Button soumet = new Button("Soumettre");
            soumet.setOnAction(event1 -> {
                Node element1 = search(id1.getText());
                Node element2 = search(id2.getText());

                if (element1 != null && element2 != null) {

                    Shape shape1 = (Shape) element1;
                    Shape shape2 = (Shape) element2;

                    Shape shape = Shape.subtract(shape1, shape2);

                    group.getChildren().addAll(shape);
                    element1.setVisible(false);
                    element2.setVisible(false);
                    stage.close();
                    shape.setId("substract," + element1.getId() + "," + element2.getId());
                    save();

                } else {
                    soumet.setText("Un identifiant est incorrect");
                }
            });

            general.getChildren().addAll(text, pane, soumet);
            general.setAlignment(Pos.CENTER);

            pane.setAlignment(Pos.CENTER);

            stage.setScene(scene);
            stage.setTitle("Gomme");
            stage.show();

        });

        functions.getItems().addAll(back, eraser, all);

        MenuItem create = new MenuItem("➕ Nouveau");
        create.setOnAction(event -> {

            int number = 1;
            File file = new File(System.getProperty("user.dir"), "projet" + number + ".txt");
            while (file.exists()) {
                number++;
                file = new File(System.getProperty("user.dir"), "projet" + number + ".txt");
            }
            try {
                file.createNewFile();

                for (int i = 2; i < group.getChildren().size(); i++) {
                    group.getChildren().removeAll(group.getChildren().get(i));
                }
                for (int i = 2; i < group.getChildren().size(); i++) {
                    group.getChildren().removeAll(group.getChildren().get(i));
                }
                for (int i = 2; i < group.getChildren().size(); i++) {
                    group.getChildren().removeAll(group.getChildren().get(i));
                }
                for (int i = 2; i < group.getChildren().size(); i++) {
                    group.getChildren().removeAll(group.getChildren().get(i));
                }
                for (int i = 2; i < group.getChildren().size(); i++) {
                    group.getChildren().removeAll(group.getChildren().get(i));
                }
                for (int i = 2; i < group.getChildren().size(); i++) {
                    group.getChildren().removeAll(group.getChildren().get(i));
                }
                numberOfCircle = 0;
                numberOfLine = 0;
                numberOfRectangle = 0;
                workspace = file;
                char[] na = workspace.getName().toCharArray();
                StringBuilder name = new StringBuilder();
                for (int i = 0; i < na.length - 4; i++) {
                    name.append(na[i]);
                }
                primary.setTitle("LogiGraphics - " + name);
                openProject(workspace);

            } catch (IOException e) {
                System.out.println("error");
            }


        });

        MenuItem open = new MenuItem("📁 Ouvrir");
        open.setOnAction(event -> {

            numberOfCircle = 0;
            numberOfLine = 0;
            numberOfRectangle = 0;
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Sélectionner un projet");
            workspace = chooser.showOpenDialog(primary);
            char[] na = workspace.getName().toCharArray();
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < na.length - 4; i++) {
                name.append(na[i]);
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            for (int i = 2; i < group.getChildren().size(); i++) {
                group.getChildren().removeAll(group.getChildren().get(i));
            }
            primary.setTitle("LogiGraphics - " + name);
            openProject(workspace);
        });

        MenuItem save = new MenuItem("✅ Enregistrer");
        save.setOnAction(event -> save());

        MenuItem superposer = new MenuItem("📃 Superposer");
        superposer.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Sélectionner un projet");
            superpose = chooser.showOpenDialog(primary);
            openProject(superpose);
        });

        files.getItems().addAll(create, open, save, superposer);


        MenuItem modifier = new MenuItem("📝 Modifier");
        modifier.setOnAction(event -> openProperties());

        elements.getItems().addAll(modifier);


        MenuItem rec = new MenuItem("🟫 Visible");
        rec.setOnAction(event -> {
            if (rVis) {
                rVis = false;
                setVisibility(false, "Rectangle");
                rec.setText("🟫 Invisible");
            } else {
                rVis = true;
                setVisibility(true, "Rectangle");
                rec.setText("🟫 Visible");
            }
        });

        MenuItem cir = new MenuItem("⭕ Visible");
        cir.setOnAction(event -> {
            if (rCi) {
                rCi = false;
                setVisibility(false, "Circle");
                cir.setText("⭕ Invisible");
            } else {
                rCi = true;
                setVisibility(true, "Circle");
                cir.setText("⭕ Visible");
            }
        });

        MenuItem li = new MenuItem("〰 Visible");
        li.setOnAction(event -> {
            if (rLi) {
                rLi = false;
                setVisibility(false, "Line");
                li.setText("〰 Invisible");
            } else {
                rLi = true;
                setVisibility(true, "Line");
                li.setText("〰 Visible");
            }
        });

        MenuItem sh = new MenuItem("🌙 Visible");
        sh.setOnAction(event -> {
            if (rSh) {
                rSh = false;
                setVisibility(false, "Path");
                sh.setText("🌙 Invisible");
            } else {
                rSh = true;
                setVisibility(true, "Path");
                sh.setText("🌙 Visible");
            }
        });


        visibilty.getItems().addAll(rec, cir, li, sh);
        //il y en aura un autre "autres" pour les paramètres,l'aide etc...
    }

    public void openProperties() {

        VBox general = new VBox();

        Stage stage = new Stage();
        Scene scene = new Scene(general, 600, 400);

        TextArea id = new TextArea();
        id.setMaxWidth(150);
        id.setMaxHeight(10);
        id.setPromptText("Recherchez par l'id");

        Button search = new Button();
        search.setText("Chercher");

        Label result = new Label("Conseil : les ids de base sont sous la forme : initiale de l'objet + nombre d'objet\nPar exmple,si vous avez 3 Cercles,ce sera c0,c1 et c2 comme ids de base pour ces cercles");

        FlowPane one = new FlowPane(id, search);

        search.setOnAction(event -> {
            Node element = search(id.getText());
            if (element != null) {
                stage.setTitle("Paramètres de l'élément : " + element.getId());
                general.getChildren().removeAll(one, result);

                Label title = new Label("Propriétés de l'élément : " + element.getId());
                Label none = new Label(" ");

                general.getChildren().addAll(title, none);

                if (element.getTypeSelector().equalsIgnoreCase("Rectangle")) {
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
                    soumettre.setOnAction(event1 -> {
                        try {
                            el.setWidth(Double.parseDouble(wi.getText()));
                            el.setHeight(Double.parseDouble(hi.getText()));
                            el.setX(Double.parseDouble(xi.getText()));
                            el.setY(Double.parseDouble(yi.getText()));
                            stage.close();
                            save();
                        } catch (NumberFormatException e) {
                            soumettre.setPadding(new Insets(5));
                            soumettre.setText("Un nombre est invalide !");
                        }
                    });


                    general.getChildren().addAll(with, wi, height, hi, x, xi, y, yi, soumettre);
                }
                if (element.getTypeSelector().equalsIgnoreCase("Circle")) {
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
                    soumettre.setOnAction(event13 -> {
                        try {
                            el.setRadius(Double.parseDouble(wi.getText()));
                            el.setCenterY(Double.parseDouble(yi.getText()));
                            el.setCenterX(Double.parseDouble(xi.getText()));
                            stage.close();
                        } catch (NumberFormatException e) {
                            soumettre.setPadding(new Insets(5));
                            soumettre.setText("Un nombre est invalide !");
                        }
                    });


                    general.getChildren().addAll(with, wi, x, xi, y, yi, soumettre);
                }
                if (element.getTypeSelector().equalsIgnoreCase("Line")) {
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
                    soumettre.setOnAction(event12 -> {
                        try {
                            el.setStartX(Double.parseDouble(wi.getText()));
                            el.setStartY(Double.parseDouble(hi.getText()));
                            el.setEndX(Double.parseDouble(xi.getText()));
                            el.setEndY(Double.parseDouble(yi.getText()));
                            stage.close();
                        } catch (NumberFormatException e) {
                            soumettre.setPadding(new Insets(5));
                            soumettre.setText("Un nombre est invalide !");
                        }
                    });

                    general.getChildren().addAll(with, wi, height, hi, x, xi, y, yi, soumettre);
                }


            } else {
                search.setText("Aucun élément");
            }
        });

        general.getChildren().addAll(one, result);
        general.setAlignment(Pos.CENTER);
        one.setAlignment(Pos.CENTER);

        stage.setTitle("Paramètres d'un élément");
        stage.setScene(scene);
        stage.show();


    }

    public Node search(String id) {
        for (int i = 2; i < group.getChildren().size(); i++) {
            if (group.getChildren().get(i).getId() != null && group.getChildren().get(i).getId().equalsIgnoreCase(id)) {
                return group.getChildren().get(i);
            }
        }
        return null;
    }

    public void setVisibility(boolean visible, String type) {

        String id1 = "";
        String id2 = "";

        try {

            FileReader fr = new FileReader(workspace);
            BufferedReader br = new BufferedReader(fr);

            StringBuilder all = new StringBuilder();
            String str = "";
            while (str != null) {
                str = br.readLine();
                if (str != null) {
                    all.append(str);
                }
            }

            String[] values = all.toString().split(":");

            for (String value : values) {
                String[] properties = value.split("/");
                if (properties[0].equalsIgnoreCase("Shape")) {

                    if (properties[1].equalsIgnoreCase("substract")) {
                        id1 = properties[2];
                        id2 = properties[3];
                    }

                }
            }

        } catch (IOException e) {
            System.out.println("error");
        }

        for (int i = 2; i < group.getChildren().size(); i++) {
            if (id1.equalsIgnoreCase(group.getChildren().get(i).getId()) || id2.equalsIgnoreCase(group.getChildren().get(i).getId())) {
                group.getChildren().get(i).setVisible(false);
            } else {
                if (group.getChildren().get(i).getTypeSelector().equalsIgnoreCase(type)) {
                    group.getChildren().get(i).setVisible(visible);
                }
            }
        }

    }

    public static VBox getVBox(String name, Button b) {
        Label label = new Label(name);
        label.setFont(getFont(30));
        VBox box = new VBox();
        box.getChildren().addAll(label, b);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(8));
        return box;
    }

    public static Font getFont(int size) {
        return new Font("Trebuchet MS", size);
    }

}