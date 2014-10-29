package main.java.de.jez_lynn.widgetCreator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.de.jez_lynn.widgetCreator.handler.ConfigHandler;
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
        } else {
            ConfigHandler.load(configFile);
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
    public void start(final Stage stage) throws Exception {
        imageSelectorScene = new ImageSelectorScene(stage);
        stage.setScene(imageSelectorScene.getScene());
        stage.setTitle("Projekte erstellen");
        stage.show();
        System.out.println(Reference.FTP.OUT());

        if (!configExist) {
            Stage ftpConfig = new Stage();
            ftpConfigureScene = new FTPConfigureScene(ftpConfig);
            ftpConfig.initModality(Modality.WINDOW_MODAL);
            ftpConfig.setScene(ftpConfigureScene.getScene());
            ftpConfig.initOwner(stage.getScene().getWindow());
            ftpConfig.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    stage.close();
                }
            });
            ftpConfig.show();
        }
    }
}
