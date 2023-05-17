package sample.logigraphics.stuff;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.logigraphics.Logiciel;
import sample.logigraphics.interfaces.LogicielStructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Debug {

    public static String getExtension(File file){
        String[] e = file.getName().split("\\.");
        return e[e.length-1];
    }

    public static ImageView getIcon(String code, LogicielStructure logicielStructure,double size){
        if(code.equalsIgnoreCase("chart")){
            ImageView imageView = null;
            try {
                imageView = new ImageView(new Image(new FileInputStream(logicielStructure.getLogiciel().getDataBase().getDirectoryByName("cache").getFileByName("graph.png"))));
            } catch (FileNotFoundException e) {e.printStackTrace();}
            imageView.setFitWidth(20*size);
            imageView.setFitHeight(20*size);
            return imageView;
        }
        if(code.equalsIgnoreCase("colors")){
            ImageView imageView = null;
            try {
                imageView = new ImageView(new Image(new FileInputStream(logicielStructure.getLogiciel().getDataBase().getDirectoryByName("cache").getFileByName("colors.png"))));
            } catch (FileNotFoundException e) {e.printStackTrace();}
            imageView.setFitWidth(20*size);
            imageView.setFitHeight(20*size);
            return imageView;
        }
        if(code.equalsIgnoreCase("keyboard")){
            ImageView imageView = null;
            try {
                imageView = new ImageView(new Image(new FileInputStream(logicielStructure.getLogiciel().getDataBase().getDirectoryByName("cache").getFileByName("keyboard.png"))));
            } catch (FileNotFoundException e) {e.printStackTrace();}
            imageView.setFitWidth(20*size);
            imageView.setFitHeight(20*size);
            return imageView;
        }
        return null;
    }

}
