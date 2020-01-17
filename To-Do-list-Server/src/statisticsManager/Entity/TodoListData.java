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
public class TodoListData {
    
    String ownerName ;
    String title;
    int numberOfItems;
    int numberOfCollaborator;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public int getNumberOfCollaborator() {
        return numberOfCollaborator;
    }

    public void setNumberOfCollaborator(int numberOfCollaborator) {
        this.numberOfCollaborator = numberOfCollaborator;
    }
    
   
    
    
}
