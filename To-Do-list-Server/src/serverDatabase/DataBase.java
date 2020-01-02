/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverDatabase;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Elesdody
 */
public class DataBase {
    private static final String DATABASE_URL="jdbc:sqlite:D:\\java work space\\other projects\\Java Project\\To-Do-list-Server\\TODO.db";
    private static volatile Connection db;

    static Connection getDatabase() throws SQLException {
        if (db == null) {
            synchronized (DataBase.class) {

                db = DriverManager.getConnection(DATABASE_URL);

            }
        }
        return db;
    }
}
