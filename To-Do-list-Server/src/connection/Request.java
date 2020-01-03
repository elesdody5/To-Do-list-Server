/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;


import Enum.REQUEST;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import serverDatabase.Repository;
import serverEntity.Items;

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
    public  JSONObject post(String[] paramter, JSONObject body) {
        System.out.println("length paramter : "+paramter.length);
        for (int i = 0; i < paramter.length; i++) {
            System.out.println("item"+i+" : "+paramter[i]);
        }
        if (paramter[0].equals("list")) {
            /*Elesdody*/
            
            /*Elesdody*/
            
             /*Aml*/
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
         /*Aml*/
            
     


 /*Aml*/
 /*Aml*/

 /*Ashraf*/
 /*Ashraf*/


 /*Ghader*/
 /*Ghader*/
 /*Sara*/
    if (paramter[1].equals("Task")) {
            try {
                String titleFromJson=(String) body.get("title");
                Items item=new Items(titleFromJson);
                System.out.print(titleFromJson);
                try {
                    repository.insertItemToDataBase(item);
                } catch (SQLException ex) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 /*Sara*/
 /*Ashraf*/
        if(paramter[1].equals(REQUEST.LOGIN)){
            JSONObject respond =repository.getUser(paramter, body);
            return respond;
        }
 /*Ashraf*/
        return body;
    }

    @Override
    public JSONObject get(String[] paramter) {
        /*Ashraf*/
        return new JSONObject();
        /*Ashraf*/
    }

    @Override
    public int put(String[] paramter, JSONObject body) {
        /*Ghader*/
 
        if (paramter[1].equals("setNewName")) {
            try {
                String id = body.getString("id");
                String name = body.getString("username");
                // 0 -> error to excute query 
                // 1-> is updated
                // 2-> name is already found
                int status = repository.updateUserName(id, name);
                return status;
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         if (paramter[1].equals("setPassword")) {
               try {
                String id = body.getString("id");
                String password = body.getString("password");
                // 0 -> error to execute query
                // 1 -> is updated 
                int status = repository.updatePassword(id, password);
                return status; 
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*Ghader*/
        return 0;
    }

    @Override
    public int delete(String[] paramter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
