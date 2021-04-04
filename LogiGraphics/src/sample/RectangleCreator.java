package sample;

import javafx.scene.shape.Rectangle;

public class RectangleCreator {

    PropertiesOpener opener = new PropertiesOpener();

    Rectangle action;

    Rectangle r1;
    Rectangle r2;
    Rectangle r3;
    Rectangle r4;
    Rectangle r5;
    Rectangle r6;
    Rectangle r7;
    Rectangle r8;
    Rectangle r9;
    Rectangle r10;
    Rectangle r11;
    Rectangle r12;
    Rectangle r13;
    Rectangle r14;
    Rectangle r15;
    Rectangle r16;
    Rectangle r17;
    Rectangle r18;
    Rectangle r19;
    Rectangle r20;

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

    public void createRectangle(){
        if(getRectangleToReturn().equalsIgnoreCase("none")){
            Main.error.showCustomError("Limite de rectangles atteinte !");
        }else{

                String r = getRectangleToReturn();

                if (r.equalsIgnoreCase("r1")) {
                    r1 = new Rectangle();
                    action = r1;

                    r1.setOnMouseClicked(event -> {
                        if(Main.selector == 5 && !r1.getId().equalsIgnoreCase("eraser"))
                        opener.openRectanglePropoerties(r1);
                    });

                }
            if (r.equalsIgnoreCase("r2")) {
                r2 = new Rectangle();
                action = r2;

                r2.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r2.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r2);
                });

            }
            if (r.equalsIgnoreCase("r3")) {
                r3 = new Rectangle();
                action = r3;

                r3.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r3.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r3);
                });

            }
            if (r.equalsIgnoreCase("r4")) {
                r4 = new Rectangle();
                action = r4;

                r4.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r4.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r4);
                });

            }
            if (r.equalsIgnoreCase("r5")) {
                r5 = new Rectangle();
                action = r5;

                r5.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r5.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r5);
                });

            }
            if (r.equalsIgnoreCase("r6")) {
                r6 = new Rectangle();
                action = r6;

                r6.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r6.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r6);
                });

            }
            if (r.equalsIgnoreCase("r7")) {
                r7 = new Rectangle();
                action = r7;

                r7.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r7.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r7);
                });

            }
            if (r.equalsIgnoreCase("r8")) {
                r8 = new Rectangle();
                action = r8;

                r8.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r8.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r8);
                });

            }
            if (r.equalsIgnoreCase("r9")) {
                r9 = new Rectangle();
                action = r9;

                r9.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r9.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r9);
                });

            }
            if (r.equalsIgnoreCase("r10")) {
                r10 = new Rectangle();
                action = r10;

                r10.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r10.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r10);
                });

            }
            if (r.equalsIgnoreCase("r11")) {
                r11 = new Rectangle();
                action = r11;

                r11.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r11.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r11);
                });

            }
            if (r.equalsIgnoreCase("r12")) {
                r12 = new Rectangle();
                action = r12;

                r12.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r12.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r12);
                });

            }
            if (r.equalsIgnoreCase("r13")) {
                r13 = new Rectangle();
                action = r13;

                r13.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r13.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r13);
                });

            }
            if (r.equalsIgnoreCase("r14")) {
                r14 = new Rectangle();
                action = r14;

                r14.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r14.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r14);
                });

            }
            if (r.equalsIgnoreCase("r15")) {
                r15 = new Rectangle();
                action = r15;

                r15.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r15.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r15);
                });

            }
            if (r.equalsIgnoreCase("r16")) {
                r16 = new Rectangle();
                action = r16;

                r16.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r16.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r16);
                });

            }
            if (r.equalsIgnoreCase("r17")) {
                r17 = new Rectangle();
                action = r17;

                r17.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r17.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r17);
                });
            }
            if (r.equalsIgnoreCase("r18")) {
                r18 = new Rectangle();
                action = r18;

                r18.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r18.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r18);
                });

            }
            if (r.equalsIgnoreCase("r19")) {
                r19 = new Rectangle();
                action = r19;

                r19.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r19.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r19);
                });

            }
            if (r.equalsIgnoreCase("r20")) {
                r20 = new Rectangle();
                action = r20;

                r20.setOnMouseClicked(event -> {
                    if(Main.selector == 5 && !r20.getId().equalsIgnoreCase("eraser"))
                    opener.openRectanglePropoerties(r20);
                });

            }



        }
    }

    public Rectangle getAction(){
        return action;
    }

}
