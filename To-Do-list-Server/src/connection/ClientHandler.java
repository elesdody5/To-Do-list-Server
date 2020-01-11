/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import Enum.REQUEST;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import serverEntity.Notifications;

/**
 *
 * @author Ashraf Mohamed
 */
public class ClientHandler {

    private int id;
    private String clientName;
    private BufferedReader in;
    private PrintStream ps;
    private Socket s;
    private static Vector<ClientHandler> clientVector = new Vector<>();

    public ClientHandler(int id, String clientName, HttpRequestHandler httpRequestHandler) {
        this.id = id;
        this.clientName = clientName;
        this.s = httpRequestHandler.getS();
        this.in = httpRequestHandler.getBufferReader();
        this.ps = httpRequestHandler.getPrintStream();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public static Vector<ClientHandler> getclientVector() {
        return clientVector;
    }

    public static void notifyCollaborator(ArrayList<Notifications> notifications) {
        
        for (Notifications notification : notifications) {
         for(ClientHandler client : clientVector)
         {
             if(client.getId()==notification.getToUserId())
             {
                 client.ps.println(REQUEST.NOTIFICATION);
                 client.ps.println(notification.getFromUserId());
                 // notification to add collaborator
                 client.ps.println(notification.getType());
                 // send todo id
                 client.ps.println(notification.getDataId());
                 System.out.println(client.getId());
             }
         }
        }
    }
}
