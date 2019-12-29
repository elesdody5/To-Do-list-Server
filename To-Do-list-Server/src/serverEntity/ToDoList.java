/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverEntity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Elesdody
 */
public class ToDoList implements Entity{
    private int id ;
    private String title;
    private int ownerId;
    private Date deadLine;
    private Date startTime;

    public ToDoList(int id, String title, int ownerId, Date deadLine, Date startTime, int itemId) {
        this.id = id;
        this.title = title;
        this.ownerId = ownerId;
        this.deadLine = deadLine;
        this.startTime = startTime;
    }



    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public Date getStartTime() {
        return startTime;
    }
    
    
}
