package main.java.de.jez_lynn.widgetCreator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import main.java.de.jez_lynn.widgetCreator.helper.BlendComposite;
import main.java.de.jez_lynn.widgetCreator.reference.Reference;
import org.apache.commons.io.FileUtils;
import sun.net.ftp.FtpClient;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Michael on 27.10.2014.
 */
public class Main extends Application {

    private TextField title;
    private Label path;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private HashMap<String, UploadData> data = new HashMap<String, UploadData>(4);
    private ImageView image;

    public Main() {
        File dir = new File(Reference.TEMP);
        if(!dir.exists()){
            try {
                dir.mkdir();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Alle Bilddateien", "*.jpg", "*.jpeg", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane pane = new BorderPane();

        GridPane top = topControl(stage);
        pane.setTop(top);


        GridPane info = infoPane();
        pane.setCenter(info);

        HBox bottom = bottomControl(stage);
        pane.setBottom(bottom);
        pane.setStyle("-fx-background-color: #336699;");

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Test");
        stage.show();
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
                    UploadData uploadData = new UploadData();
                    uploadData.title = title.getText();
                    uploadData.path = path.getText();
                    uploadData.image = cut(exclusion(path.getText()).getAbsolutePath());
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
                while (it.hasNext()){
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

    private File exclusion(String ref) {
        Path path = Paths.get(ref).getParent();
        Path name = Paths.get(ref).getFileName();
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(new File(ref));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage overlay = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_INT_RGB);
        overlay.createGraphics();
        Graphics2D g2 = (Graphics2D) overlay.getGraphics();
        g2.setColor(new java.awt.Color(0x000d4c));
        g2.fillRect(0, 0, orig.getWidth(), orig.getHeight());
        g2.dispose();

        BufferedImage result = new BufferedImage(orig.getWidth(),
                orig.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        g2 = result.createGraphics();
        g2.drawImage(orig, 0, 0, null);
        g2.setComposite(BlendComposite.getInstance(BlendComposite.BlendingMode.EXCLUSION));
        g2.drawImage(overlay, 0, 0, null);
        g2.dispose();
        File out = new File(Reference.TEMP + "\\1_" +  name);
        try {
            ImageIO.write(result, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    private File cut(String ref) {
        Path path = Paths.get(ref).getParent();
        Path name = Paths.get(ref).getFileName();
        BufferedImage orig = null;
        try {
            orig = ImageIO.read(new File(ref));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage overlay = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_INT_ARGB);
        overlay.getGraphics();
        Graphics2D g2 = (Graphics2D) overlay.getGraphics();
        g2.setBackground(new java.awt.Color(1f,1f,1f,0f));
        g2.setColor(new java.awt.Color(0xdcdcdc));
        int x[] = {0, orig.getWidth(), orig.getWidth(), 0};
        int y[] = {orig.getHeight(), orig.getHeight(), (int) (orig.getHeight()*0.9), orig.getHeight()};
        int n = 4;
        g2.fillPolygon(x, y, n);
        int x2[] = {0, 0, orig.getWidth(), 0};
        int y2[] = {(int) (orig.getHeight()*0.1), 0, 0, (int) (orig.getHeight()*0.1)};
        int n2 = 4;
        g2.fillPolygon(x2, y2, n2);
        g2.dispose();

        BufferedImage result = new BufferedImage(orig.getWidth(),
                orig.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        g2 = result.createGraphics();
        g2.drawImage(orig, 0, 0, null);
        g2.drawImage(overlay, 0, 0, null);
        g2.dispose();
        File out = new File(Reference.TEMP + "\\2_" + name);
        try {
            ImageIO.write(result, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void close(Stage stage){
        try {
            FileUtils.deleteDirectory(new File(Reference.TEMP));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.close();
    }

    private static class UploadData {
        public String title;
        public String path;
        public File image;
    }
}
