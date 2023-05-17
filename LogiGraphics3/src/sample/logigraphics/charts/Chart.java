package sample.logigraphics.charts;

import javafx.scene.canvas.Canvas;

import java.util.HashMap;

public interface Chart {

    void addValue(String name,double value);
    void removeValue(String name);
    double getValue(String name);
    void init();
    HashMap<String, Double> getValues();
    void editValue(String name,double newValue);
    Canvas build();

}
