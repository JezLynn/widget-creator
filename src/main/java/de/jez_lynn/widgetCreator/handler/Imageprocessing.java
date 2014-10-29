package main.java.de.jez_lynn.widgetCreator.handler;

import main.java.de.jez_lynn.widgetCreator.helper.BlendComposite;
import main.java.de.jez_lynn.widgetCreator.reference.Reference;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael on 29.10.2014.
 */
public class Imageprocessing {

    private static Imageprocessing processer = new Imageprocessing();

    public static Imageprocessing getInstance() {
        return processer;
    }

    public File exclusion(String ref) {
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
        File out = new File(Reference.TEMP + "\\1_" + name);
        try {
            ImageIO.write(result, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public File cut(String ref) {
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
        g2.setBackground(new java.awt.Color(1f, 1f, 1f, 0f));
        g2.setColor(new java.awt.Color(0xdcdcdc));
        int x[] = {0, orig.getWidth(), orig.getWidth(), 0};
        int y[] = {orig.getHeight(), orig.getHeight(), (int) (orig.getHeight() * 0.9), orig.getHeight()};
        int n = 4;
        g2.fillPolygon(x, y, n);
        int x2[] = {0, 0, orig.getWidth(), 0};
        int y2[] = {(int) (orig.getHeight() * 0.1), 0, 0, (int) (orig.getHeight() * 0.1)};
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
}
