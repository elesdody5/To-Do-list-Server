/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Elesdody
 */
public class HttpRequestHandler extends Thread {

    BufferedReader in;
    PrintStream ps;
    Socket s;

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
        try {
            // first read http clientRequest type(GET , POST,PUT,DELETE);
            String clientRequest = in.readLine();
            String[] paramter = in.readLine().split("/");

            switch (clientRequest) {
                case "POST":
                    JSONObject requestJson = readJson();

                    JSONObject responseJson = request.post(paramter, requestJson);
                    ps.println(responseJson.toString());
                    // to notifay the client the response was ended 
                    ps.println("-1");
                    close();
                    break;

                case "GET":
                    responseJson = request.get(paramter);
                    ps.println(responseJson.toString());
                    // to notifay the client the response was ended 
                    ps.println("-1");
                    close();
                    break;
                case "PUT":
                    requestJson = readJson();
                    int response = request.put(paramter, requestJson);
                    ps.println(response);
                    ps.println("-1");
                    close();
                    break;
                case "DELETE":
                    response = request.delete(paramter);
                    ps.println(response);
                    ps.println("-1");
                    close();
                    break;

            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(HttpRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    JSONObject readJson() throws IOException, JSONException {
        StringBuilder body = new StringBuilder();
        String data = in.readLine();

        while (!data.equals("-1")) {
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
