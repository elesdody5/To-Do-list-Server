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
import java.util.logging.Level;
import java.util.logging.Logger;
import serverEntity.User;

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
            int insertionResult = statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("insert exception");
            System.out.println(ex.getCause());
        }
    }

    public void getUser(User user){
        if(user == null)return;
        
        try{
            PreparedStatement statement = db.prepareStatement("SELECT * FROM UserTable WHERE USER_NAME =? AND PASSWORD =?");
            statement.setString(1,user.getUserName());
            statement.setString(2, user.getPassword());
            ResultSet result = statement.executeQuery();
            boolean hasRow = result.next();
            System.out.println(result.getString("USER_NAME"));
            System.out.println(result.getString("PASSWORD"));
            System.out.println("has row "+hasRow);
        }catch(SQLException ex){
            
        }
    }
    /*Ashraf*/
 /*Aml*/
 /*Aml*/
 /*Ghader*/
 /*Ghader*/
 /*Sara*/
 /*Sara*/
}
