/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import connection.ClientHandler;
import connection.HttpRequestHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private static Vector<ClientHandler> clientVector;
    private static HttpRequestHandler httpHandlerRequest;
    public PortListener() {
        clientVector = new Vector<>();
        jsonPortListener();
    }

    private void jsonPortListener() {
        try {
            jsoServerSocket = new ServerSocket(JSON_PORT);
            //vector<client>
            while (true) {

                Socket s = jsoServerSocket.accept();
                //add to vector
                httpHandlerRequest = new HttpRequestHandler(s);                
               
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

  
    
    
    public static void addClientToVector(int userId , String userName){
        ClientHandler clientHandler = new ClientHandler(userId,userName,httpHandlerRequest);
        System.out.println("client userName :"+userName+"   user id:"+userId);
        clientVector.add(clientHandler);
        System.out.println("number of clients is :"+clientVector.size());
    }

}
