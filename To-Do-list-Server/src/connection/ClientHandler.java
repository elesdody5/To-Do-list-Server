/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/**
 *
 * @author Ashraf Mohamed
 */
public class ClientHandler {
     
    private int id;
    private String clientName;
    private HttpRequestHandler httpRequestHandler;

    public ClientHandler(int id, String clientName, HttpRequestHandler httpRequestHandler) {
        this.id = id;
        this.clientName = clientName;
        this.httpRequestHandler = httpRequestHandler;
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

    public HttpRequestHandler getHttpRequestHandler() {
        return httpRequestHandler;
    }

    public void setHttpRequestHandler(HttpRequestHandler httpRequestHandler) {
        this.httpRequestHandler = httpRequestHandler;
    }
    
    
    
}
