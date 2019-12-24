/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.server.db.entity;

import java.util.Date;
import java.util.List;
import to.pkgdo.list.server.db.DAO;

/**
 *
 * @author Elesdody
 */
public class ToDoList implements DAO<ToDoList>{
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

    @Override
    public List<ToDoList> getAllData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ToDoList getItem(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(ToDoList object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(ToDoList object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
