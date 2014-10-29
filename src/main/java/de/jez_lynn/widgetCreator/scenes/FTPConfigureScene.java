package main.java.de.jez_lynn.widgetCreator.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.de.jez_lynn.widgetCreator.handler.Config;
import main.java.de.jez_lynn.widgetCreator.reference.Reference;

import java.io.File;

/**
 * Created by Michael on 29.10.2014.
 */
public class FTPConfigureScene {

    private Scene scene;
    private TextField adresseField;
    private TextField userField;
    private TextField passField;

    public FTPConfigureScene(Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #336699;");

        GridPane topUserInteraction = topUserInteraction();
        pane.setTop(topUserInteraction);

        HBox save = saveButton(stage);

        pane.setBottom(save);

        scene = new Scene(pane, 350, 350);
    }

    public Scene getScene() {
        return scene;
    }

    private GridPane topUserInteraction() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 12, 15, 12));

        final Label header = new Label("FTP-Einstellungen");
        header.setTextFill(Color.web("#ffffff"));
        header.setFont(new Font("Cambria", 16));
        grid.add(header, 0, 0);

        final Label adressLabel = new Label("Server-Adresse: ");
        adressLabel.setTextFill(Color.web("#ffffff"));
        adressLabel.setFont(new Font("Cambria", 16));
        adresseField = new TextField();
        grid.add(adressLabel, 0, 1);
        grid.add(adresseField, 1, 1);

        final Label userLabel = new Label("Benutzername: ");
        userLabel.setTextFill(Color.web("#ffffff"));
        userLabel.setFont(new Font("Cambria", 16));
        userField = new TextField();
        grid.add(userLabel, 0, 2);
        grid.add(userField, 1, 2);

        final Label passLabel = new Label("Passwort: ");
        passLabel.setTextFill(Color.web("#ffffff"));
        passLabel.setFont(new Font("Cambria", 16));
        passField = new TextField();
        grid.add(passLabel, 0, 3);
        grid.add(passField, 1, 3);

        return grid;
    }

    public HBox saveButton(final Stage stage) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);

        Button save = new Button("Speichern");
        save.setPrefSize(100, 20);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String address = adresseField.getText();
                String user = userField.getText();
                String pass = passField.getText();
                if (address.length() > 0 & user.length() > 0 & pass.length() > 0) {
                    Reference.FTP.SERVER = address;
                    Reference.FTP.USER = user;
                    Reference.FTP.PASSWORD = pass;
                    File configFile = new File(Reference.CONFIG);
                    Config.create(configFile);
                    stage.close();
                } else {
                    Stage error = new Stage();
                    error.initModality(Modality.WINDOW_MODAL);
                    VBox dialogBox = new VBox();
                    dialogBox.setPadding(new Insets(15, 12, 15, 12));
                    dialogBox.setSpacing(10);
                    Label message = new Label("Es müssen alle Felder \n ausgefüllt werden");
                    message.setTextFill(Color.web("#F62217"));
                    message.setFont(new Font("Cambria", 20));
                    dialogBox.getChildren().add(message);
                    Scene scene = new Scene(dialogBox);
                    error.setScene(scene);
                    error.initOwner(stage.getScene().getWindow());
                    error.show();
                }
            }
        });

        hBox.getChildren().addAll(save);

        hBox.setAlignment(Pos.CENTER_RIGHT);
        return hBox;
    }
}
