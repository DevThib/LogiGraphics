package sample.logigraphics.charts;

import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LineChart implements Chart{

    HashMap<String,Double> values = new HashMap<>();
    ArrayList<String> keys = new ArrayList<>();
    ArrayList<Double> xs = new ArrayList<>();
    ArrayList<Double> ys = new ArrayList<>();

    Canvas chart;

    double size;

    public LineChart(double size){
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
        xs = new ArrayList<>();
        ys = new ArrayList<>();
    }

    @Override
    public void addValue(String x,double y){
        values.put(x,y);
        keys.add(x);
        xs.add(Double.valueOf(x));
        ys.add(y);
    }

    @Override
    public void removeValue(String x){
        xs.remove(Double.valueOf(x));
        values.remove(x);
        keys.remove(x);
        ys.remove(values.get(x));
    }

    @Override
    public double getValue(String x){
        return values.get(x);
    }

    @Override
    public void editValue(String x,double newY){
        ys.remove(values.get(x));
        xs.remove(Double.valueOf(x));
        values.replace(x,newY);
        xs.add(Double.valueOf(x));
        ys.add(newY);
        build();
    }

    @Override
    public Canvas build(){

        double theoHeight = size*300+15;
        double theoWidth = size*300+415;

        chart.getGraphicsContext2D().clearRect(0,0,size*300+420,size*300+20);
        chart.getGraphicsContext2D().setFill(Color.WHITE);
        chart.getGraphicsContext2D().fillRect(0,0,size*300+420,size*300+20);
        chart.getGraphicsContext2D().setFill(Color.GREY);
        chart.getGraphicsContext2D().strokeLine(5,5,5,theoHeight);
        chart.getGraphicsContext2D().strokeLine(5,theoHeight,theoWidth,theoHeight);
        chart.getGraphicsContext2D().setFill(Color.BLACK);

        double scaleX = theoWidth/getMax(xs);
        double scaleY = theoHeight/getMax(ys);

        for(int i = 0; i <= theoWidth/scaleX; i++){
            if(i != 0)chart.getGraphicsContext2D().strokeLine(i*scaleX,theoHeight,i*scaleX,theoHeight-5);
           // chart.getGraphicsContext2D().fillText(String.valueOf(i),i*scaleX,chart.getHeight());
        }
        for(int i = 0; i <= theoHeight/scaleY; i++){
            if(i != theoHeight/scaleY)chart.getGraphicsContext2D().strokeLine(5,i*scaleY+5,10,i*scaleY+5);
           // chart.getGraphicsContext2D().fillText(String.valueOf(i),0,i*scaleY);
        }

        double e1;
        double e2;

        for(String d : keys){
            if(Double.parseDouble(d)*scaleX == 0){
                e1 = Double.parseDouble(d)*scaleX+5;
            }else{e1 = Double.parseDouble(d)*scaleX;}
            if(theoHeight-values.get(d)*scaleY != 0){
                e2 = theoHeight-values.get(d)*scaleY;
            }else{e2 = theoHeight-values.get(d)*scaleY+5;}

            System.out.println(e2);

            chart.getGraphicsContext2D().strokeLine(e1-5,e2,e1+5,e2);
            chart.getGraphicsContext2D().strokeLine(e1,e2-5,e1,e2+5);

          //  chart.getGraphicsContext2D().fillRect(Double.parseDouble(d)*scaleX+5,chart.getHeight()-5-values.get(d)*scaleY,5,5);
        }

        chart.getGraphicsContext2D().fillText("y = "+giveAffineEquation(),30,30);

        System.out.println(giveAffineEquation());

        return chart;
    }

    private double getMax(ArrayList<Double> values){
        double max = 0.0;
        for(double d : values){
            if(d > max)max = d;
        }
        return max;
    }

    private String giveAffineEquation(){

        double coef = 0.0;
        int warn = 0;

        for(int i = 0; i < xs.size(); i++){
            if(xs.get(i) != 0){
                coef = coef + ys.get(i)/xs.get(i);
            }else warn++;
        }
        coef = coef/(xs.size()-warn);

        return coef+"x";
    }

}
