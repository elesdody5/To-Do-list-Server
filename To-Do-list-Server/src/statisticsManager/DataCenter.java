/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticsManager;

import serverDatabase.Repository;

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
    
    
    //get number of lists
    public int getNumberOfLists(){
        return 0 ;
    }
    
    //get number of item for each list
    public int getNumberOfItems(int listId){
        return  0 ;
    }
    
    //get number of friend for each user
    public int getNumberOfFriends(int userId){
        return 0 ;
    }
    
    //get number of tasks for each user/team member
    public int getNumberOfTasks(int userId){
        return 0 ;
    }
    
    
}
