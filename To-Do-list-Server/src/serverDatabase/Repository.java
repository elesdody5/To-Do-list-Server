/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverEntity.ToDoList;

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
    public int insertList(ToDoList list) throws SQLException {
        PreparedStatement pre = db.prepareStatement("insert into TODO_LIST (title,ownerId,startDate,deadLine,color,Descreption)VALUES (?,?,?,?,?,?)");
        pre.setString(1, list.getTitle());
        pre.setInt(2, list.getOwnerId());
        pre.setDate(3, Date.valueOf(list.getStartTime()));
        pre.setDate(4, Date.valueOf(list.getDeadLine()));
        pre.setString(5, list.getColor());
        pre.setString(6, list.getDescription());
        int result = pre.executeUpdate();
        System.out.println(pre.toString());
        System.out.println(result);
        if (result != 0) {
            return getListWithTitle(list.getTitle());
        } else {
            return -1;
        }

    }

    public int getListWithTitle(String title) throws SQLException {
        PreparedStatement pre = db.prepareStatement("Select id from TODO_LIST where title = ?");
        pre.setString(1, title);
        ResultSet set = pre.executeQuery();
        set.next();
        return set.getInt(1);
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
}
