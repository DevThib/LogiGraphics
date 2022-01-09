package sample.logigraphics.projects;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.logigraphics.Logiciel;
import sample.logigraphics.creation.Shape;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Project {

    Rectangle paper = new Rectangle(0,0,2000,2000);

    Logiciel logiciel;

    File project;

    String name;

    boolean hasBeenSaved = false;

    public Project(Logiciel logiciel,String name){
        this.logiciel = logiciel;
        this.name = name;
    }

    public void load(){

    }

    public void startNew(){
        if(hasBeenSaved){
            logiciel.stop();
            logiciel = new Logiciel();
            logiciel.start();
        }else{
           save(true);
        }
    }

    public void save(boolean show){

        try {

            BufferedImage bufferedImage = new BufferedImage(1500, 800, BufferedImage.TYPE_INT_RGB);

            Graphics2D g = bufferedImage.createGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 1500, 800);
            g.setColor(Color.BLACK);

            for (Shape shape : logiciel.getShapeCreator().getShapes()) {

                g.setPaint(convert(shape.getColor()));

                switch (shape.getShapeType()) {

                    case RECTANGLE:
                        Rectangle r = (Rectangle) shape.get();
                        g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
                        break;

                    case CIRCLE:
                        Circle c = (Circle) shape.get();
                        g.fillOval((int) c.getCenterX(), (int) c.getCenterY(), (int) c.getRadius(), (int) c.getRadius());
                        break;

                    case LINE:
                        g.setPaint(Color.BLACK);
                        Line l = (Line) shape.get();
                        g.drawLine((int) l.getStartX(), (int) l.getStartY(), (int) l.getEndX(), (int) l.getEndY());
                        break;

                    case NONFILLEDRECTANGLE:
                        javafx.scene.control.Button b = (Button) shape.get();
                        g.drawRect((int) b.getTranslateX(), (int) b.getTranslateY(), (int) b.getMaxWidth(), (int) b.getMinHeight());
                        break;

                    case ELLIPSE:
                        Ellipse ellipse = (Ellipse) shape.get();
                        g.fillOval((int) ellipse.getCenterX(), (int) ellipse.getCenterY(), (int) ellipse.getRadiusX(), (int) ellipse.getRadiusY());
                        break;

                    case IMAGE:
                        ImageView imageView = (ImageView) shape.get();

                        //a faire

                        break;


                }
            }
            g.dispose();

            if (show) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Enregistrer le projet");
                fileChooser.setInitialFileName(name + ".jpg");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image (*JPG)", "*.jpg"));

                project = fileChooser.showSaveDialog(new Stage());
                name = project.getName().split("\\.")[0];
                hasBeenSaved = true;
            }

            try {
                logiciel.getStage().setTitle("LogiGraphics - "+name);
                ImageIO.write(bufferedImage, "png", project);
            } catch (IOException | IllegalArgumentException e) {}

        } catch (NullPointerException e) {
            hasBeenSaved = false;
        }

    }

    public String getName() {
        return name;
    }

    public Color convert(javafx.scene.paint.Color color){
        if(color.equals(javafx.scene.paint.Color.BLACK)){
            return Color.BLACK;
        }
        if(color.equals(javafx.scene.paint.Color.RED)){
            return Color.RED;
        }
        if(color.equals(javafx.scene.paint.Color.WHITE)){
            return Color.WHITE;
        }
        if(color.equals(javafx.scene.paint.Color.YELLOW)){
            return Color.YELLOW;
        }
        if(color.equals(javafx.scene.paint.Color.PURPLE)){
            return Color.MAGENTA;
        }
        if(color.equals(javafx.scene.paint.Color.BLUE)){
            return Color.BLUE;
        }
        if(color.equals(javafx.scene.paint.Color.GREEN)){
            return Color.GREEN;
        }
        if(color.equals(javafx.scene.paint.Color.LIGHTGRAY)){
            return Color.LIGHT_GRAY;
        }
        return null;
    }

    public boolean hasBeenSaved() {
        return hasBeenSaved;
    }
}
