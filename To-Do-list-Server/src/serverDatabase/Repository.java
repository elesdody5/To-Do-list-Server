/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import serverEntity.User;
import Enum.RESPOND_CODE;

import serverEntity.Items;


/**
 *
 * @author Elesdody
 */
public class Repository {

    private Connection db;

    public Repository() {
        try {
            db = DataBase.getDatabase();

        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // TODO write query methods (select ,update ,insert ,delete )

    /*Aml*/
    public int insertUser(String userName, String password) {
        int x =-10;
        try {
        String insertString = "INSERT INTO USER_TABLE  (USER_NAME, PASSWORD)  VALUES  (" + "'" + userName + "' , " + "'" + password + "'" + ")";
           //String insertString = "INSERT INTO CONTACTS  (NAME, PHONENUMBER)  VALUES  (" + "'" + "xyzz" + "' , " + "'" + "8888888888888" + "'" + ")";
        System.out.println(insertString);
        Statement statment = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        //    connection.prepareStatement(queryString);
        //       x = statment.executeUpdate(queryString);
        PreparedStatement pst = db.prepareStatement(insertString);
         x = pst.executeUpdate();
        System.out.println("Result of insert" + x);
       
         } catch (SQLException ex) {
             ex.printStackTrace();
    }
         return x;

    // TODO write query methods (select ,update ,insert ,delete )
}
       /*Aml*/
    

   // TODO write query methods (select ,update ,insert ,delete )
    /*Elesdody*/

    /*Elesdody*/
 /*Elesdody*/
 /*Ashraf*/
    public void insertUser(User user) {
        if (user == null) {
            return;
        }
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO UserTable (USER_NAME , PASSWORD) VALUES(?,?)");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());

        } catch (SQLException ex) {
            System.out.println("insert exception");
            System.out.println(ex.getCause());
        }
    }

    public JSONObject getUser(String[] userDataParam, JSONObject body) {

        System.out.println(userDataParam[0] + " " + userDataParam[1]);
        JSONObject respondJson = new JSONObject();
        if (userDataParam == null && body == null) {
            try {
                respondJson.put("Code", RESPOND_CODE.FAILD);
                return respondJson;
            } catch (JSONException ex) {
                System.out.println("get user exception");

                Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try {

                String userName = body.getString("USER_NAME");
                String password = body.getString("PASSWORD");
                System.out.println("query with :" + userName + "    " + password);
                PreparedStatement statement = db.prepareStatement("SELECT * FROM User_table WHERE User_name =? AND Password =?");
                statement.setString(1, userName);
                statement.setString(2, password);
                ResultSet result = statement.executeQuery();
                boolean hasRow = result.next();
                if (hasRow) {
                    int userId = result.getInt("ID");
                    String resultUserName = result.getString("User_name");
                    String resultPassword = result.getString("Password");
                    respondJson.put("Code", RESPOND_CODE.SUCCESS);
                    respondJson.put("ID", userId);
                    respondJson.put("User_name", resultUserName);
                    respondJson.put("password", resultPassword);
                    System.out.println("repository result bllabala " + respondJson);

                } else {
                    respondJson.put("Code", RESPOND_CODE.FAILD);

                    System.out.println("no row with this email or passwords");
                }

            } catch (SQLException | JSONException ex) {
                System.out.println("Repository class , getUser method exception");
            }
        }
        return respondJson;
    }
    /*Ashraf*/

 /*Aml*/
 /*Aml*/
 /*Ghader*/
 /*Ghader*/
 /*Sara*/
 /*Sara*/

    /*Ashraf*/

 
    /*Ghader*/
    /*Ghader*/
    /*Sara*/

 /*Sara*/
    public void insertItemToDataBase(Items item) throws SQLException {
        String sql = "INSERT INTO Item(title) VALUES(?)";

        PreparedStatement pstmt = db.prepareStatement(sql);
        pstmt.setString(1, item.getTitle());
        pstmt.executeUpdate();

    }

    /*Sara*/


}
