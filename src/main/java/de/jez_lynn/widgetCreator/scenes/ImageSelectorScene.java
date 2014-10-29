package main.java.de.jez_lynn.widgetCreator.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.de.jez_lynn.widgetCreator.handler.FTP;
import main.java.de.jez_lynn.widgetCreator.handler.Imageprocessing;
import main.java.de.jez_lynn.widgetCreator.helper.UploadData;
import main.java.de.jez_lynn.widgetCreator.reference.Reference;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Michael on 29.10.2014.
 */
public class ImageSelectorScene {

    private Scene scene;

    private TextField title;
    private Label path;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private HashMap<String, UploadData> data = new HashMap<String, UploadData>(4);
    private ImageView image;

    public ImageSelectorScene(Stage stage) {
        BorderPane pane = new BorderPane();

        GridPane top = topControl(stage);
        pane.setTop(top);


        GridPane info = infoPane();
        pane.setCenter(info);

        HBox bottom = bottomControl(stage);
        pane.setBottom(bottom);
        pane.setStyle("-fx-background-color: #336699;");

        scene = new Scene(pane, 800, 600);
    }

    private static void configureFileChooser(final FileChooser fileChooser) {

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Alle Bilddateien", "*.jpg", "*.jpeg", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    public Scene getScene() {
        return scene;
    }

    public GridPane topControl(final Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 12, 15, 12));
        grid.setStyle("-fx-background-color: #336699;");

        final Label titleLabel = new Label("Titel: ");
        titleLabel.setTextFill(Color.web("#ffffff"));
        titleLabel.setFont(new Font("Cambria", 16));
        grid.add(titleLabel, 0, 0);

        title = new TextField();
        grid.add(title, 1, 0);

        Label picturePath = new Label("Bild: ");
        picturePath.setTextFill(Color.web("#ffffff"));
        picturePath.setFont(new Font("Cambria", 16));
        grid.add(picturePath, 0, 1);

        path = new Label();
        path.setTextFill(Color.web("#ffffff"));
        path.setFont(new Font("Cambria", 16));
        grid.add(path, 1, 1);

        Button buttonSearch = new Button("Durchsuchen");
        buttonSearch.setPrefSize(100, 20);
        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final FileChooser fileChooser = new FileChooser();
                configureFileChooser(fileChooser);
                final File selectedDirectory = fileChooser.showOpenDialog(stage);
                if (selectedDirectory != null) {
                    System.out.println(selectedDirectory.getAbsolutePath());
                    path.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });
        grid.add(buttonSearch, 2, 1);

        Button add = new Button("Hinzufügen");
        add.setPrefSize(100, 20);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!items.contains(title.getText())) {
                    Imageprocessing processer = Imageprocessing.getInstance();
                    UploadData uploadData = new UploadData();
                    uploadData.title = title.getText();
                    uploadData.path = path.getText();
                    uploadData.image = processer.cut(processer.exclusion(path.getText()).getAbsolutePath());
                    data.put(uploadData.title, uploadData);
                    items.add(uploadData.title);
                } else {
                    Stage error = new Stage();
                    error.initModality(Modality.WINDOW_MODAL);
                    error.setScene(new Scene(VBoxBuilder.create().children(new Text("Dieser Titel wurde bereits vergeben!\nBitte wählen Sie einen anderen.")).alignment(Pos.CENTER).padding(new Insets(5)).build()));
                    error.show();
                }
            }
        });
        grid.add(add, 0, 2);

        return grid;
    }

    private GridPane infoPane() {
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 12, 15, 12));
        grid.setStyle("-fx-background-color: #ffffff;");

        ScrollPane scrollPane = new ScrollPane();
        final ListView<String> list = new ListView<String>();
        list.setItems(items);
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Image imageLoaded = new Image(data.get(list.getSelectionModel().getSelectedItem()).image.toURI().toString(), 200, 150, true, true);
                image.setImage(imageLoaded);
            }
        });
        scrollPane.setContent(list);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        grid.add(scrollPane, 0, 0);

        image = new ImageView();
        grid.add(image, 1, 0);
        return grid;
    }

    public HBox bottomControl(final Stage stage) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #336699;");

        Button buttonUpload = new Button("Upload");
        buttonUpload.setPrefSize(100, 20);
        buttonUpload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Iterator<Map.Entry<String, UploadData>> it = data.entrySet().iterator();
                FTP ftp = new FTP();
                while (it.hasNext()) {
                    Map.Entry<String, UploadData> pair = it.next();
                    ftp.transferData(pair.getValue().image);
                    it.remove();
                }
                ftp.close();
                close(stage);
            }
        });

        Button buttonCancel = new Button("Abbrechen");
        buttonCancel.setPrefSize(100, 20);
        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close(stage);
            }
        });
        hBox.getChildren().addAll(buttonUpload, buttonCancel);

        return hBox;
    }

    public void close(Stage stage) {
        try {
            FileUtils.deleteDirectory(new File(Reference.TEMP));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.close();
    }
}
