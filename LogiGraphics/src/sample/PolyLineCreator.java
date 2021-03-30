package sample;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

public class PolyLineCreator {

    PropertiesOpener opener = new PropertiesOpener();

    Polyline action;

    Polyline r1;
    Polyline r2;
    Polyline r3;
    Polyline r4;
    Polyline r5;
    Polyline r6;
    Polyline r7;
    Polyline r8;
    Polyline r9;
    Polyline r10;
    Polyline r11;
    Polyline r12;
    Polyline r13;
    Polyline r14;
    Polyline r15;
    Polyline r16;
    Polyline r17;
    Polyline r18;
    Polyline r19;
    Polyline r20;

    private String getRectangleToReturn(){

        if(r1 == null){
            return "r1";
        }
        if(r2 == null){
            return "r2";
        }
        if(r3 == null){
            return "r3";
        }
        if(r4 == null){
            return "r4";
        }
        if(r5 == null){
            return "r5";
        }
        if(r6 == null){
            return "r6";
        }
        if(r7 == null){
            return "r7";
        }
        if(r8 == null){
            return "r8";
        }
        if(r9 == null){
            return "r9";
        }
        if(r10 == null){
            return "r10";
        }
        if(r11 == null){
            return "r11";
        }
        if(r12 == null){
            return "r12";
        }
        if(r13 == null){
            return "r13";
        }
        if(r14 == null){
            return "r14";
        }
        if(r15 == null){
            return "r15";
        }
        if(r16 == null){
            return "r16";
        }
        if(r17 == null){
            return "r17";
        }
        if(r18 == null){
            return "r18";
        }
        if(r19 == null){
            return "r19";
        }
        if(r20 == null){
            return "r20";
        }
        return "none";
    }

    public void createPolyLine(){
        if(getRectangleToReturn().equalsIgnoreCase("none")){
            Main.error.showCustomError("Limite de multilignes atteinte ! (wow !)");
        }else{

            String r = getRectangleToReturn();

            if (r.equalsIgnoreCase("r1")) {
                r1 = new Polyline();
                action = r1;

                r1.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r1);
                });

                Main.group.getChildren().add(r1);
            }
            if (r.equalsIgnoreCase("r2")) {
                r2 = new Polyline();
                action = r2;

                r2.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r2);
                });

                Main.group.getChildren().add(r2);
            }
            if (r.equalsIgnoreCase("r3")) {
                r3 = new Polyline();
                action = r3;

                r3.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r3);
                });

                Main.group.getChildren().add(r3);
            }
            if (r.equalsIgnoreCase("r4")) {
                r4 = new Polyline();
                action = r4;

                r4.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r4);
                });

                Main.group.getChildren().add(r4);
            }
            if (r.equalsIgnoreCase("r5")) {
                r5 = new Polyline();
                action = r5;

                r5.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r5);
                });

                Main.group.getChildren().add(r5);
            }
            if (r.equalsIgnoreCase("r6")) {
                r6 = new Polyline();
                action = r6;

                r6.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r6);
                });

                Main.group.getChildren().add(r6);
            }
            if (r.equalsIgnoreCase("r7")) {
                r7 = new Polyline();
                action = r7;

                r7.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r7);
                });

                Main.group.getChildren().add(r7);
            }
            if (r.equalsIgnoreCase("r8")) {
                r8 = new Polyline();
                action = r8;

                r8.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r8);
                });

                Main.group.getChildren().add(r8);
            }
            if (r.equalsIgnoreCase("r9")) {
                r9 = new Polyline();
                action = r9;

                r9.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r9);
                });

                Main.group.getChildren().add(r9);
            }
            if (r.equalsIgnoreCase("r10")) {
                r10 = new Polyline();
                action = r10;

                r10.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r10);
                });

                Main.group.getChildren().add(r10);
            }
            if (r.equalsIgnoreCase("r11")) {
                r11 = new Polyline();
                action = r11;

                r11.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r11);
                });

                Main.group.getChildren().add(r11);
            }
            if (r.equalsIgnoreCase("r12")) {
                r12 = new Polyline();
                action = r12;

                r12.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r12);
                });

                Main.group.getChildren().add(r12);
            }
            if (r.equalsIgnoreCase("r13")) {
                r13 = new Polyline();
                action = r13;

                r13.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r13);
                });

                Main.group.getChildren().add(r13);
            }
            if (r.equalsIgnoreCase("r14")) {
                r14 = new Polyline();
                action = r14;

                r14.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r14);
                });

                Main.group.getChildren().add(r14);
            }
            if (r.equalsIgnoreCase("r15")) {
                r15 = new Polyline();
                action = r15;

                r15.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r15);
                });

                Main.group.getChildren().add(r15);
            }
            if (r.equalsIgnoreCase("r16")) {
                r16 = new Polyline();
                action = r16;

                r16.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r16);
                });

                Main.group.getChildren().add(r16);
            }
            if (r.equalsIgnoreCase("r17")) {
                r17 = new Polyline();
                action = r17;

                r17.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r17);
                });

                Main.group.getChildren().add(r17);
            }
            if (r.equalsIgnoreCase("r18")) {
                r18 = new Polyline();
                action = r18;

                r18.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r18);
                });

                Main.group.getChildren().add(r18);
            }
            if (r.equalsIgnoreCase("r19")) {
                r19 = new Polyline();
                action = r19;

                r19.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r19);
                });

                Main.group.getChildren().add(r19);
            }
            if (r.equalsIgnoreCase("r20")) {
                r20 = new Polyline();
                action = r20;

                r20.setOnMouseClicked(event -> {
                    opener.openPolyLineProperties(r20);
                });

                Main.group.getChildren().add(r20);
            }



        }
    }

    public Polyline getAction(){
        return action;
    }


}
