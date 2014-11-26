package main.java.de.jez_lynn.widgetCreator.scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.de.jez_lynn.widgetCreator.reference.Reference;

/**
 * Created by Michael on 26.11.2014.
 */
public class LinkScene {

    private Scene scene;
    private TextArea textArea;

    public LinkScene(Stage linkStage, TextArea textArea) {
        this.textArea = textArea;

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(15, 12, 15, 12));
        pane.setHgap(20);
        pane.setVgap(12);
        pane.setStyle("-fx-background-color: #336699;");

        Label linkLabel = new Label("Link Adresse:");
        linkLabel.setTextFill(Color.web("#FFFFFF"));
        linkLabel.setFont(new Font("Cambria", 20));
        pane.add(linkLabel, 0, 0);

        TextField linkAddress = new TextField();
        pane.add(linkAddress, 1, 0, 2, 1);

        Button save = saveButton(linkAddress, linkStage);
        pane.add(save, 0, 1);

        scene = new Scene(pane, 350, 100);
    }

    public Button saveButton(final TextField linkAddress, final Stage stage) {
        Button save = new Button();
        save.setText("Speichern");
        save.setPrefSize(100, 20);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (linkAddress.getText().length() > 0) {
                    textArea.replaceSelection(String.format(Reference.HTML.LINK, linkAddress.getText(), textArea.getSelectedText()));
                    stage.close();
                }
            }
        });
        return save;
    }

    public Scene getScene() {
        return scene;
    }
}
