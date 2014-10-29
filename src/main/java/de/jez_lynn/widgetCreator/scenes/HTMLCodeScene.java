package main.java.de.jez_lynn.widgetCreator.scenes;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.de.jez_lynn.widgetCreator.helper.UploadData;
import main.java.de.jez_lynn.widgetCreator.reference.Reference;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Michael on 29.10.2014.
 */
public class HTMLCodeScene {

    Scene scene;
    HashMap<String, UploadData> data;

    public HTMLCodeScene(Stage stage, HashMap<String, UploadData> data) {
        BorderPane pane = new BorderPane();
        this.data = data;
        pane.setTop(codeWrapper());
        scene = new Scene(pane, 800, 400);
    }

    /**
     * Will join all Strings to the finished html widget
     *
     * @return filled TextArea
     */
    public TextArea codeWrapper() {
        TextArea area = new TextArea();
        StringBuilder sb = new StringBuilder();
        HashMap<String, String> descriptions = new HashMap<String, String>(32);
        Map.Entry<String, String> sideInfo = new AbstractMap.SimpleEntry<String, String>("", "");

        //Begin the widget with the top holder part
        sb.append(Reference.WIDGET.topHolder);
        Iterator<String> it = data.keySet().iterator();
        while (it.hasNext()) {
            UploadData uData = data.get(it.next());
            //find the main Image and add it to the first Image line
            if (uData.isMain()) {
                it.remove();
                String bigImage = String.format(Reference.WIDGET.bigImage, Reference.FTP.SERVER + "/images/" + uData.filename(), uData.name(), uData.name());
                sb.append(bigImage);
                sideInfo = new AbstractMap.SimpleEntry<String, String>(uData.title, uData.description);
            }
        }

        it = data.keySet().iterator();

        if (it.hasNext()) {
            UploadData smallFirstLine = data.get(it.next());
            it.remove();

            sb.append(String.format(Reference.WIDGET.smallImage, smallFirstLine.title, smallFirstLine.name(), smallFirstLine.ftpPath()));
            descriptions.put(smallFirstLine.title, smallFirstLine.description);
        }

        while (it.hasNext()) {
            UploadData first = data.get(it.next());
            it.remove();
            UploadData second;
            if (it.hasNext()) {
                second = data.get(it.next());
                it.remove();
            } else second = first;

            String everyOtherLine = String.format(Reference.WIDGET.secondLine, first.title, first.name(), first.ftpPath(), second.title, second.name(), second.ftpPath());
            sb.append(everyOtherLine);
            descriptions.put(first.title, first.description);
            descriptions.put(second.title, second.description);
        }
        sb.append(String.format(Reference.WIDGET.sideInformation, sideInfo.getKey(), sideInfo.getValue()));

        Iterator<Map.Entry<String, String>> description = descriptions.entrySet().iterator();

        while (description.hasNext()) {
            Map.Entry<String, String> d = description.next();
            sb.append(Reference.WIDGET.beginProjectDescription);
            sb.append(String.format(Reference.WIDGET.projectDescription, d.getKey(), d.getValue()));
            sb.append(Reference.WIDGET.endProjectDescription);
        }
        area.setText(sb.toString());
        return area;
    }

    public Scene getScene() {
        return scene;
    }
}
