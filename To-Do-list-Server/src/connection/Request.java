/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import org.json.JSONObject;
import to.pkgdo.list.server.db.Repository;

/**
 *
 * @author Elesdody
 */
public class Request implements HttpRequest{

    Repository repository ;

    public Request() {
        repository = new Repository();
    }

    @Override
    public  JSONObject post(String[] paramter, JSONObject body) {

        if (paramter[0].equals("list")) {
            /*Elesdody*/
            
            /*Elesdody*/
        }
        return body;
    }

    @Override
    public  JSONObject get(String[] paramter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public  int put(String[] paramter, JSONObject body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public  int delete(String[] paramter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
