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
public class Collaborator {
    private int userId;
    private int toDoId;

    public Collaborator(int userId, int toDoId) {
        this.userId = userId;
        this.toDoId = toDoId;
    }

    public int getUserId() {
        return userId;
    }

    public int getToDoId() {
        return toDoId;
    }

   
    
}
