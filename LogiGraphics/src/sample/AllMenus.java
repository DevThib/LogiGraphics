package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AllMenus {

    MenuBar bar = new MenuBar();

    Menu formes = new Menu("Construire");
    Menu functions = new Menu("Fonctions");
    Menu files = new Menu("Fichiers");
    Menu elements = new Menu("Elements");
    Menu visibilty = new Menu("Visibilité");
    Menu settings = new Menu("Paramètres");

    public AllMenus() {

        MenuItem rectangle = new MenuItem("🟫 Rectangle");
        rectangle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Main.isOnAction) {
                    Main.group.getChildren().remove(Main.group.getChildren().size() - 1);
                }
                Main.isOnAction = false;
                Main.nodeAction = null;
                Main.selector = 1;
                Main.affChange();
                Main.pline = null;
            }
        });

        MenuItem circle = new MenuItem("⭕ Cercle");
        circle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Main.isOnAction) {
                    Main.group.getChildren().remove(Main.group.getChildren().size() - 1);
                }
                Main.isOnAction = false;
                Main.nodeAction = null;
                Main.selector = 2;
                Main.affChange();
                Main.pline = null;
            }
        });

        MenuItem line = new MenuItem("➖ Ligne");
        line.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Main.isOnAction) {
                    Main.group.getChildren().remove(Main.group.getChildren().size() - 1);
                }
                Main.isOnAction = false;
                Main.nodeAction = null;
                Main.selector = 3;
                Main.affChange();
                Main.pline = null;
            }
        });

        MenuItem polyline = new MenuItem("〰 MultiLigne");
        polyline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Main.isOnAction) {
                    Main.group.getChildren().remove(Main.group.getChildren().size() - 1);
                }
                Main.isOnAction = false;
                Main.nodeAction = null;
                Main.selector = 4;
                Main.affChange();
                Main.pline = null;
            }
        });

        MenuItem free = new MenuItem("🖱 Libre");
        free.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.isOnAction = false;
                Main.nodeAction = null;
                Main.selector = 5;
                Main.affChange();
                Main.pline = null;
            }
        });

        formes.getItems().addAll(rectangle, circle, line, polyline, free);

        MenuItem back = new MenuItem("↩ Retour");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.back();
                updateElements();
            }
        });

        MenuItem all = new MenuItem("❌ Effacer tout");
        all.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage stage = new Stage();
                VBox box = new VBox();
                Scene scene = new Scene(box,700,350);

                Label label = new Label("Voulez vous vraiment effacer tout les éléments ?");
                label.setFont( new Font("Trebuchet MS",20));

                VBox bb = new VBox();
                VBox bbb = new VBox();

                FlowPane pane = new FlowPane();
                Button b1 = new Button("Oui");
                Button b2 = new Button("Non");

                b1.setOnAction(event1 -> {
                    stage.close();
                    deleteAll();
                });

                b2.setOnAction(event12 -> {
                    stage.close();
                });

                b1.setFont( new Font("Trebuchet MS",30));
            //    b1.setTextFill(Color.RED);
             //   b2.setTextFill(Color.GREEN);
                b2.setFont(new Font("Trebuchet MS",30));

                bb.getChildren().addAll(b1);
                bb.setPadding(new Insets(20));
                bbb.getChildren().addAll(b2);
                bbb.setPadding(new Insets(20));

                if(!pane.getChildren().contains(b1) && !pane.getChildren().contains(b2)){
                    pane.getChildren().addAll(bb,bbb);
                }

                if(!box.getChildren().contains(label) && !pane.getChildren().contains(pane)){
                    box.getChildren().addAll(label,pane);
                }

                box.setAlignment(Pos.CENTER);
                pane.setAlignment(Pos.CENTER);

                stage.setScene(scene);
                stage.show();

            }
        });


        MenuItem eraser = new MenuItem("✂ Découper");
        eraser.setOnAction(event -> {
            if(Main.isOnEraser){
                Main.isOnEraser = false;
            }else{
                Main.isOnEraser = true;
            }
            Main.affChange();

        });
     /*   eraser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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
                soumet.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Node element1 = Main.search(id1.getText());
                        Node element2 = Main.search(id2.getText());

                        if (element1 != null && element2 != null) {

                            Shape shape1 = (Shape) element1;
                            Shape shape2 = (Shape) element2;

                            Shape shape = Shape.subtract(shape1, shape2);

                            Main.group.getChildren().addAll(shape);
                            element1.setVisible(false);
                            element2.setVisible(false);
                            stage.close();
                            shape.setId("substract," + element1.getId() + "," + element2.getId());
                            Main.save();

                        } else {
                            soumet.setText("Un identifiant est incorrect");
                        }
                    }
                });

                general.getChildren().addAll(text, pane, soumet);
                general.setAlignment(Pos.CENTER);

                pane.setAlignment(Pos.CENTER);

                stage.setScene(scene);
                stage.setTitle("Gomme");
                stage.show();

            }
        });

      */

        MenuItem gravity = new MenuItem("🛩 Gravité");
        gravity.setOnAction(event -> {

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

                for (int i = 4; i < Main.group.getChildren().size(); i++) {
                    Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
                }
                for (int i = 4; i < Main.group.getChildren().size(); i++) {
                    Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
                }
                for (int i = 4; i < Main.group.getChildren().size(); i++) {
                    Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
                }
                for (int i = 4; i < Main.group.getChildren().size(); i++) {
                    Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
                }
                for (int i = 4; i < Main.group.getChildren().size(); i++) {
                    Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
                }
                for (int i = 4; i < Main.group.getChildren().size(); i++) {
                    Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
                }
                Main.numberOfCircle = 0;
                Main.numberOfLine = 0;
                Main.numberOfRectangle = 0;
                Main.workspace = file;
                char[] na = Main.workspace.getName().toCharArray();
                String name = "";
                for (int i = 0; i < na.length - 4; i++) {
                    name += na[i];
                }
                Main.primary.setTitle("LogiGraphics - " + name);
                Main.openProject(Main.workspace);

            } catch (IOException e) {
                Main.error.showError();
            }


        });

        MenuItem open = new MenuItem("📁 Ouvrir");
        open.setOnAction(event -> {

            Main.numberOfCircle = 0;
            Main.numberOfLine = 0;
            Main.numberOfRectangle = 0;
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Sélectionner un projet");
            Main.workspace = chooser.showOpenDialog(Main.primary);
            char[] na = Main.workspace.getName().toCharArray();
            String name = "";
            for (int i = 0; i < na.length - 4; i++) {
                name += na[i];
            }
            for (int i = 4; i < Main.group.getChildren().size(); i++) {
                Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
            }
            for (int i = 4; i < Main.group.getChildren().size(); i++) {
                Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
            }
            for (int i = 4; i < Main.group.getChildren().size(); i++) {
                Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
            }
            for (int i = 4; i < Main.group.getChildren().size(); i++) {
                Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
            }
            for (int i = 4; i < Main.group.getChildren().size(); i++) {
                Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
            }
            for (int i = 4; i < Main.group.getChildren().size(); i++) {
                Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
            }
            Main.primary.setTitle("LogiGraphics - " + name);
            Main.openProject(Main.workspace);
        });

        MenuItem save = new MenuItem("✅ Enregistrer");
        save.setOnAction(event -> Main.save());

        MenuItem superposer = new MenuItem("📃 Superposer");
        superposer.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            try {
                chooser.setTitle("Sélectionner un projet");
                Main.superpose = chooser.showOpenDialog(Main.primary);
                Main.openProject(Main.superpose);
            } catch (NullPointerException e) {
            }
        });

        files.getItems().addAll(create, open, save, superposer);

        MenuItem rec = new MenuItem("🟫 Visible");
        rec.setOnAction(event -> {
            if (Main.rVis) {
                Main.rVis = false;
                Main.setVisibilty(false, "Rectangle");
                rec.setText("🟫 Invisible");
            } else {
                Main.rVis = true;
                Main.setVisibilty(true, "Rectangle");
                rec.setText("🟫 Visible");
            }
        });

        MenuItem cir = new MenuItem("⭕ Visible");
        cir.setOnAction(event -> {
            if (Main.rCi) {
                Main.rCi = false;
                Main.setVisibilty(false, "Circle");
                cir.setText("⭕ Invisible");
            } else {
                Main.rCi = true;
                Main.setVisibilty(true, "Circle");
                cir.setText("⭕ Visible");
            }
        });

        MenuItem li = new MenuItem("➖ Visible");
        li.setOnAction(event -> {
            if (Main.rLi) {
                Main.rLi = false;
                Main.setVisibilty(false, "Line");
                li.setText("〰 Invisible");
            } else {
                Main.rLi = true;
                Main.setVisibilty(true, "Line");
                li.setText("〰 Visible");
            }
        });

        MenuItem mli = new MenuItem("〰 Visible");
        mli.setOnAction(event -> {
            if (Main.mLi) {
                Main.mLi = false;
                Main.setVisibilty(false, "Polyline");
                mli.setText("〰 Invisible");
            } else {
                Main.mLi = true;
                Main.setVisibilty(true, "Polyline");
                mli.setText("〰 Visible");
            }
        });

        MenuItem sh = new MenuItem("🌙 Visible");
        sh.setOnAction(event -> {
            if (Main.rSh) {
                Main.rSh = false;
                Main.setVisibilty(false, "Path");
                sh.setText("🌙 Invisible");
            } else {
                Main.rSh = true;
                Main.setVisibilty(true, "Path");
                sh.setText("🌙 Visible");
            }
        });


        visibilty.getItems().addAll(rec, cir, li, mli, sh);

        updateElements();
        MenuItem item = new MenuItem("Aucun élément dans le projet");
        elements.getItems().add(item);


        /////////////////////////////////////////////////////////////////////////////////////////

        //paramètres


        bar.getMenus().addAll(files, formes, functions, visibilty, elements, settings);
        //il y en aura un autre "autres" pour les paramètres,l'aide etc...

    }


    public MenuBar getBar() {
        return bar;
    }

    public void updateElements() {

        PropertiesOpener opener = new PropertiesOpener();

        for (int i = 0; i < elements.getItems().size(); i++) {
            elements.getItems().remove(i);
        }
        for (int i = 0; i < elements.getItems().size(); i++) {
            elements.getItems().remove(i);
        }
        for (int i = 0; i < elements.getItems().size(); i++) {
            elements.getItems().remove(i);
        }
        for (int i = 3; i < Main.group.getChildren().size(); i++) {
            if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Rectangle")) {
                int a = i;
                MenuItem item = new MenuItem("🟫 Rectangle");
                item.setOnAction(event -> {
                    opener.openRectanglePropoerties((Rectangle) Main.group.getChildren().get(a));
                });
                elements.getItems().add(item);
            }
            if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")) {
                int a = i;
                MenuItem item = new MenuItem("⭕ Circle");
                item.setOnAction(event -> {
                    opener.openCircleProperties((Circle) Main.group.getChildren().get(a));
                });
                elements.getItems().add(item);
            }
            if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Line")) {
                int a = i;
                MenuItem item = new MenuItem("➖ Ligne");
                item.setOnAction(event -> {
                    opener.openLineProperties((Line) Main.group.getChildren().get(a));
                });
                elements.getItems().add(item);
            }
            if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Polyline")) {
                int a = i;
                MenuItem item = new MenuItem("〰 MultiLigne");
                item.setOnAction(event -> {
                    opener.openPolyLineProperties((Polyline) Main.group.getChildren().get(a));
                });
                elements.getItems().add(item);
            }
        }
        if (Main.group.getChildren().size() == 4) {
            MenuItem item = new MenuItem("Aucun élément dans le projet");
            elements.getItems().add(item);
        }
    }

    public void deleteAll() {
        for(int i = 4; i < Main.group.getChildren().size();i++){
            Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
        }
        for(int i = 4; i < Main.group.getChildren().size();i++){
            Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
        }
        for(int i = 4; i < Main.group.getChildren().size();i++){
            Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
        }
        for(int i = 4; i < Main.group.getChildren().size();i++){
            Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
        }
        for(int i = 4; i < Main.group.getChildren().size();i++){
            Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
        }
        for(int i = 4; i < Main.group.getChildren().size();i++){
            Main.group.getChildren().removeAll(Main.group.getChildren().get(i));
        }
        Main.numberOfRectangle = 0;
        Main.numberOfLine = 0;
        Main.numberOfCircle = 0;
        updateElements();
    }
}
