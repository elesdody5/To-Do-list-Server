/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.server.db.entity;

import java.util.List;

/**
 *
 * @author Elesdody
 */
public class TaskMember{
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
