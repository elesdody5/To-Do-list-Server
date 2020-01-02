/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverEntity;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Elesdody
 */
public class Items implements Entity {

    private int id;
    private int listId;
    private String title;

    private String description;
    private Date deadLine;
    private Date startTime;

    public Items(int id, int listId, String title, String description, Date deadLine, Date startTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public Items(String title) {

        this.title = title;

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

    public JSONObject writeTaskInfoObjectAsJson() {
        JSONObject toDoTaskJsonObject = null;
        try {
            toDoTaskJsonObject = new JSONObject();
            toDoTaskJsonObject.put("title", this.getTitle());

        } catch (JSONException ex) {
            Logger.getLogger(Items.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toDoTaskJsonObject;

    }
}
