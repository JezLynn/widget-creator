package main.java.de.jez_lynn.widgetCreator.handler;

import main.java.de.jez_lynn.widgetCreator.reference.Reference;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.nio.file.Paths;

/**
 * Created by Michael on 27.10.2014.
 */
public final class FTPUploadHandler {

    FTPClient client;

    public FTPUploadHandler() {
        client = new FTPClient();
        System.out.println("Created FTP-Client");
        try {
            this.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() throws IOException {
        client.connect(Reference.FTP.SERVER, Reference.FTP.PORT);
        if (client.login(Reference.FTP.USER, Reference.FTP.PASSWORD)) {
            System.out.println("Logged in");
        }
        client.enterLocalPassiveMode();
        client.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
    }

    public boolean transferData(File localFile) {
        System.out.println("Transfering: " + localFile.getAbsolutePath());
        String remotePath = "/images/" + Paths.get(localFile.getAbsolutePath()).getFileName().toString();
        System.out.println("To: " + remotePath);
        InputStream is = null;
        boolean done = false;
        try {
            is = new FileInputStream(localFile);
            done = client.storeFile(remotePath, is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        if (done) System.out.println("SUCCESS");
        return done;
    }

    public void close() {
        if (client.isConnected()) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
