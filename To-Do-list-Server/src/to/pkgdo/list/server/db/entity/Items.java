/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.server.db.entity;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Elesdody
 */
public class Items {
    private int id ;
    private int listId;
    private String title;
    private String description;
    private Date deadLine;
    private Date startTime;

    public Items(int id, int listId,String title, String description, Date deadLine, Date startTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }
    public int getListId()
    {
    return listId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public Date getStartTime() {
        return startTime;
    }

    
   
}
