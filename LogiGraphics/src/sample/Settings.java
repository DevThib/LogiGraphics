package sample;

import javafx.scene.paint.Color;

import java.util.Random;

public class Settings {

    public Color randomColor(){

        Random r = new Random();

        int nb1 = r.nextInt(256);
        int nb2 = r.nextInt(256);
        int nb3 = r.nextInt(256);

        Color color = new Color(nb1,nb2,nb3,1);

        return color;

    }

}
