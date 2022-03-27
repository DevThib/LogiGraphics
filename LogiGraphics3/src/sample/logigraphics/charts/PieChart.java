package sample.logigraphics.charts;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PieChart {

    HashMap<String,Double> values = new HashMap<>();
    ArrayList<String> keys = new ArrayList<>();

    Canvas chart;

    Random random = new Random();

    double size;

    public PieChart(double size){
        chart = new Canvas(size*300,size*300);
        this.size = size;
    }

    public HashMap<String, Double> getValues() {
        return values;
    }

    public void init(){
        values = new HashMap<>();
        keys = new ArrayList<>();
    }

    public void addValue(String name,double value){
        values.put(name,value);
        keys.add(name);
    }

    public void removeValue(String name){
        values.remove(name);
        keys.remove(name);
    }

    public double getValue(String name){
        return values.get(name);
    }

    public void editValue(String name,double newValue){
        values.replace(name,newValue);
        build();
    }

    public Canvas build(){

        double total = 0;
        double part;
        double totalAngle = 0;

        for(String string : keys){
            total += values.get(string);
        }

        for(String string : keys){
            part = values.get(string)/total;
            chart.getGraphicsContext2D().setFill(Color.rgb(random.nextInt(256),random.nextInt(256), random.nextInt(256)));
            chart.getGraphicsContext2D().fillArc(0,0,size*300,size*300,totalAngle,360*part, ArcType.ROUND);
            totalAngle += 360*part;
        }

        return chart;
    }



}
