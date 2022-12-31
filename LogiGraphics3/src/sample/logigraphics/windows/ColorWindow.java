package sample.logigraphics.windows;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import sample.logigraphics.Logiciel;
import sample.logigraphics.interfaces.LogicielStructure;
import sample.logigraphics.stuff.Debug;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ColorWindow {

    SmallWindow smallWindow;

    public ColorWindow(LogicielStructure logicielStructure, Rectangle indicator){

        smallWindow = new SmallWindow("Couleur custom",1, Debug.getIcon("colors",logicielStructure,1));

        ColorPicker colorPicker = new ColorPicker();
        Button button = smallWindow.getStyliziedButton("OK",false);
        button.setOnAction(event -> {
            logicielStructure.getGraphicsContext().setStroke(colorPicker.getValue());
            logicielStructure.getGraphicsContext().setFill(colorPicker.getValue());
            indicator.setFill(colorPicker.getValue());
            smallWindow.close();
        });
        smallWindow.setSpacing(10);
        smallWindow.add(colorPicker);
        smallWindow.add(button);
    }

    public void show(){
        smallWindow.show();
    }

}
