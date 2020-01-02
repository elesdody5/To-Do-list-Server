/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;


/**
 *
 * @author Elesdody
 */
public class DataBase {

    private static volatile Connection db;

    static Connection getDatabase() throws SQLException {
        if (db == null) {
            synchronized (DataBase.class) {

                DriverManager.registerDriver(new ClientDriver());
                db = DriverManager.getConnection("jdbc:derby://localhost:1527/ToDoListDataBase2", "root", "root");

            }
        }
        return db;
    }
}
