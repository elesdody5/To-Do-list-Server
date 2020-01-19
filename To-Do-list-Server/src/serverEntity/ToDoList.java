/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverEntity;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Elesdody
 */
public class ToDoList implements Entity{
    private int id ;
    private String title;
    private String ownerName;
    private int ownerId;
    private String deadLine;
    private String startTime;
    private String color;
    private String description;
    private int numberOfTaskes;
    private int numberOfCollaborators;
    
    private ArrayList<Items> taskes;
    private ArrayList<User> collaborator;
    private String status;

    public void setTaskes(ArrayList<Items> taskes) {
        this.taskes = taskes;
    }

    public ArrayList<Items> getTaskes() {
        return taskes;
    }

    public void setCollab(ArrayList<User> collaborator)
    {
        this.collaborator= collaborator;
    }

    public ArrayList<User> getCollaborator() {
        return collaborator;
    }
   
    public ToDoList(int id, String title, int ownerId, String deadLine, String startTime, String color, String description, ArrayList<Items> taskes,ArrayList<User> collaborator) {
        this.id = id;
        this.title = title;
        this.ownerId = ownerId;
        this.deadLine = deadLine;
        this.startTime = startTime;
        this.color = color;
        this.description = description;
        this.taskes = taskes;
        this.collaborator = collaborator;
    }

    public ToDoList(String title, int ownerId,  String startTime,String deadLine, String color) {
        this.title = title;
        this.ownerId = ownerId;
        this.deadLine = deadLine;
        this.startTime = startTime;
        this.color = color;
    }
    
    public ToDoList(int id , String title, int ownerId,String desc,  String startTime,String deadLine, String status) {
        this.id = id;
        this.title = title;
        this.ownerId = ownerId;
        this.deadLine = deadLine;
        this.startTime = startTime;
        this.status = status;
        description = desc;
    }

    public String getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    public String getDeadLine() {
        return deadLine;
    }

    public String getStartTime() {
        return startTime;
    }
    
    
}
