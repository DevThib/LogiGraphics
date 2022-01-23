package sample.logigraphics.interfaces;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.logigraphics.events.Event;

import java.io.File;

public class TreeBuilder {

    TreeView<String> treeView;

    File root;

    ContextMenu contextMenu = new ContextMenu();

    TreeItem<String> selected;

    Node node;

    Event dragEvent = () -> {};

    Scene scene;

    Background background = new Background(new BackgroundFill(Color.GREY,new CornerRadii(0),new Insets(0)));

    public TreeBuilder(File root, Node node, Scene scene){
        this.root = root;
        this.node = node;
        this.scene = scene;

        changeTree();
        createMenu();
    }

    public void setDirectory(File root){
        this.root = root;
        changeTree();

        dragEvent.run();
    }

    public TreeView<String> get() {
        return treeView;
    }

    private void changeTree(){

        TreeView<String> treeView;

        if(root.isDirectory()){treeView = new TreeView<>(getTree(root));}else{treeView = new TreeView<>(new TreeItem<>(root.getName())); }

        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {

            selected = treeView.getSelectionModel().getSelectedItem();

            if(event.getButton().equals(MouseButton.SECONDARY)){
                contextMenu.show(node,event.getScreenX(),event.getScreenY());
            }else{
                contextMenu.hide();
            }
        };

        treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
        treeView.setMaxWidth(170);
        treeView.setMinWidth(170);
        treeView.setBorder(new Border(new BorderStroke(LogicielColors.getTopBarColor(),BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1))));
        treeView.setStyle(
                "  -fx-base: #242424 ;\n" +
                        "  -fx-control-inner-background: derive(-fx-base,20%);\n" +
                        "  -fx-control-inner-background-alt: derive(-fx-control-inner-background,-10%);\n" +
                        "  -fx-background: grey;\n" +
                        "  -fx-accent: #006689;\n" +
                        "  -fx-focus-color: #036E83;\n" +
                        "  -fx-faint-focus-color: #036E8322;" +
                        "  -fx-font-family: \"Trebuchet MS\";");

        this.treeView = treeView;
    }

    private TreeItem<String> getTree(File file){

        TreeItem<String> treeItem = new TreeItem<>(file.getName());
        treeItem.setExpanded(true);

        if(file.isDirectory()){

            File[] files = file.listFiles();

            if(files != null && files.length != 0) {

                for (File file1 : files) {

                    if(!file1.isDirectory()){
                        treeItem.getChildren().add(new TreeItem<>(file1.getName()));
                    }else{
                        treeItem.getChildren().add(getTree(file1));
                    }

                }

            }else{
                return treeItem;
            }

        }else{
            return treeItem;
        }

        return treeItem;
    }

    private void createMenu(){

        MenuItem delete = new MenuItem("🚮 Supprimer");
        delete.setOnAction(event -> {
            if(selected != treeView.getTreeItem(0)) searchFileAndDelete(String.valueOf(selected.getValue()),root);
        });

        MenuItem open = new MenuItem("📁 Ouvrir");
        open.setOnAction(event -> setDirectory( new DirectoryChooser().showDialog(new Stage())));

        contextMenu.getItems().addAll(delete,open);
    }

    private void searchFileAndDelete(String name,File root){

        File searched = null;

        if(root.isDirectory()){

            File[] files = root.listFiles();

            if(files != null && files.length != 0) {

                for (File file1 : files) {

                    if(!file1.isDirectory()){
                        if(file1.getName().equals(name)){
                            searched = file1;
                        }
                    }else{
                        if(file1.getName().equals(name)){
                            searched = file1;
                        }else{
                            searchFileAndDelete(name,file1);
                        }
                    }

                }

            }

        }

        try{
            searched.delete();
            changeTree();
            dragEvent.run();
        }catch (NullPointerException e){}
    }

    public void setOnRootChange(Event event){
        this.dragEvent = event;
    }

}
