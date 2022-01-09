package sample.logigraphics.interfaces;

import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import sample.logigraphics.Logiciel;
import sample.logigraphics.windows.ThemesWindow;

public class TopMenuBar {

    Logiciel logiciel;

    MenuBar menuBar = new MenuBar();

    public TopMenuBar(Logiciel logiciel){
        this.logiciel = logiciel;
    }

    public MenuBar build(){

        Menu files = new Menu("_Fichiers");
        Menu settings = new Menu("_Paramètres");
        Menu themes = new Menu("_Thèmes");

        //files
        MenuItem nouv = new MenuItem("✏ Nouveau");
        nouv.setOnAction(event -> logiciel.getProject().startNew());

        MenuItem save = new MenuItem("💾 Enregistrer");
        save.setOnAction(event -> logiciel.getProject().save(!logiciel.getProject().hasBeenSaved()));
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        MenuItem open = new MenuItem("📁 Ouvrir");
        open.setOnAction(event -> {

        });

        //paramètres

        MenuItem mirror = new MenuItem("🕳 Miroir");
        mirror.setOnAction(event -> {



        });


        //thèmes
        MenuItem dark = new MenuItem("🖍 Sombre");
        dark.setOnAction(event -> {
            logiciel.getTheme().setCustomColors(Color.rgb(64,64,64), Color.rgb(96,96,96));
            logiciel.changeThemeInDatabase("dark/none");
            logiciel.getTheme().apply();
        });

        MenuItem light = new MenuItem("🖍 Blanc");
        light.setOnAction(event -> {
            logiciel.getTheme().setCustomColors(Color.rgb(230,230,230),Color.rgb(216,216,216));
            logiciel.changeThemeInDatabase("light/none");
            logiciel.getTheme().apply();
        });

        MenuItem custom = new MenuItem("🖍 Custom");
        custom.setOnAction(event ->{
            ThemesWindow themesWindow = new ThemesWindow();
            themesWindow.open(logiciel);
        });

        files.getItems().addAll(nouv,save,open);
        settings.getItems().addAll(mirror);
        themes.getItems().addAll(dark,light,custom);

        menuBar.getMenus().addAll(files,settings,themes);

        return menuBar;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
