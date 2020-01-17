/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticsManager.Entity;

/**
 *
 * @author Ehab mohamed
 */
public class UserData {
    
    private int numberOfFriends ;
    private int numberOfLists;
    private int numberOfItemAssign;

    public UserData(int numberOfFriends, int numberOfLists, int numberOfItemAssign) {
        this.numberOfFriends = numberOfFriends;
        this.numberOfLists = numberOfLists;
        this.numberOfItemAssign = numberOfItemAssign;
    }

    
    
    public int getNumberOfItemAssign() {
        return numberOfItemAssign;
    }

    public void setNumberOfItemAssign(int numberOfItemAssign) {
        this.numberOfItemAssign = numberOfItemAssign;
    }

    
    public int getNumberOfFriends() {
        return numberOfFriends;
    }

    public void setNumberOfFriends(int numberOfFriends) {
        this.numberOfFriends = numberOfFriends;
    }

    public int getNumberOfLists() {
        return numberOfLists;
    }

    public void setNumberOfLists(int numberOfLists) {
        this.numberOfLists = numberOfLists;
    }

    
    
    
}
