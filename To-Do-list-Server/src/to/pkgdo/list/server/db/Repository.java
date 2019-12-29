/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.server.db;

import java.sql.Connection;
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
    /*Ashraf*/
    /*Aml*/
    /*Aml*/
    /*Ghader*/
    /*Ghader*/
    /*Sara*/
    /*Sara*/
}
