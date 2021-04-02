package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PropertiesOpener {

    Stage rStage = new Stage();
    Stage cStage = new Stage();
    Stage lStage = new Stage();

    public void openRectanglePropoerties(Rectangle rectangle){

        VBox general = new VBox();

        Scene scene = new Scene(general,600,400);

        if(!rStage.isShowing()) {

            Label with = new Label("Largeur :");
            TextField wi = new TextField();
            wi.setMaxWidth(150);
            wi.setMaxHeight(10);
            wi.setText(String.valueOf(rectangle.getWidth()));
            Label height = new Label("Hauteur :");
            TextField hi = new TextField();
            hi.setMaxWidth(150);
            hi.setMaxHeight(10);
            hi.setText(String.valueOf(rectangle.getHeight()));
            Label x = new Label("Position X :");
            TextField xi = new TextField();
            xi.setMaxWidth(150);
            xi.setMaxHeight(10);
            xi.setText(String.valueOf(rectangle.getX()));
            Label y = new Label("Position Y :");
            TextField yi = new TextField();
            yi.setMaxWidth(150);
            yi.setMaxHeight(10);
            yi.setText(String.valueOf(rectangle.getY()));
            Label r = new Label("Rotation :");
            TextField ri = new TextField();
            ri.setMaxWidth(150);
            ri.setMaxHeight(10);
            ri.setText(String.valueOf(rectangle.getRotate()));

            Button soumettre = new Button("Modifier");
            soumettre.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        rectangle.setWidth(Double.parseDouble(wi.getText()));
                        rectangle.setHeight(Double.parseDouble(hi.getText()));
                        rectangle.setX(Double.parseDouble(xi.getText()));
                        rectangle.setY(Double.parseDouble(yi.getText()));
                        rectangle.setRotate(Double.parseDouble(ri.getText()));
                        rStage.close();
                        Main.save();
                    } catch (NumberFormatException e) {
                        soumettre.setPadding(new Insets(5));
                        soumettre.setText("Un nombre est invalide !");
                    }
                }
            });

            general.getChildren().addAll(with, wi, height, hi, x, xi, y, yi, r, ri, soumettre);
            general.setAlignment(Pos.CENTER);

            rStage.setTitle("Paramètres d'un élément");
            rStage.setScene(scene);
            rStage.show();
        }
    }

    public void openCircleProperties(Circle circle){

        if(!cStage.isShowing()) {

            VBox general = new VBox();

            Scene scene = new Scene(general, 600, 400);

            Label with = new Label("Rayon :");
            TextField wi = new TextField();
            wi.setMaxWidth(150);
            wi.setMaxHeight(10);
            wi.setText(String.valueOf(circle.getRadius()));
            Label x = new Label("Position X :");
            TextField xi = new TextField();
            xi.setMaxWidth(150);
            xi.setMaxHeight(10);
            xi.setText(String.valueOf(circle.getCenterX()));
            Label y = new Label("Position Y :");
            TextField yi = new TextField();
            yi.setMaxWidth(150);
            yi.setMaxHeight(10);
            yi.setText(String.valueOf(circle.getCenterY()));

            Button soumettre = new Button("Modifier");
            soumettre.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        circle.setRadius(Double.parseDouble(wi.getText()));
                        circle.setCenterY(Double.parseDouble(yi.getText()));
                        circle.setCenterX(Double.parseDouble(xi.getText()));
                        cStage.close();
                    } catch (NumberFormatException e) {
                        soumettre.setPadding(new Insets(5));
                        soumettre.setText("Un nombre est invalide !");
                    }
                }
            });


            general.getChildren().addAll(with, wi, x, xi, y, yi, soumettre);
            general.setAlignment(Pos.CENTER);

            cStage.setTitle("Paramètres d'un élément");
            cStage.setScene(scene);
            cStage.show();
        }

    }

    public void openLineProperties(Line line){

        if(!lStage.isShowing()) {

            VBox general = new VBox();

            Scene scene = new Scene(general, 600, 400);

            Label with = new Label("Position X du début :");
            TextField wi = new TextField();
            wi.setMaxWidth(150);
            wi.setMaxHeight(10);
            wi.setText(String.valueOf(line.getStartX()));
            Label height = new Label("Position Y du début :");
            TextField hi = new TextField();
            hi.setMaxWidth(150);
            hi.setMaxHeight(10);
            hi.setText(String.valueOf(line.getStartY()));
            Label x = new Label("Position X de la fin :");
            TextField xi = new TextField();
            xi.setMaxWidth(150);
            xi.setMaxHeight(10);
            xi.setText(String.valueOf(line.getEndX()));
            Label y = new Label("Position Y de la fin :");
            TextField yi = new TextField();
            yi.setMaxWidth(150);
            yi.setMaxHeight(10);
            yi.setText(String.valueOf(line.getEndY()));

            Button soumettre = new Button("Modifier");
            soumettre.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        line.setStartX(Double.parseDouble(wi.getText()));
                        line.setStartY(Double.parseDouble(hi.getText()));
                        line.setEndX(Double.parseDouble(xi.getText()));
                        line.setEndY(Double.parseDouble(yi.getText()));
                        lStage.close();
                    } catch (NumberFormatException e) {
                        soumettre.setPadding(new Insets(5));
                        soumettre.setText("Un nombre est invalide !");
                    }
                }
            });

            general.getChildren().addAll(with, wi, height, hi, x, xi, y, yi, soumettre);
            general.setAlignment(Pos.CENTER);

            lStage.setTitle("Paramètres d'un élément");
            lStage.setScene(scene);
            lStage.show();
        }
    }

    public void openPolyLineProperties(Polyline polyline){

    }

}
