/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Elesdody
 */
public class DataBase {

    private static volatile Connection db;

    static Connection getDatabase() throws SQLException {
        if (db == null) {
            synchronized (DataBase.class) {

                
                db = DriverManager.getConnection("jdbc:sqlite:H:\\Projects\\ITI\\java\\project\\To-Do-list-Server\\TODO.db");

            }
        }
        return db;
    }
}
