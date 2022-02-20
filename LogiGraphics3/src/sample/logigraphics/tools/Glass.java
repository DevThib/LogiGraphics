package sample.logigraphics.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import sample.logigraphics.interfaces.LogicielStructure;

public class Glass {

    Canvas studied;

    Canvas render = new Canvas(150,150);

    boolean activated = false;

    public Glass(LogicielStructure logicielStructure){
        this.studied = logicielStructure.getCanvas();
        render.getGraphicsContext2D().fillRect(0,0,150,150);
    }

    public Canvas getRender() {
        return render;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public void stopZoom(){
        render.getGraphicsContext2D().clearRect(0,0,150,150);
        render.getGraphicsContext2D().setFill(Color.BLACK);
        render.getGraphicsContext2D().fillRect(0,0,150,150);
    }

    public void zoom(double sceneX,double sceneY){

        for(double i = sceneY-50; i < sceneY+50; i++){
            for(double a = sceneX-50; a < sceneX+50; a++){

            }
        }






    }

}
