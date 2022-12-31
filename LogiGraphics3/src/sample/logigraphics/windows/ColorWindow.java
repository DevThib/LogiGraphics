package sample.logigraphics.windows;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.shape.Rectangle;
import sample.logigraphics.Logiciel;
import sample.logigraphics.interfaces.LogicielStructure;

public class ColorWindow {

    SmallWindow smallWindow = new SmallWindow("Couleur custom",1);

    public ColorWindow(LogicielStructure logicielStructure, Rectangle indicator){
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
