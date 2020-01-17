/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import Enum.REQUEST;
import Enum.RESPOND_CODE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Notification;
import org.json.JSONArray;

import org.json.JSONObject;
import server.PortListener;
import serverDatabase.Repository;

import serverEntity.Items;
import serverEntity.Notifications;
import serverEntity.ToDoList;
import serverEntity.User;

/**
 *
 * @author Elesdody
 */
public class Request implements HttpRequest {

    Repository repository;

    public Request() {
        repository = new Repository();
    }

    public JSONObject post(String[] paramter, JSONObject body, HttpRequestHandler handler) throws JSONException, SQLException {

        /*Elesdody*/
        if (paramter[1].equals("list")) {
            try {
                ToDoList list;

                list = getTodoObject(body);

                int resullt = repository.insertList(list);

                return resullt != -1 ? new JSONObject("{id:" + resullt + "}") : new JSONObject("{Error:\"Error insert list \"}");
            } catch (SQLException ex) {
                try {
                    System.out.println(ex.getMessage());
                    return new JSONObject("{Error:\"Error insert list \"}");
                } catch (JSONException ex1) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
            } catch (ParseException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (paramter[1].equals("notification")) {
            try {
                ArrayList<Notifications> notifications = getNotificatinArray(body.getJSONArray("notification_List"));
                // first insert in database
                int resullt = repository.insertTodoNotification(notifications);
                // notify other friends
                if (resullt > 0) {
                    ClientHandler.notifyCollaborator(notifications);

                }
                return resullt != -1 ? new JSONObject("{id:" + resullt + "}") : new JSONObject("{Error:\"Error insert list \"}");

            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }

        } /*Elesdody*/ /*Aml*/ else if (paramter[1].equals("register")) {
            try {
                String userName = body.getString("username");
                String password = body.getString("password");
                int insertResult = repository.insertUser(userName, password);

                body = new JSONObject();
                if (insertResult == 1) {
                    body.put("result", "1");
                } else {
                    body.put("result", "User already exist in DB");
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        /*Aml*/

 /*Aml*/
 /*Aml*/
 /*Ghader*/
 /*Ghader*/
 /*Sara*/
        if (paramter[1].equals("Task")) {
            try {
                String titleFromJson = (String) body.get("title");
                int listIdFromJson = (int) body.get("listId");
                String description = (String) body.get("description");
                String deadline = (String) body.get("deadLine");
                String starttime = (String) body.get("startTime");
                String comment = (String) body.get("comment");
                Items item = new Items(titleFromJson, listIdFromJson);
                item.setDeadLine(deadline);
                item.setDescription(description);
                item.setStartTime(starttime);
                item.setComment(comment);

                try {
                    repository.insertItemToDataBase(item);
                } catch (SQLException ex) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(paramter[1].equals("Assignnotification"))
        {
           /*   int fromUserId = 0 ;
              int toUserId = 0;
              int type = 0;
              int status=0;
              int dataId=0;
            try {
                fromUserId =  (int) body.get("fromUserId");
                toUserId =  (int) body.get("toUserId");
                type = (int) body.get("type");
                status=(int) body.get("status");
                dataId=(int) body.get("dataId");
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
                Notifications notificationData=new Notifications();
                notificationData.setFromUserId(fromUserId);
                notificationData.setToUserId(toUserId);
                notificationData.setType(type);
                
   try {
                    repository.insertNotificationToDataBase(notificationData);
                } catch (SQLException ex) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            JSONArray notificationArray=body.getJSONArray("listOfNotifications");
            List<Notifications>notificationList=new ArrayList<Notifications>();
            for(int i=0;i<notificationArray.length();i++){
            JSONObject notification = notificationArray.getJSONObject(i);
             Notifications notificationData=new Notifications();
                notificationData.setFromUserId(notification.getInt("fromUserId"));
                notificationData.setToUserId(notification.getInt("toUserId"));
                notificationData.setType(notification.getInt("type"));
                notificationData.setDataId(notification.getInt("dataId"));
                notificationData.setStatus(notification.getInt("status"));
                notificationList.add(notificationData);
                
            }
            repository.insertNotificationToDataBase(notificationList);

            } 
                
               
       
        /*Sara*/ /*Ashraf*/ else if (paramter[1].equals(REQUEST.LOGIN)) {
            try {
                User user = getUserFromJson(body);
                JSONObject respond = repository.getUser(user);
                // get last one been add to victor
                if (respond != null && respond.getInt("Code") == RESPOND_CODE.SUCCESS) {
                    //add user to server clients
                    int userId = respond.getInt("ID");
                    String userName = respond.getString("User_name");
                    ClientHandler.getclientVector().add(new ClientHandler(userId, userName, handler));
                } else {
                    System.out.println("login respond faild, not added to portListener any client");
                    // remove from vector
                }
                System.out.println(ClientHandler.getclientVector().size());
                for (ClientHandler clientt : ClientHandler.getclientVector()) {
                    System.out.println(clientt.getClientName());
                }
                return respond;
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*Ashraf*/
        return body;
    }

    @Override
    public JSONObject get(String[] paramter) {
        /*Elesdody*/
        if (paramter[1].equals("todo")) {
            try {
                //  get user data
                User user = repository.getUserData(Integer.parseInt(paramter[2]));

                // get user todo list
                ArrayList<ToDoList> toDoList = repository.getUserToDo(Integer.parseInt(paramter[2]));
                //get shared todo 
                ArrayList<ToDoList> shared = repository.getUserSharedToDo(Integer.parseInt(paramter[2]));                
                // get user friends
                ArrayList<User> friends = repository.getUserFriends(Integer.parseInt(paramter[2]));
                // get notificaiton
                ArrayList<Notifications>notificationses = repository.getUserNotification(Integer.parseInt(paramter[2]));
                Gson gson = new GsonBuilder().create();
                // convert friendsList to json
                String friendsArray = gson.toJson(friends);

                JSONArray friendsjsonArray = new JSONArray(friendsArray);
                // convert shared List to json
                String sharedArray = gson.toJson(shared);
                JSONArray sharedJSONArray = new JSONArray(sharedArray);
                // convert todoList to jsonArray
                String TodoArray = gson.toJson(toDoList);

                JSONArray todojsonArray = new JSONArray(TodoArray);
                // convert notification to json 
                String notificationArray = gson.toJson(notificationses);
                JSONArray notificationJSONArray = new JSONArray(notificationArray);
                // convert user to json
                JSONObject userJosn = user.getUserAsJson();
                // add friends to user
                userJosn.put("friends", friendsjsonArray);
                // add todolist to user 
                userJosn.put("todo_list", todojsonArray);
                // add shared List 
                userJosn.put("shared_list", sharedJSONArray);
                userJosn.put("notification", notificationJSONArray);
                return userJosn;
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                System.out.println(ex);
            }
        }
        /*Elesdody*/
 /*Ashraf*/
 /*Ashraf*/
 /*Aml*/
 /*Aml*/
 /*Ghader*/
 /*Ghader*/
 /*Sara*/
        if (paramter[1].equals("getTasksOflist")) {
            ArrayList<Items> itemList = null;
            try {

                itemList = repository.getTaskFromDataBase(Integer.parseInt(paramter[2]));
                 Gson gson = new GsonBuilder().create();

                String TodoItemsArray = gson.toJson(itemList);
                JSONArray todojsonArray = new JSONArray(TodoItemsArray);
                JSONObject jsonObjectOfList = new JSONObject();
                jsonObjectOfList.put("listOfTasks", todojsonArray);
                return jsonObjectOfList;

            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (paramter[1].equals("getTeamMemberInToDo")) {
            ArrayList<User> teamMember = null;
            try {
                teamMember = repository.getTeamMemberFromDataBase(Integer.parseInt(paramter[2]));
                Gson gson = new GsonBuilder().create();
                String TodoItemsArray = gson.toJson(teamMember);
                JSONArray TeamMemberjsonArray = new JSONArray(TodoItemsArray);
                JSONObject jsonObjectOfList = new JSONObject();
                jsonObjectOfList.put("listOfTeamMember", TeamMemberjsonArray);
                return jsonObjectOfList;

            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /**/
          if (paramter[1].equals("getTaskMemberInToDo")) {
            ArrayList<User> taskMember = null;
            try {
                taskMember = repository.getTaskMemberFromDataBase(Integer.parseInt(paramter[2]));
                Gson gson = new GsonBuilder().create();
                String taskMemberArray = gson.toJson(taskMember);
                JSONArray TeamMemberjsonArray = new JSONArray(taskMemberArray);
                JSONObject jsonObjectOfTaskMember = new JSONObject();
                jsonObjectOfTaskMember.put("listOfTaskMember", TeamMemberjsonArray);
                return jsonObjectOfTaskMember;

            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /**/
  if (paramter[1].equals("getnotificationInTask")) {
            ArrayList<Notifications> listOfNotifications = null;
            try {
                listOfNotifications = repository.getNotificationsFromDataBase(Integer.parseInt(paramter[2]));
                Gson gson = new GsonBuilder().create();
                String NotificationsArray = gson.toJson(listOfNotifications);
                JSONArray TeamMemberjsonArray = new JSONArray(NotificationsArray);
                JSONObject jsonObjectOfNotifications = new JSONObject();
                jsonObjectOfNotifications.put("listOfNotifications", TeamMemberjsonArray);
                return jsonObjectOfNotifications;

            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    if (paramter[1].equals("getUser")) 
    {
            try {
                User user=repository.getUserData(Integer.parseInt(paramter[2]));
           JSONObject jsonBbjOfUser=user.getUserAsJson();
           return jsonBbjOfUser;

            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
        /*Sara*/
        return null;
    }

    @Override
    public int put(String[] paramter, JSONObject body) {
        /*Ghader*/

        if (paramter[1].equals("setNewName")) {
            try {
                String id = body.getString("id");
                String name = body.getString("username");
                // 0 -> error to excute query 
                // 1-> is updated
                // 2-> name is already found
                int status = repository.updateUserName(id, name);
                return status;
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (paramter[1].equals("setPassword")) {
            try {
                System.out.println(body);
                String id = body.getString("id");
                String password = body.getString("password");
                // 0 -> error to execute query
                // 1 -> is updated 
                int status = repository.updatePassword(id, password);
                return status;
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*Ghader*/
 /*Elesdody*/
        if (paramter[1].equals("list")) {
            try {
                int result = repository.updateList(getTodoObject(body));
                return result;
            } catch (JSONException ex) {

                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                return -1;

            } catch (ParseException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
        /*Elesdody*/
        /*sara*/
        if (paramter[1].equals("task")) {
            try {
                String titleFromJson = (String) body.get("title");
                int listIdFromJson = (int) body.get("listId");
                int id = (int) body.get("id");
                String description =(String) body.get("description");
                String deadline =(String) body.get("deadLine");
                String starttime =(String) body.get("startTime");
                String comment =(String) body.get("comment");
                Items item = new Items(titleFromJson,listIdFromJson);
                item.setDeadLine(deadline);
                item.setId(id);
                item.setDescription(description);
                item.setStartTime(starttime);
                item.setComment(comment);
                int result = repository.updateTask(item);
                return result;
            } catch (JSONException ex) {

                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                return -1;

            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        /*sara*/
        return 0;
    }

    @Override
    public int delete(String[] paramter) {

        /*Elesdody*/
        if (paramter[1].equals("list")) {
            try {
                int result = repository.deleteList(Integer.parseInt(paramter[2]));
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
        /*Elesdody*/

 /*Ashraf*/
 /*Ashraf*/
 /*Aml*/
 /*Aml*/
 /*Ghader*/
 /*Ghader*/
 /*Sara*/
   if (paramter[1].equals("task")) {
            try {
                int result = repository.deleteTask(Integer.parseInt(paramter[2]));
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
    if (paramter[1].equals("teammember")) {
            try {
                int result = repository.deleteTeamMember(Integer.parseInt(paramter[2]));
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
 /*Sara*/
        return -1;
    }

    private ToDoList getTodoObject(JSONObject body) throws JSONException, ParseException {
        ToDoList oDoList = new ToDoList(body.getString("title"), body.getInt("ownerId"), body.getString("startDate"), body.getString("deadLine"), body.getString("color"));
        return oDoList;
    }

    private ArrayList<Notifications> getNotificatinArray(JSONArray notiJSONArray) throws JSONException {
        ArrayList<Notifications> notifications = new ArrayList<>();
        for (int i = 0; i < notiJSONArray.length(); i++) {
            JSONObject json = notiJSONArray.getJSONObject(i);
            notifications.add(new Notifications(json.getInt("fromUserId"), json.getInt("toUserId"), json.getInt("type"), json.getInt("listId")));
        }
        return notifications;
    }

    /*Ashraf*/
    private User getUserFromJson(JSONObject body) {
        User user = new User();
        try {
            String userName = body.getString("USER_NAME");
            String password = body.getString("PASSWORD");
            user.setUserName(userName);
            user.setPassword(password);
        } catch (JSONException ex) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }
    /*Ashraf*/
}
