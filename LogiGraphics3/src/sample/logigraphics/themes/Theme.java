package sample.logigraphics.themes;

import fr.devthib.databaseapi.Directory;
import fr.devthib.databaseapi.thens.ThenI;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import sample.logigraphics.Logiciel;

import java.io.File;
import java.util.ArrayList;

public class Theme {

    Logiciel logiciel;

    Color color1;
    Color color2;

    public Theme(Logiciel logiciel){
        this.logiciel = logiciel;
    }

    public void apply(){
        logiciel.getTopMenuBar().getMenuBar().setBackground(new Background(new BackgroundFill(color1,new CornerRadii(0),new Insets(0))));
        logiciel.getTopBar().changeColor(color2);
    }

    public void setCustomColors(Color color1,Color color2){
        this.color1 = color1;
        this.color2 = color2;
    }

    public void createCustomTheme(int red1,int green1,int blue1,int red2,int green2,int blue2){
        Directory directory = logiciel.getDataBase().getDirectoryByName("themes");

        for(int i = 0; i < 50; i++){
            if(!directory.containsFile("theme"+i+".txt")){
                int finalI = i;
                directory.createFile("theme"+i+".txt").then(() -> directory.saveInFile("theme"+ finalI +".txt",red1+","+green1+","+blue1+"/"+red2+","+green2+","+blue2));
                return;
            }
        }

    }

    public void removeCustomTheme(int index){
        try {
            logiciel.getDataBase().getDirectoryByName("themes").getFileByName("theme" + index + ".txt").delete();
        }catch (NullPointerException e){}
    }

    public ArrayList<Theme> getCustomThemes(){

        ArrayList<Theme> themes = new ArrayList<>();

        for(File file : logiciel.getDataBase().getDirectoryByName("themes").getFiles()){
            Theme theme = new Theme(logiciel);

            String[] colors = logiciel.getDataBase().getDirectoryByName("themes").readLineFile(file,0).split("/");

            String[] color1 = colors[0].split(",");
            String[] color2 = colors[1].split(",");

            theme.setCustomColors(Color.rgb(Integer.parseInt(color1[0]),Integer.parseInt(color1[1]),Integer.parseInt(color1[2])),Color.rgb(Integer.parseInt(color2[0]),Integer.parseInt(color2[1]),Integer.parseInt(color2[2])));

            themes.add(theme);
        }
        return themes;
    }

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

}
