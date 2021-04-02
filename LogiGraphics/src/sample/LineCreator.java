package sample;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class LineCreator {

    PropertiesOpener opener = new PropertiesOpener();

    Line action;

    Line r1;
    Line r2;
    Line r3;
    Line r4;
    Line r5;
    Line r6;
    Line r7;
    Line r8;
    Line r9;
    Line r10;
    Line r11;
    Line r12;
    Line r13;
    Line r14;
    Line r15;
    Line r16;
    Line r17;
    Line r18;
    Line r19;
    Line r20;

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

    public void createLine(){
        if(getRectangleToReturn().equalsIgnoreCase("none")){
            Main.error.showCustomError("Limite de lignes atteinte !");
        }else{

            String r = getRectangleToReturn();

            if (r.equalsIgnoreCase("r1")) {
                r1 = new Line();
                action = r1;

                r1.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r1.getId().equalsIgnoreCase("eraser"))
                        opener.openLineProperties(r1);

                });

                Main.group.getChildren().add(r1);
            }
            if (r.equalsIgnoreCase("r2")) {
                r2 = new Line();
                action = r2;

                r2.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r2.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r2);
                });

                Main.group.getChildren().add(r2);
            }
            if (r.equalsIgnoreCase("r3")) {
                r3 = new Line();
                action = r3;

                r3.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r3.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r3);
                });

                Main.group.getChildren().add(r3);
            }
            if (r.equalsIgnoreCase("r4")) {
                r4 = new Line();
                action = r4;

                r4.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r4.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r4);
                });

                Main.group.getChildren().add(r4);
            }
            if (r.equalsIgnoreCase("r5")) {
                r5 = new Line();
                action = r5;

                r5.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r5.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r5);
                });

                Main.group.getChildren().add(r5);
            }
            if (r.equalsIgnoreCase("r6")) {
                r6 = new Line();
                action = r6;

                r6.setOnMouseClicked(event -> {
                    if(Main.selector == 5&& !r6.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r6);
                });

                Main.group.getChildren().add(r6);
            }
            if (r.equalsIgnoreCase("r7")) {
                r7 = new Line();
                action = r7;

                r7.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r7.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r7);
                });

                Main.group.getChildren().add(r7);
            }
            if (r.equalsIgnoreCase("r8")) {
                r8 = new Line();
                action = r8;

                r8.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r8.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r8);
                });

                Main.group.getChildren().add(r8);
            }
            if (r.equalsIgnoreCase("r9")) {
                r9 = new Line();
                action = r9;

                r9.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r9.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r9);
                });

                Main.group.getChildren().add(r9);
            }
            if (r.equalsIgnoreCase("r10")) {
                r10 = new Line();
                action = r10;

                r10.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r10.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r10);
                });

                Main.group.getChildren().add(r10);
            }
            if (r.equalsIgnoreCase("r11")) {
                r11 = new Line();
                action = r11;

                r11.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r11.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r11);
                });

                Main.group.getChildren().add(r11);
            }
            if (r.equalsIgnoreCase("r12")) {
                r12 = new Line();
                action = r12;

                r12.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r12.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r12);
                });

                Main.group.getChildren().add(r12);
            }
            if (r.equalsIgnoreCase("r13")) {
                r13 = new Line();
                action = r13;

                r13.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r13.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r13);
                });

                Main.group.getChildren().add(r13);
            }
            if (r.equalsIgnoreCase("r14")) {
                r14 = new Line();
                action = r14;

                r14.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r14.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r14);
                });

                Main.group.getChildren().add(r14);
            }
            if (r.equalsIgnoreCase("r15")) {
                r15 = new Line();
                action = r15;

                r15.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r15.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r15);
                });

                Main.group.getChildren().add(r15);
            }
            if (r.equalsIgnoreCase("r16")) {
                r16 = new Line();
                action = r16;

                r16.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r16.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r16);
                });

                Main.group.getChildren().add(r16);
            }
            if (r.equalsIgnoreCase("r17")) {
                r17 = new Line();
                action = r17;

                r17.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r17.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r17);
                });

                Main.group.getChildren().add(r17);
            }
            if (r.equalsIgnoreCase("r18")) {
                r18 = new Line();
                action = r18;

                r18.setOnMouseClicked(event -> {
                    if(Main.selector == 5&& !r18.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r18);
                });

                Main.group.getChildren().add(r18);
            }
            if (r.equalsIgnoreCase("r19")) {
                r19 = new Line();
                action = r19;

                r19.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r19.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r19);
                });

                Main.group.getChildren().add(r19);
            }
            if (r.equalsIgnoreCase("r20")) {
                r20 = new Line();
                action = r20;

                r20.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r20.getId().equalsIgnoreCase("eraser"))
                    opener.openLineProperties(r20);
                });

                Main.group.getChildren().add(r20);
            }



        }
    }

    public Line getAction(){
        return action;
    }


}
