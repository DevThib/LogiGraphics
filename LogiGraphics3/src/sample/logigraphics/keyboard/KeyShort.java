package sample.logigraphics.keyboard;

import javafx.scene.input.KeyCombination;

public class KeyShort {

    KeyShortEvent event;

    KeyCombination keyCombination;

    public KeyShort(KeyCombination keyCombination){
        this.keyCombination = keyCombination;
    }

    public KeyCombination getKeyCombination() {
        return keyCombination;
    }

    public void setOnExecuted(KeyShortEvent event){
        this.event = event;
    }

    public void execute(){
        event.run();
    }

}
