/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import Enum.REQUEST;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

import org.json.JSONObject;
import serverDatabase.Repository;

import serverEntity.Items;
import serverEntity.ToDoList;
import serverEntity.User;

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
        if (paramter[1].equals("list")) {
            try {
                ToDoList list;

                list = getTodoObject(body);

                int resullt = repository.insertList(list);

                return resullt != -1 ? new JSONObject("{id:" + resullt + "}") : new JSONObject("{Error:\"Error insert list \"}");
            } catch (SQLException ex) {
                try {
                    System.out.println(ex.getMessage());
                    return new JSONObject("{Error:\"Error insert list \"}");
                } catch (JSONException ex1) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
            } catch (ParseException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }

        } /*Elesdody*/ /*Aml*/ else if (paramter[1].equals("register")) {
            try {
                String userName = body.getString("username");
                String password = body.getString("password");
                int insertResult = repository.insertUser(userName, password);

                body = new JSONObject();
                if (insertResult == 1) {
                    body.put("result", "successfullyRegisteration");
                } else {
                    body.put("result", "User already exist in DB");
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        /*Aml*/

 /*Aml*/
 /*Aml*/
 /*Ghader*/
 /*Ghader*/
 /*Sara*/
        if (paramter[1].equals("Task")) {
            try {
                String titleFromJson = (String) body.get("title");
                Items item = new Items(titleFromJson);
                System.out.print(titleFromJson);
                try {
                    repository.insertItemToDataBase(item);
                } catch (SQLException ex) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        } /*Sara*/ /*Ashraf*/ else if (paramter[1].equals(REQUEST.LOGIN)) {
            JSONObject respond = repository.getUser(paramter, body);
            return respond;
        }
        /*Ashraf*/
        return body;
    }

    @Override
    public JSONObject get(String[] paramter) {
        /*Elesdody*/
        if (paramter[1].equals("todo")) {
            try {
                ArrayList<ToDoList> toDoList = repository.getUserToDo(Integer.parseInt(paramter[2]));
                User user = repository.getUserData(Integer.parseInt(paramter[2]));
                Gson gson = new GsonBuilder().create();
                String TodoArray = gson.toJson(toDoList);
                JSONArray todojsonArray = new JSONArray(TodoArray);
                JSONObject userJosn = user.getUserAsJson();
                userJosn.put("todo_list", todojsonArray);
                System.out.println(userJosn);
                return userJosn;
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                System.out.println(ex);
            }
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
        return null;
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
        /*Elesdody*/
 /*Elesdody*/

 /*Ashraf*/
 /*Ashraf*/
 /*Aml*/
 /*Aml*/
 /*Ghader*/
 /*Ghader*/
 /*Sara*/
 /*Sara*/
    }

    private ToDoList getTodoObject(JSONObject body) throws JSONException, ParseException {
        ToDoList oDoList = new ToDoList(body.getString("title"), body.getInt("ownerId"), body.getString("startDate"), body.getString("deadLine"), body.getString("color"));
        return oDoList;
    }

}
