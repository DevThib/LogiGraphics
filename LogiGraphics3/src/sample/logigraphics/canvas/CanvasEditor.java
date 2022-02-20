package sample.logigraphics.canvas;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import sample.logigraphics.interfaces.LogicielStructure;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class CanvasEditor {

    public static void blackAndWhite(Canvas canvas,File image){

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

            int color;
            int red;
            int blue;
            int green;
            double grey;

            for (int i = 0; i < canvas.getHeight(); i++) {
                for (int a = 0; a < canvas.getWidth(); a++) {
                    color = pixelReader.getArgb(a, i);
                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;
                    grey = (red + blue + green) / 3;
                    canvas.getGraphicsContext2D().setFill(Color.rgb((int) grey, (int) grey, (int) grey));
                    canvas.getGraphicsContext2D().fillRect(a, i, 1, 1);
                }

            }

        }catch (IOException ee){}

    }


}
