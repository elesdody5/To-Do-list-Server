/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import connection.ClientHandler;
import connection.HttpRequestHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("portListener is up and running");
            jsoServerSocket = new ServerSocket(JSON_PORT);

            while (true) {
                System.out.println("inside portListener");

                Socket s = jsoServerSocket.accept();
                if (getIsStart()) {
                    new HttpRequestHandler(s);
                } else {
                    s.close();
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
