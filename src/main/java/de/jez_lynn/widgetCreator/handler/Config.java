package main.java.de.jez_lynn.widgetCreator.handler;

import main.java.de.jez_lynn.widgetCreator.helper.FTPConfig;

import java.io.*;

/**
 * Created by Michael on 29.10.2014.
 */
public class Config {

    private static final String OPEN = "ftp {";

    private static final String CLOSE = "\n}";

    private static final String COMMENTARY = "\n\t# ";

    private static final String NEWLINE = "\n\t";

    public static FTPConfig load(File file){
        //TODO Still something to do
        return null;
    }

    public static void create(File file){
        OutputStreamWriter out = null;
        StringBuilder sb = new StringBuilder();
        buildConfigFile(sb);
        try {
            out =  new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)));
            out.write(sb.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildConfigFile(StringBuilder sb){
        sb.append(OPEN);
        sb.append(COMMENTARY).append("FTP Server Address");
        sb.append(NEWLINE).append("server=").append(FTPConfig.SERVER);
        sb.append(COMMENTARY).append("FTP Server Port");
        sb.append(NEWLINE).append("port=").append(FTPConfig.PORT);
        sb.append(COMMENTARY).append("Username for FTP Login");
        sb.append(NEWLINE).append("user=").append(FTPConfig.USER);
        sb.append(COMMENTARY).append("Password for FTP Login");
        sb.append(NEWLINE).append("pass=").append(FTPConfig.PASSWORD);
        sb.append(CLOSE);
    }
}
