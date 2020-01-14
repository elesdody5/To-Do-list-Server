/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import Enum.REQUEST;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Elesdody
 */
public class HttpRequestHandler extends Thread {

    private BufferedReader in;
    private PrintStream ps;
    private Socket s;
    private ClientHandler clientHandler;

    public BufferedReader getBufferReader() {
        return in;
    }

    public PrintStream getPrintStream() {
        return ps;
    }

    public Socket getS() {
        return s;
    }

    public HttpRequestHandler(Socket s) {
        try {
            this.s = s;
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ps = new PrintStream(s.getOutputStream());
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        Request request = new Request();
        boolean connected= true;
        while (connected) {
            try {
                // first read http clientRequest type(GET , POST,PUT,DELETE);
                String clientRequest = in.readLine();
                String[] paramter = in.readLine().split("/");

                switch (clientRequest) {
                    case REQUEST.POST:
                        JSONObject requestJson = readJson();

                        JSONObject responseJson = request.post(paramter, requestJson,this);
                        ps.println(responseJson.toString());
                        // to notifay the client the response was ended 
                        ps.println(REQUEST.END);
                        break;

                    case REQUEST.GET:
                        responseJson = request.get(paramter);
                        ps.println(responseJson.toString());
                        // to notifay the client the response was ended 
                        ps.println(REQUEST.END);
                        break;
                    case REQUEST.PUT:
                        requestJson = readJson();
                        int response = request.put(paramter, requestJson);
                        ps.println(response);
                       // ps.println(REQUEST.END);
                        break;
                    case REQUEST.DELETE:
                        response = request.delete(paramter);
                        ps.println(response);
                        //ps.println(REQUEST.END);
                        break;
                    case REQUEST.LOGOUT:
                         // remove client from vector of client
                         // to disconnect the client 
                         System.out.println(paramter[1]);
                         removeClientFromVector(paramter[1]);
                         break;
                        

                }
            } catch (IOException | JSONException ex) {
                System.out.println(ex.getMessage());
                connected=false;
            }
        }
    }

    private boolean removeClientFromVector(String id){
        //conver id from string to int
        int userId = Integer.parseInt(id);
        //delegate to clientHandler remove operation
        boolean isRemoved = clientHandler.removeClientFromVector(userId);
        // if true then user with @id is exist in vector and removed succesfully.
        // if false then user with @id is NOT exist in the vector.
        return isRemoved;
        
    }
    JSONObject readJson() throws IOException, JSONException {
        StringBuilder body = new StringBuilder();
        String data = in.readLine();

        while (!data.equals(REQUEST.END)) {
            body.append(data);
            data = in.readLine();

        }
        return new JSONObject(body.toString());
    }

    private void close() throws IOException {
        in.close();
        s.close();
    }
}
