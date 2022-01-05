package sample.logigraphics.projects;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ProjectChooser {

    FileChooser chooser = new FileChooser();

    public File openAndGetFile(){
        return chooser.showOpenDialog(new Stage());
    }

    public void setTitle(String title){
        chooser.setTitle(title);
    }

    public void setInitialDirectory(File file){
        chooser.setInitialDirectory(file);
    }

    public File getInitialDirectory(){
        return chooser.getInitialDirectory();
    }

    public String getTitle(){
        return chooser.getTitle();
    }

}