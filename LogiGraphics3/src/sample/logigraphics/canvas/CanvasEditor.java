package sample.logigraphics.canvas;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import sample.logigraphics.Logiciel;
import sample.logigraphics.creation.Point;
import sample.logigraphics.interfaces.LogicielStructure;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CanvasEditor {

    public static void blackAndWhite(Canvas canvas, File image, double percentage, LogicielStructure logicielStructure,boolean cursor){

        try {

            Image image1 = new Image(new FileInputStream(image));

            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            PixelReader pixelReader = image1.getPixelReader();

            int color;
            int red;
            int blue;
            int green;
            double grey;

            logicielStructure.getLogiciel().setTreating(true);
            for(int i = 0; i < canvas.getHeight(); i++) {
                for (int a = 0; a < canvas.getWidth(); a++) {
                    color = pixelReader.getArgb(a, i);
                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;
                    grey = (red + blue + green) / 3 * (percentage / 50);
                    if (grey > 255) grey = 255;
                    canvas.getGraphicsContext2D().setFill(Color.rgb((int) grey, (int) grey, (int) grey));
                    canvas.getGraphicsContext2D().fillRect(a, i, 1, 1);
                }
            }
            //if(!cursor)savePicture(canvas,logicielStructure);
            logicielStructure.getLogiciel().setTreating(false);

        }catch (IOException ee){}

    }

    public static void invertColors(Canvas canvas,File image,LogicielStructure logicielStructure){

        try {

            Image image1 = new Image(new FileInputStream(image));

            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            PixelReader pixelReader = image1.getPixelReader();

            int color;
            int red;
            int blue;
            int green;
            int invertRed;
            int invertBlue;
            int invertGreen;

            logicielStructure.getLogiciel().setTreating(true);
            for(int i = 0; i < canvas.getHeight(); i++){
                for (int a = 0; a < canvas.getWidth(); a++) {
                    color = pixelReader.getArgb(a, i);
                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;
                    invertRed = 255 - red;
                    invertGreen = 255 - green;
                    invertBlue = 255 - blue;
                    canvas.getGraphicsContext2D().setFill(Color.rgb(invertRed,invertGreen,invertBlue));
                    canvas.getGraphicsContext2D().fillRect(a, i, 1, 1);
                }
            }
            //savePicture(canvas,logicielStructure);
            logicielStructure.getLogiciel().setTreating(false);

        }catch (IOException  ee){}

    }

    public static void removeBackground(Canvas canvas,File image,LogicielStructure logicielStructure,double epsilon){

        try {

            Image image1 = new Image(new FileInputStream(image));

            PixelReader pixelReader = image1.getPixelReader();

            HashMap<Color,Integer> colors = new HashMap<>();
            ArrayList<Color> cols = new ArrayList<>();

            HashMap<Integer,double[]> colos = new HashMap<>();

            int index = 0;

            logicielStructure.getLogiciel().setTreating(true);
            for(int i = 0; i < canvas.getHeight(); i++){
                for (int a = 0; a < canvas.getWidth(); a++) {
                    Color color1 = pixelReader.getColor(a,i);
                    if(!colors.containsKey(color1)){
                        colors.put(color1,1);
                        cols.add(color1);
                        colos.put(a*i,new double[]{(pixelReader.getArgb(a,i) >> 16) & 0xff,(pixelReader.getArgb(a,i) >> 8) & 0xff,pixelReader.getArgb(a,i) & 0xff});
                    }else{
                        colors.replace(color1,colors.get(color1)+1);
                    }
                }
            }

            int mostUsed = 0;

            for(int i = 0; i < cols.size(); i++){
                Color color = cols.get(i);
                if(colors.get(color) > mostUsed){
                    mostUsed = colors.get(color);
                    index = i;
                }
            }

            for(int i = 0; i < canvas.getHeight(); i++){
                for (int a = 0; a < canvas.getWidth(); a++) {
                    Color color1 = pixelReader.getColor(a,i);
                    try {
                        if (approxColor((pixelReader.getArgb(a, i) >> 16) & 0xff, (pixelReader.getArgb(a, i) >> 8) & 0xff, pixelReader.getArgb(a, i) & 0xff, colos.get(index)[0], colos.get(index)[1], colos.get(index)[2], epsilon)) {
                            canvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
                        } else {
                            canvas.getGraphicsContext2D().setFill(color1);
                        }
                    }catch (NullPointerException e){e.printStackTrace();}
                    canvas.getGraphicsContext2D().fillRect(a, i, 1, 1);
                }
            }

            logicielStructure.getLogiciel().setTreating(false);

        }catch (IOException ee){}

    }

    public static void changeToDraw(Canvas canvas,File image,LogicielStructure logicielStructure,double epsilon){

        try {

            blackAndWhite(canvas,image,50,logicielStructure,false);

            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight() - 2);
            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", new File(logicielStructure.getLogiciel().getDataBase().getLocation() + "\\.logiGraphics\\temporary\\temporary.png"));

            Image image1 = new Image(new FileInputStream(new File(logicielStructure.getLogiciel().getDataBase().getLocation() + "\\.logiGraphics\\temporary\\temporary.png")));

            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            PixelReader pixelReader = image1.getPixelReader();

            HashMap<Color,Integer> colors = new HashMap<>();
            ArrayList<Color> cols = new ArrayList<>();

            HashMap<Integer,double[]> colos = new HashMap<>();

            int index = 0;

            logicielStructure.getLogiciel().setTreating(true);
            for(int i = 0; i < canvas.getHeight(); i++){
                for (int a = 0; a < canvas.getWidth(); a++) {
                    Color color1 = pixelReader.getColor(a,i);
                    if(!colors.containsKey(color1)){
                        colors.put(color1,1);
                        cols.add(color1);
                        colos.put(a*i,new double[]{(pixelReader.getArgb(a,i) >> 16) & 0xff,(pixelReader.getArgb(a,i) >> 8) & 0xff,pixelReader.getArgb(a,i) & 0xff});
                    }else{
                        colors.replace(color1,colors.get(color1)+1);
                    }
                }
            }

            int mostUsed = 0;

            for(int i = 0; i < cols.size(); i++){
                Color color = cols.get(i);
                if(colors.get(color) > mostUsed){
                    mostUsed = colors.get(color);
                    index = i;
                }
            }

            for(int i = 0; i < canvas.getHeight(); i++){
                for (int a = 0; a < canvas.getWidth(); a++) {
                    Color color1 = pixelReader.getColor(a,i);
                    if(approxColor((pixelReader.getArgb(a,i) >> 16) & 0xff,(pixelReader.getArgb(a,i) >> 8) & 0xff,pixelReader.getArgb(a,i) & 0xff,colos.get(index)[0], colos.get(index)[1],colos.get(index)[2],epsilon)){
                        canvas.getGraphicsContext2D().setFill(Color.WHITE);
                    }else{
                        canvas.getGraphicsContext2D().setFill(color1);
                    }
                    canvas.getGraphicsContext2D().fillRect(a, i, 1, 1);
                }
            }

            logicielStructure.getLogiciel().setTreating(false);




        }catch (IOException  ee){}

    }

    private static void savePicture(Canvas canvas, LogicielStructure logicielStructure){
        try {
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight() - 2);
            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", new File(logicielStructure.getLogiciel().getDataBase().getLocation() + "\\.logiGraphics\\temporary\\temporary.png"));
            logicielStructure.openImage(new File(logicielStructure.getLogiciel().getDataBase().getLocation() + "\\.logiGraphics\\temporary\\temporary.png"));
        }catch (IOException e){}
    }

    private static boolean approxColor(int red,int blue,int green,double red1,double blue1,double green1,double epsilon){
        if(red-epsilon < red1 && red+epsilon > red1){
            if(green-epsilon < green1 && green+epsilon > green1){
                return blue-epsilon < blue1 && blue+epsilon > blue1;
            }
        }
        return false;
    }
}
