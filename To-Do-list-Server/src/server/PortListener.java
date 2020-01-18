/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import connection.Client;
import connection.RequestHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Elesdody
 */
public class PortListener {

    private ServerSocket jsoServerSocket;
    private static final int JSON_PORT = 5005;
    private static boolean isStart;

    public PortListener() {
        isStart = false;
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                jsonPortListener();
            }
        });
        th.start();
    }

    private void jsonPortListener() {
        try {
            jsoServerSocket = new ServerSocket(JSON_PORT);

            while (true) {

                Socket s = jsoServerSocket.accept();
                if (getIsStart()) {
                    new RequestHandler(s);
                } else {
                    s.close();
                }

            }
        } catch (IOException ex) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("This Posrt Number is In Use Now, Please Close any application using this Posrt Number " + JSON_PORT + " any try again");
                    alert.showAndWait();
                    Platform.exit();

                }

            });
            

        }
    }

    /*Ashraf  stop server operation*/
    //stop server operation
    public static void closeServer() {
        isStart = false;

    }

    //start a server operation
    public static void startServer() {
        isStart = true;
    }

    //get isStart value
    private boolean getIsStart() {
        return isStart;
    }

}
