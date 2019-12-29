/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void insertRecord() {
        try {
            System.out.println("insert recode");
            PreparedStatement statement = db.prepareStatement("INSERT INTO USER_TABLE (USER_NAME,PASSWORD) VALUES(?,?)");
            statement.setString(1, "ASHRAF");
            statement.setString(2, "PASSWORD");
            statement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getCause());
            System.out.println("insert record exception");
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
