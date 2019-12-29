/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import connection.HttpRequestHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Elesdody
 */
public class PortListener {

    private ServerSocket jsoServerSocket;
    private static final int JSON_PORT = 5005;
    private ServerSocket serverSocket;
    private static final int PORT = 8080;

    public PortListener() {
        jsonPortListener();
    }

    private void jsonPortListener() {
        try {
            jsoServerSocket = new ServerSocket(JSON_PORT);
            while (true) {

                Socket s = jsoServerSocket.accept();
               
                new HttpRequestHandler(s);
                
                
               
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void listener() {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {

                Socket s = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                while (in.readLine() != null) {
                    System.out.println(in.readLine());
                };

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
