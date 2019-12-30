/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import to.pkgdo.list.server.db.entity.Collaborator;
import to.pkgdo.list.server.db.entity.Items;
import to.pkgdo.list.server.db.entity.TaskMember;
import to.pkgdo.list.server.db.entity.ToDoList;

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
    
}
