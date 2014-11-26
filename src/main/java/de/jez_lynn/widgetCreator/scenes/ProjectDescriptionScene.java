package main.java.de.jez_lynn.widgetCreator.scenes;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.de.jez_lynn.widgetCreator.helper.UploadData;

/**
 * Created by Michael on 29.10.2014.
 */
public class ProjectDescriptionScene {

    static TextArea inputArea;
    GridPane scene;
    Image image;
    ImageView imageView;
    UploadData lastData;
    CheckBox main;

    public ProjectDescriptionScene(final Stage stage) {
        scene = new GridPane();

        GridPane helper = new GridPane();
        main = new CheckBox("Ist dieses das Haupbild");
        helper.add(main, 0, 0);

        imageView = new ImageView();
        VBox pictureRegion = new VBox();
        pictureRegion.setPadding(new Insets(15, 12, 15, 12));
        pictureRegion.setFillWidth(true);
        pictureRegion.getChildren().add(imageView);
        helper.add(pictureRegion, 1, 0);

        scene.add(helper, 0, 0);

        VBox labelBox = new VBox();
        GridPane pane = new GridPane();
        labelBox.setPadding(new Insets(15, 12, 15, 12));
        labelBox.setFillWidth(true);
        Label title = new Label("Projekt Beschreibung");
        labelBox.getChildren().add(title);

        ImageView link = new ImageView("/ico/link.png");
        link.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (inputArea != null && inputArea.getSelectedText().length() > 0) {
                    System.out.println(inputArea.getSelectedText());
                    Stage linkStage = new Stage();
                    LinkScene linkScene = new LinkScene(linkStage, inputArea);
                    linkStage.initModality(Modality.WINDOW_MODAL);
                    linkStage.setScene(linkScene.getScene());
                    linkStage.initOwner(stage.getScene().getWindow());
                    linkStage.show();
                }
            }
        });
        pane.add(labelBox, 0, 0);
        pane.add(link, 1, 0);
        scene.add(pane, 0, 1);

        VBox inputBox = new VBox();
        inputBox.setPadding(new Insets(15, 12, 15, 12));
        inputBox.setFillWidth(true);
        inputArea = new TextArea();
        inputArea.setPrefRowCount(10);
        inputArea.setPrefColumnCount(100);
        inputArea.setWrapText(true);
        inputArea.setPrefWidth(550);
        scene.add(inputArea, 0, 2);

        scene.setVisible(false);
    }

    public static void saveData(UploadData data) {
        System.out.println(inputArea.getText());
        data.description = inputArea.getText();
    }

    public void updateScene(UploadData data) {
        if (lastData != null && lastData != data) {
            if (inputArea.getText().length() > 0) {
                lastData.description = inputArea.getText();
                inputArea.clear();
            }

            if (main.isSelected()) {
                lastData.main = true;
                main.setDisable(true);
                main.setSelected(false);
            } else if (data.main) {
                main.setSelected(data.main);
                main.setDisable(false);
            }
        }
        lastData = data;
        image = new Image(data.image.toURI().toString(), 200, 150, true, true);
        imageView.setImage(image);
        inputArea.setText(data.description);
        scene.setVisible(true);
    }

    public GridPane getScene() {
        return scene;
    }
}
