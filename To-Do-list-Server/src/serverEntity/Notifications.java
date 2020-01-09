/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverEntity;

/**
 *
 * @author ghadeerelmahdy
 */
public class Notifications {
    private int id ;
    private int fromUserId;
    private int toUserId;
    private String fromUsername;

  
    //type -> list or task
    private int type;
    //status -> accept or reject
    private int status;
    private int dataId;
    private String dataName;

   
    

    public Notifications(int id, int fromUserId, int toUserId, int type, int status, int dataId) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.type = type;
        this.status = status;
        this.dataId = dataId;
    }

    public Notifications( int fromUserId, int toUserId, int type, int dataId) {
     
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.type = type;
        this.dataId = dataId;
    }

    public int getId() {
        return id;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public int getDataId() {
        return dataId;
    }
     
     public void setfromUserName(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUserName() {
        return fromUsername;
    }
     public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
}
