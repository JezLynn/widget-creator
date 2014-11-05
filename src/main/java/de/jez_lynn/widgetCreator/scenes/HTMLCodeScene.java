package main.java.de.jez_lynn.widgetCreator.scenes;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
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
    boolean testing = false;

    public HTMLCodeScene(HashMap<String, UploadData> data, boolean testing) {
        this.data = data;
        this.testing = testing;
        if (!testing) {
            BorderPane pane = new BorderPane();
            TextArea area = new TextArea();
            area.setText(codeWrapper());
            pane.setTop(area);
            scene = new Scene(pane, 800, 400);
        }
    }

    /**
     * Will join all Strings to the finished html widget
     *
     * @return filled TextArea
     */
    public String codeWrapper() {
        StringBuilder sb = new StringBuilder();
        HashMap<String, UploadData> descriptions = new HashMap<String, UploadData>(32);
        Map.Entry<String, String> sideInfo = new AbstractMap.SimpleEntry<String, String>("", "");

        //Begin the widget with the top holder part
        sb.append(Reference.WIDGET.topHolder);
        Iterator<String> it = data.keySet().iterator();
        while (it.hasNext()) {
            UploadData uData = data.get(it.next());
            System.out.println(uData.title);
            //find the main Image and add it to the first Image line
            if (uData.isMain()) {
                it.remove();
                String bigImage = String.format(Reference.WIDGET.bigImage, uData.ftpPath(), uData.name());
                sb.append(bigImage);
                sideInfo = new AbstractMap.SimpleEntry<String, String>(uData.title, uData.description);
            }
        }

        it = data.keySet().iterator();

        if (it.hasNext()) {
            UploadData smallFirstLine = data.get(it.next());
            it.remove();

            sb.append(String.format(Reference.WIDGET.smallImage, smallFirstLine.title, smallFirstLine.name(), smallFirstLine.ftpPath()));
            descriptions.put(smallFirstLine.title, smallFirstLine);
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
            descriptions.put(first.title, first);
            descriptions.put(second.title, second);
        }
        sb.append(String.format(Reference.WIDGET.sideInformation, sideInfo.getKey(), sideInfo.getValue()));

        Iterator<Map.Entry<String, UploadData>> description = descriptions.entrySet().iterator();

        while (description.hasNext()) {
            Map.Entry<String, UploadData> d = description.next();
            sb.append(Reference.WIDGET.beginProjectDescription);
            sb.append(String.format(Reference.WIDGET.projectDescription, d.getValue().name(), d.getKey(), d.getValue().description));
            sb.append(Reference.WIDGET.endProjectDescription);
        }

        return sb.toString();
    }

    public Scene getScene() {
        return scene;
    }
}
