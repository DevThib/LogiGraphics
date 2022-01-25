package sample.logigraphics.stuff;

import java.io.File;

public class Debug {

    public static String getExtension(File file){
        String[] e = file.getName().split("\\.");
        return e[e.length-1];
    }

}
