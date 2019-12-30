/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
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
        System.out.println("length paramter : "+paramter.length);
        for (int i = 0; i < paramter.length; i++) {
            System.out.println("item"+i+" : "+paramter[i]);
        }
        if (paramter[0].equals("list")) {
            /*Elesdody*/
            
            /*Elesdody*/
        } else if (paramter[1].equals("register"))
        {
            System.out.println("register");
            try {
                String userName = body.getString("username");
                String password = body.getString("password");
                System.out.println("username"+userName);
                System.out.println("password"+password);
              int insertResult =  repository.insertUser(userName,password);
              
              body = new JSONObject();
              if (insertResult == 1)
                  body.put("result", "successfullyRegisteration");
              else
                  body.put("result", "User already exist in DB");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
             
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
