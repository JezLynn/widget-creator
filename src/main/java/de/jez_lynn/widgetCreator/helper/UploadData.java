package main.java.de.jez_lynn.widgetCreator.helper;

import main.java.de.jez_lynn.widgetCreator.reference.Reference;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael on 29.10.2014.
 */
public class UploadData {
    public String title = "";
    public String path = "";
    public File image;
    public String description = "";
    public boolean main = false;

    public String name() {
        return title.toLowerCase().replaceAll("\\s", "");
    }

    public String ftpPath() {
        return "http://" + Reference.FTP.SERVER + "/images/" + filename();
    }

    public String filename() {
        Path path = Paths.get(this.path);
        return path.getFileName().toString();
    }

    public boolean isMain() {
        return main;
    }
}
