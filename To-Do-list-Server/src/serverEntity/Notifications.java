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

    private int id;
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

    public Notifications(int id, int fromUserId, int toUserId, String fromUsername, int type, int status, int dataId, String dataName) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.fromUsername = fromUsername;
        this.type = type;
        this.status = status;
        this.dataId = dataId;
        this.dataName = dataName;
    }


    public Notifications(int fromUserId, int toUserId, int type, int status) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.type = type;
        this.status = status;
    }

    public Notifications(int fromUserId,String fromUserName ,int toUserId, int type, int status, int dataId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.type = type;
        this.dataId = dataId;
        this.status = status;
        this.fromUsername = fromUserName;

        
    }

    public Notifications(int fromUserId, int toUserId, int type) {

        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.type = type;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public Notifications() {
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

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public void setType(int type) {
        this.type = type;
    }
    

}
