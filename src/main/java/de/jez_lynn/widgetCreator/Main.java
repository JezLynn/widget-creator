package main.java.de.jez_lynn.widgetCreator;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.de.jez_lynn.widgetCreator.helper.FTPConfig;
import main.java.de.jez_lynn.widgetCreator.reference.Reference;
import main.java.de.jez_lynn.widgetCreator.scenes.FTPConfigureScene;
import main.java.de.jez_lynn.widgetCreator.scenes.ImageSelectorScene;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael on 27.10.2014.
 */
public class Main extends Application {

    private FTPConfig ftpConfig;
    private ImageSelectorScene imageSelectorScene;
    private FTPConfigureScene ftpConfigureScene;
    private boolean configExist = true;

    public Main() {
        Path configPath = Paths.get(Reference.CONFIG);
        File configDirPath = new File(configPath.getParent().toString());
        if (!configDirPath.exists()) {
            try {
                configDirPath.mkdir();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        File configFile = new File(configPath.toString());
        if (!configFile.exists()) {
            configExist = false;
            //Config.create(configFile);
            //ftpConfig = Config.load(configFile);
        }
        File dir = new File(Reference.TEMP);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        imageSelectorScene = new ImageSelectorScene(stage);

        ftpConfigureScene = new FTPConfigureScene(stage);

        if (!configExist) {
            stage.setScene(ftpConfigureScene.getScene());
        } else
            stage.setScene(imageSelectorScene.getScene());
        stage.setTitle("Test");
        stage.show();
    }
}
