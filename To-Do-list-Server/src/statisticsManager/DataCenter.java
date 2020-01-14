/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticsManager;

import java.sql.SQLException;
import java.util.ArrayList;
import serverDatabase.Repository;
import serverEntity.User;
import statisticsManager.Entity.UserData;

/**
 *
 * @author Ashraf mohamed
 * 
 * this class is for collecting data from database
 */
public class DataCenter {
    private Repository repository;
    
    
    public DataCenter (){
        repository = new Repository();
    }
    
    //get number of users
    public int getNumberOfUsers(){
        int numberOfUsers = repository.getNumberOfUsers();
        return numberOfUsers;
    }
    
    //get list of users
    public ArrayList<User> getListOfUsers() throws SQLException{
        ArrayList<User> users = repository.getListOfUsers();
        return users;
    }
    
    //get user statisctics data 
    public UserData getUserData(int userId) throws SQLException{
        UserData userData = repository.getUserStatisticsData(userId);
        return userData;
    }
    
    
}
