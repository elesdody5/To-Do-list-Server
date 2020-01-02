/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import serverDatabase.Repository;
import serverEntity.ToDoList;

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

    private ToDoList getTodoObject(JSONObject body) throws JSONException, ParseException {
        ToDoList oDoList = new ToDoList(body.getString("title"), body.getInt("ownerId"), body.getString("startDate"), body.getString("deadLine"), body.getString("color"));
        return oDoList;
    }

}
