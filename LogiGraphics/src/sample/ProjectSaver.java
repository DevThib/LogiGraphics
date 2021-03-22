package sample;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.acl.Group;

public class ProjectSaver {

    public void saveProject(){

        if(workspaceIsTxt()) {

            try {

                FileWriter fw = new FileWriter(Main.workspace);
                BufferedWriter bw = new BufferedWriter(fw);

                for (int i = 4; i < Main.group.getChildren().size(); i++) {

                    System.out.println(Main.group.getChildren().get(i).getTypeSelector());

                    bw.newLine();
                    bw.write(":");
                    bw.newLine();

                    if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Rectangle")) {
                        Rectangle circle = (Rectangle) Main.group.getChildren().get(i);
                        bw.write("Rectangle/");
                        bw.newLine();
                        bw.write(String.valueOf(circle.getX() + "/"));
                        bw.newLine();
                        bw.write(String.valueOf(circle.getY() + "/"));
                        bw.newLine();
                        bw.write(circle.getWidth() + "," + circle.getHeight() + "/");
                        bw.newLine();
                        if (circle.getId() == null) {
                            bw.write("null/");
                        } else {
                            bw.write(circle.getId()+"/");
                        }
                        bw.newLine();
                        bw.write(String.valueOf(circle.getRotate()));

                    }
                    if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Line")) {
                        Line circle = (Line) Main.group.getChildren().get(i);
                        bw.write("Line/");
                        bw.newLine();
                        bw.write(circle.getStartX() + "," + circle.getStartY() + "/");
                        bw.newLine();
                        bw.write(circle.getEndX() + "," + circle.getEndY() + "/");
                        bw.newLine();
                        if (circle.getId() == null) {
                            bw.write("null/");
                        } else {
                            bw.write(circle.getId()+"/");
                        }
                        bw.newLine();
                        bw.write(String.valueOf(circle.getRotate()));

                    }
                    if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Circle")) {
                        Circle circle = (Circle) Main.group.getChildren().get(i);
                        bw.write("Circle/");
                        bw.newLine();
                        bw.write(String.valueOf(circle.getCenterX() + "/"));
                        bw.newLine();
                        bw.write(String.valueOf(circle.getCenterY() + "/"));
                        bw.newLine();
                        bw.write(String.valueOf(circle.getRadius()) + "/");
                        bw.newLine();
                        if (circle.getId() == null) {
                            bw.write("null/");
                        } else {
                            bw.write(circle.getId()+"/");
                        }
                        bw.newLine();
                        bw.write(String.valueOf(circle.getRotate()));

                    }
                    if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Path")) {

                        String[] types = Main.group.getChildren().get(i).getId().split(",");

                        bw.write("Shape/");
                        bw.newLine();
                        bw.write(types[0] + "/");
                        bw.newLine();
                        bw.write(types[1] + "/");
                        bw.newLine();
                        bw.write(types[2] + "/");
                        bw.newLine();
                        bw.write(String.valueOf(Main.group.getChildren().get(i).getRotate()));

                    }
                    if (Main.group.getChildren().get(i).getTypeSelector().equalsIgnoreCase("Polyline")) {

                        Polyline line = (Polyline) Main.group.getChildren().get(i);

                        String d = String.valueOf(line.getPoints());
                        char[] dd = d.toCharArray();
                        String toWrite = "";
                        for (int a = 1; a < dd.length - 1; a++) {
                            toWrite += dd[a];
                        }

                        bw.write("PolyLine/");
                        bw.newLine();
                        bw.write(toWrite + "/");
                        System.out.println(line.getPoints() + "/");
                        bw.newLine();
                        bw.write(line.getId() + "/");
                        bw.newLine();
                        bw.write(String.valueOf(line.getRotate()));

                    }
                }
                bw.newLine();
                bw.write(":");
                bw.close();
                fw.close();

            } catch (IOException e) {
                Main.error.showError();
            }

        }else{
            Main.error.showCustomError("Le fichier du projet n'est pas un fichier txt ! Veuillez changer de fichier s'il vous plait");
        }

    }

    private boolean workspaceIsTxt(){
        String[] extension = Main.workspace.getName().split("\\.");
        return extension[1].equalsIgnoreCase("txt");
    }

    //futur nouvel enregistrement du projet
    //vérifier si c'ets bien un fichier txt
}
