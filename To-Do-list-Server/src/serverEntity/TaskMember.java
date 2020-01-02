/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverEntity;

import java.util.List;

/**
 *
 * @author Elesdody
 */
public class TaskMember implements Entity{
    private int userId;
    private int itemId;

    public TaskMember(int userId, int itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

  
}
