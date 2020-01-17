/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticsManager;

import java.sql.SQLException;
import java.util.ArrayList;
import server.ServerController2;
import serverDatabase.Repository;
import serverEntity.ToDoList;
import serverEntity.User;
import statisticsManager.Entity.TodoListData;
import statisticsManager.Entity.UserData;

/**
 *
 * @author Ashraf mohamed
 *
 * this class is for collecting data from database
 */
public class DataCenter {

    private Repository repository;
    private ServerController2 serverController2;

    public DataCenter() {
        repository = new Repository();
        serverController2 = new ServerController2();
    }

    //get number of users
    public int getNumberOfUsers() {
        int numberOfUsers = repository.getNumberOfUsers();
        return numberOfUsers;
    }

    //get list of users
    public ArrayList<User> getListOfUsers() throws SQLException {
        ArrayList<User> users = repository.getListOfUsers();
        return users;
    }

    //get user statisctics data 
    public UserData getUserData(int userId) throws SQLException {
        return repository.getUserStatisticsData(userId);
    }
    
    // get TodoList statistics data
    public TodoListData getTodoListData(int listId) throws SQLException{
        return repository.getTodoListStatisticsData(listId);
    }

    //get number of lists
    public int getNumberOfLists() throws SQLException {
        int numberOfLists = repository.getNumberOfLists();
        return numberOfLists;
    }

    //update online users
   /* public void updateOnlineUsers(int onlineUsers) {
        serverController2.setOnlineUsersNumber(onlineUsers);
    }*/
    
    //get list of ToDoList
    public ArrayList<ToDoList> getToDoList() throws SQLException{
        return repository.getListofToDoList();
    }

}
