package sample;

import javafx.scene.shape.Polygon;

public class PolyGonCreator {

    PropertiesOpener opener = new PropertiesOpener();

    Polygon action;

    Polygon r1;
    Polygon r2;
    Polygon r3;
    Polygon r4;
    Polygon r5;
    Polygon r6;
    Polygon r7;
    Polygon r8;
    Polygon r9;
    Polygon r10;
    Polygon r11;
    Polygon r12;
    Polygon r13;
    Polygon r14;
    Polygon r15;
    Polygon r16;
    Polygon r17;
    Polygon r18;
    Polygon r19;
    Polygon r20;

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

    public void createPolyGon(){
        if(getRectangleToReturn().equalsIgnoreCase("none")){
            Main.error.showCustomError("Limite de polygones atteinte ! (wow !)");
        }else{

            String r = getRectangleToReturn();

            if (r.equalsIgnoreCase("r1")) {
                r1 = new Polygon();
                action = r1;

                r1.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r1.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r1);
                });

            }
            if (r.equalsIgnoreCase("r2")) {
                r2 = new Polygon();
                action = r2;

                r2.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r2.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r2);
                });

            }
            if (r.equalsIgnoreCase("r3")) {
                r3 = new Polygon();
                action = r3;

                r3.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r3.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r3);
                });

            }
            if (r.equalsIgnoreCase("r4")) {
                r4 = new Polygon();
                action = r4;

                r4.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r4.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r4);
                });

            }
            if (r.equalsIgnoreCase("r5")) {
                r5 = new Polygon();
                action = r5;

                r5.setOnMouseClicked(event -> {
                    if(Main.selector == 5&& !r5.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r5);
                });

            }
            if (r.equalsIgnoreCase("r6")) {
                r6 = new Polygon();
                action = r6;

                r6.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r6.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r6);
                });

            }
            if (r.equalsIgnoreCase("r7")) {
                r7 = new Polygon();
                action = r7;

                r7.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r7.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r7);
                });

            }
            if (r.equalsIgnoreCase("r8")) {
                r8 = new Polygon();
                action = r8;

                r8.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r8.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r8);
                });

            }
            if (r.equalsIgnoreCase("r9")) {
                r9 = new Polygon();
                action = r9;

                r9.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r9.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r9);
                });

            }
            if (r.equalsIgnoreCase("r10")) {
                r10 = new Polygon();
                action = r10;

                r10.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r10.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r10);
                });

            }
            if (r.equalsIgnoreCase("r11")) {
                r11 = new Polygon();
                action = r11;

                r11.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r11.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r11);
                });

            }
            if (r.equalsIgnoreCase("r12")) {
                r12 = new Polygon();
                action = r12;

                r12.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r12.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r12);
                });

            }
            if (r.equalsIgnoreCase("r13")) {
                r13 = new Polygon();
                action = r13;

                r13.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r13.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r13);
                });

            }
            if (r.equalsIgnoreCase("r14")) {
                r14 = new Polygon();
                action = r14;

                r14.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r14.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r14);
                });

            }
            if (r.equalsIgnoreCase("r15")) {
                r15 = new Polygon();
                action = r15;

                r15.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r15.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r15);
                });

            }
            if (r.equalsIgnoreCase("r16")) {
                r16 = new Polygon();
                action = r16;

                r16.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r16.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r16);
                });

            }
            if (r.equalsIgnoreCase("r17")) {
                r17 = new Polygon();
                action = r17;

                r17.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r17.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r17);
                });

            }
            if (r.equalsIgnoreCase("r18")) {
                r18 = new Polygon();
                action = r18;

                r18.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r18.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r18);
                });

            }
            if (r.equalsIgnoreCase("r19")) {
                r19 = new Polygon();
                action = r19;

                r19.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r19.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r19);
                });

            }
            if (r.equalsIgnoreCase("r20")) {
                r20 = new Polygon();
                action = r20;

                r20.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r20.getId().equalsIgnoreCase("eraser"))
                        opener.openPolyGonProperties(r20);
                });

            }



        }
    }

    public Polygon getAction(){
        return action;
    }


}
