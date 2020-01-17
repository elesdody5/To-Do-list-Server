/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverEntity;


import org.json.JSONException;
import org.json.JSONObject;


public class User implements Entity{
    private int id;
    private String userName;
    private String password;

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }
    
    public User(int id ,String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
    public User(String userName  , String password){
        this.userName = userName;
        this.password = password;
    }
    public User(){
        this.userName = "";
    }
    
    
    public int getId() {
        return id;
    }
    public void setId(int id )
    {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public JSONObject getUserAsJson() throws JSONException{
        JSONObject user = new JSONObject();
        user.put("ID",id);
        user.put("user name", userName);
        user.put("password",password);
        
        
        return user;
    }
    
    
    
    
    
}
