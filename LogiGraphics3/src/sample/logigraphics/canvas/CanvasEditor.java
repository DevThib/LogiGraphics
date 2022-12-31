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

         /*   WritableImage writableImage = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight()-2);
            canvas.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", new File(logicielStructure.getLogiciel().getDataBase().getLocation()+"\\.logiGraphics\\temporary\\converting.png"));

            File pict = new File(logicielStructure.getLogiciel().getDataBase().getLocation()+"\\.logiGraphics\\temporary\\converting.png");

            Image image1 = new Image(new FileInputStream(pict));

            pict.delete();

          */

            Image image1 = new Image(new FileInputStream(image));

            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            PixelReader pixelReader = image1.getPixelReader();

            /*Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int i = 0;
                int color;
                int red;
                int blue;
                int green;
                double grey;
                @Override
                public void run() {
                   if(i < canvas.getHeight()){
                       for (int a = 0; a < canvas.getWidth(); a++) {
                           color = pixelReader.getArgb(a, i);
                           red = (color >> 16) & 0xff;
                           green = (color >> 8) & 0xff;
                           blue = color & 0xff;
                           grey = (red + blue + green) / 3 * (percentage/50);
                           if(grey > 255)grey = 255;
                           canvas.getGraphicsContext2D().setFill(Color.rgb((int) grey, (int) grey, (int) grey));
                           canvas.getGraphicsContext2D().fillRect(a, i, 1, 1);
                       }

                   }else{
                       cancel();
                   }
                   i++;
                }
            },2,2);

             */


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

    public static void removeBackground(Canvas canvas,File image,LogicielStructure logicielStructure){

        try {

            Image image1 = new Image(new FileInputStream(image));

            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            PixelReader pixelReader = image1.getPixelReader();

            HashMap<Color,Integer> colors = new HashMap<>();
            ArrayList<Color> cols = new ArrayList<>();

            logicielStructure.getLogiciel().setTreating(true);
            for(int i = 0; i < canvas.getHeight(); i++){
                for (int a = 0; a < canvas.getWidth(); a++) {
                    Color color1 = pixelReader.getColor(a,i);
                    if(!colors.containsKey(color1)){
                        colors.put(color1,1);
                        cols.add(color1);
                    }else{
                        colors.replace(color1,colors.get(color1)+1);
                    }
                }
            }

            int mostUsed = 0;

            for(Color color : cols){
                if(colors.get(color) > mostUsed){
                    mostUsed = colors.get(color);
                }
            }

            for(int i = 0; i < canvas.getHeight(); i++){
                for (int a = 0; a < canvas.getWidth(); a++) {
                    Color color1 = pixelReader.getColor(a,i);
                    if(colors.get(color1) == mostUsed){
                        canvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
                    }else{
                        canvas.getGraphicsContext2D().setFill(color1);
                    }
                    canvas.getGraphicsContext2D().fillRect(a, i, 1, 1);
                }
            }

            //savePicture(canvas,logicielStructure);
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

}
