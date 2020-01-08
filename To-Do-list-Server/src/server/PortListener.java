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

/**
 *
 * @author Elesdody
 */
public class PortListener {

    private ServerSocket jsoServerSocket;
    private static final int JSON_PORT = 5005;
    public PortListener() {
        jsonPortListener();
    }

    private void jsonPortListener() {
        try {
            jsoServerSocket = new ServerSocket(JSON_PORT);
            //vector<client>
            while (true) {

                Socket s = jsoServerSocket.accept();
                //add to vector
                new HttpRequestHandler(s);                
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

  
    
    
    

}
