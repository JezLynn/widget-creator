package main.java.de.jez_lynn.widgetCreator.handler;

import main.java.de.jez_lynn.widgetCreator.reference.Reference;

import java.io.*;

/**
 * Created by Michael on 29.10.2014.
 */
public class ConfigHandler {

    private static final String OPEN = "ftp {";

    private static final String CLOSE = "\n}";

    private static final String COMMENTARY = "\n\t# ";

    private static final String NEWLINE = "\n\t";

    public static void load(File file) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("=")) {
                    String[] split = line.split("=");
                    if (split[0].contains("server")) {
                        Reference.FTP.SERVER = split[1];
                    } else if (split[0].contains("port")) {
                        Reference.FTP.PORT = Integer.parseInt(split[1]);
                    } else if (split[0].contains("user")) {
                        Reference.FTP.USER = split[1];
                    } else if (split[0].contains("pass")) {
                        Reference.FTP.PASSWORD = split[1];
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void create(File file) {
        OutputStreamWriter out = null;
        StringBuilder sb = new StringBuilder();
        buildConfigFile(sb);
        try {
            out = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)));
            out.write(sb.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildConfigFile(StringBuilder sb) {
        sb.append(OPEN);
        sb.append(COMMENTARY).append("FTP Server Address");
        sb.append(NEWLINE).append("server=").append(Reference.FTP.SERVER);
        sb.append(COMMENTARY).append("FTP Server Port");
        sb.append(NEWLINE).append("port=").append(Reference.FTP.PORT);
        sb.append(COMMENTARY).append("Username for FTP Login");
        sb.append(NEWLINE).append("user=").append(Reference.FTP.USER);
        sb.append(COMMENTARY).append("Password for FTP Login");
        sb.append(NEWLINE).append("pass=").append(Reference.FTP.PASSWORD);
        sb.append(CLOSE);
    }
}
