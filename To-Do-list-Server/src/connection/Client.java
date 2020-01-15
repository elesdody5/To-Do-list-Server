/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import Enum.REQUEST;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import serverEntity.Notifications;
import serverEntity.User;

/**
 *
 * @author Ashraf Mohamed
 */
public class Client {

    private int id;
    private String clientName;
    private BufferedReader in;
    private PrintStream ps;
    private Socket s;
    private static Vector<Client> clientVector = new Vector<>();

    public Client(int id, String clientName, RequestHandler httpRequestHandler) {
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

    public static Vector<Client> getclientVector() {
        return clientVector;
    }

    /*Elesdody*/
    public static void notifyCollaborator(ArrayList<Notifications> notifications) {

        for (Notifications notification : notifications) {
            for (Client client : clientVector) {
                if (client.getId() == notification.getToUserId()) {
                    client.ps.println(REQUEST.NOTIFICATION);
                    client.ps.println(toNotifcationJson(notification));
                    // to notifiy user end of data
                    client.ps.println(REQUEST.END);
                }
            }
        }
    }

    private static String toNotifcationJson(Notifications notification) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(notification);

    }
    /*Elesdody*/
    
    
    public static void removeClient(int userId , ArrayList<User> friends){
        for(int i = 0 ;i<clientVector.size();i++){
            if(clientVector.get(i).getId() == userId){
                clientVector.remove(i);
            }
        }
    }
}
