/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import org.json.JSONObject;
import serverDatabase.Repository;

/**
 *
 * @author Elesdody
 */
public class Request implements HttpRequest {

    Repository repository;

    public Request() {
        repository = new Repository();
    }

    @Override
    public JSONObject post(String[] paramter, JSONObject body) {

        /*Elesdody*/
        if (paramter[0].equals("list")) {

        }
        /*Elesdody*/

 /*Ashraf*/
 /*Ashraf*/
 /*Aml*/
 /*Aml*/
 /*Ghader*/
 /*Ghader*/
 /*Sara*/
 /*Sara*/
        return body;
    }

    @Override
    public JSONObject get(String[] paramter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int put(String[] paramter, JSONObject body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(String[] paramter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
