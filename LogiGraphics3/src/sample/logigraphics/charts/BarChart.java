package sample.logigraphics.charts;

import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BarChart implements Chart{

    HashMap<String,Double> values = new HashMap<>();
    ArrayList<String> keys = new ArrayList<>();

    Canvas chart;

    Random random = new Random();

    double size;

    public BarChart(double size){
        chart = new Canvas(size*300+400+20,size*300+20);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(5);
        ds.setOffsetX(5);
        ds.setRadius(10);

        chart.setEffect(ds);

        this.size = size;
    }

    @Override
    public HashMap<String, Double> getValues() {
        return values;
    }

    @Override
    public void init(){
        values = new HashMap<>();
        keys = new ArrayList<>();
    }

    @Override
    public void addValue(String name,double value){
        values.put(name,value);
        keys.add(name);
    }

    @Override
    public void removeValue(String name){
        values.remove(name);
        keys.remove(name);
    }

    @Override
    public double getValue(String name){
        return values.get(name);
    }

    @Override
    public void editValue(String name,double newValue){
        values.replace(name,newValue);
        build();
    }

    @Override
    public Canvas build(){

        chart.getGraphicsContext2D().clearRect(0,0,size*300+420,size*300+20);
        chart.getGraphicsContext2D().setFill(Color.WHITE);
        chart.getGraphicsContext2D().fillRect(0,0,size*300+420,size*300+20);

        double max = 0;

        for (String key : keys) {
            if (max < values.get(key)) max = values.get(key);
        }
        double ratio = size*300/max;

        int i = 0;
        for(String string : keys){

            Color color = Color.rgb(random.nextInt(256),random.nextInt(256), random.nextInt(256));

            double width = (size*300)/keys.size()-10;

            chart.getGraphicsContext2D().setFill(color);
            chart.getGraphicsContext2D().fillRect(i*(width+10)+10,10+size*300-ratio*values.get(string),width,ratio*values.get(string));

            chart.getGraphicsContext2D().fillRect(size*300+60,50+i*45,40,40);
            chart.getGraphicsContext2D().setFill(Color.BLACK);
            chart.getGraphicsContext2D().setFont(new Font("Trebuchet MS",20));
            chart.getGraphicsContext2D().fillText(string,size*300+115,75+i*45);

            i++;
        }

        return chart;
    }

}
