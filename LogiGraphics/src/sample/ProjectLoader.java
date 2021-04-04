package sample;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static sample.Main.menus;
import static sample.Main.search;

public class ProjectLoader {

    public void loadProject(File fileToOpen){

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

                    Main.rectangleCreator.createRectangle();
                    menus.updateElements();
                    Rectangle r = Main.rectangleCreator.getAction();

                    r.setY(posY);
                    r.setX(posX);
                    r.setWidth(Double.parseDouble(pr[0]));
                    r.setHeight(Double.parseDouble(pr[1]));
                    r.setId(properties[4]);
                    if(properties[4].equalsIgnoreCase("eraser")){
                        r.setFill(Color.GREY);
                    }
                    r.setRotate(Double.parseDouble(properties[5]));
                    Main.group.getChildren().add(r);
                    Main.numberOfRectangle++;

                }
                if(properties[0].equalsIgnoreCase("Circle")){

                    double posX = Double.parseDouble(properties[1]);
                    double posY = Double.parseDouble(properties[2]);
                    double radius = Double.parseDouble(properties[3]);

                    Main.circleCreator.createCircle();
                    Main.menus.updateElements();
                    Circle c  = Main.circleCreator.getAction();
                    c.setCenterY(posY);
                    c.setCenterX(posX);
                    c.setRadius(radius);
                    c.setId(properties[4]);
                    if(properties[4].equalsIgnoreCase("eraser")){
                        c.setFill(Color.GREY);
                    }
                    c.setRotate(Double.parseDouble(properties[5]));
                    Main.group.getChildren().add(c);
                    Main.numberOfCircle++;

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
                    l.setRotate(Double.parseDouble(properties[4]));
                    Main.group.getChildren().add(l);
                    Main.numberOfLine++;

                }
                if(properties[0].equalsIgnoreCase("Polyline")){

                    String[] val = properties[1].split(",");

                    Polyline l = new Polyline();

                    for(int a = 0; a < val.length; a++){
                        l.getPoints().addAll(Double.valueOf(val[a]));
                    }

                    l.setId(properties[2]);
                    l.setRotate(Double.parseDouble(properties[3]));
                    Main.group.getChildren().add(l);
                    Main.numberOfPolyLine++;

                }

            }
            for(int i = 0; i < values.length; i++){
                String[] properties = values[i].split("/");
                if(properties[0].equalsIgnoreCase("Shape")){

                    if(properties[1].equalsIgnoreCase("substract")){
                        Shape shape1 = (Shape) search(properties[2]);
                        Shape shape2 = (Shape) search(properties[3]);
                        Shape shape = Shape.subtract(shape1,shape2);

                        for(int a = 0; a < Main.group.getChildren().size(); a++){
                            if(properties[2].equalsIgnoreCase(Main.group.getChildren().get(a).getId()) || properties[3].equalsIgnoreCase(Main.group.getChildren().get(a).getId())){
                                Main.group.getChildren().get(a).setVisible(false);
                            }
                        }
                        shape.setRotate(Double.parseDouble(properties[4]));
                        Main.group.getChildren().addAll(shape);
                    }

                }
            }


        }catch (IOException e){
            Main.error.showError();
        }

    }

}
