/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;


import static Enum.NotificationKeys.NORESPONSE_COLLABORATOR_REQUEST;
import static Enum.NotificationKeys.REQUEST_FRIEND;
import Enum.NotificationKeys;
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
import org.json.JSONArray;

import org.json.JSONObject;
import serverDatabase.Repository;

import serverEntity.Items;
import serverEntity.Notifications;
import serverEntity.ToDoList;
import serverEntity.User;
import statisticsManager.DataCenter;

/**
 *
 * @author Elesdody
 */
public class Request implements ClientRequest {

    Repository repository;
    DataCenter dataCenter;

    public Request() {
        repository = new Repository();
        dataCenter = new DataCenter();
    }

    public JSONObject post(String[] paramter, JSONObject body, RequestHandler handler) {

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
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (paramter[1].equals("notification")) {
            try {
                ArrayList<Notifications> notifications = getNotificatinArray(body.getJSONArray("notification_List"));
                // first insert in database
                int resullt = repository.insertTodoNotification(notifications);
                // remove friend from todo
                int result = repository.removeCollab(getFriendsList(body.getJSONArray("removed_friends")), body.getInt("todoId"));
                // notify other friends
                if (resullt > 0) {
                    Client.notifyCollaborator(notifications);

                }
                return resullt != -1 ? new JSONObject("{id:" + resullt + "}") : new JSONObject("{Error:\"Error insert list \"}");

            } catch (JSONException ex) {
                System.out.println(ex.getMessage());
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }

        } /*Elesdody*/ /*Aml*/else if (paramter[1].equals("register")) {
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
        } else if (paramter[1].equals("sendFriendRequest")) {
            try {
                int fromUserID = Integer.parseInt(body.getString("currentUserID"));
                String fromUserName = body.getString("currentUserName");
                String friendName = body.getString("friendName");
                boolean a1 = repository.checkUser(friendName);
                if (a1) {
                         int toUserID = repository.getUserID(friendName);
                        int resultInsertNotification = repository.insertIntoNotificationTables(fromUserID, toUserID);
                        if (resultInsertNotification == 1) {
                            body.put("result", "Friend Request  sent now");
                            Notifications notification = new Notifications(fromUserID,
                                    toUserID, REQUEST_FRIEND, NORESPONSE_COLLABORATOR_REQUEST);
                            Client.notifyUsetWithFriendRequest(notification);
                        } else {
                            body.put("result", "Friend Request is sent before");
                        }
                    
                } else {
                    System.out.println("checkFriendNameInUserTable" + repository.isNameNotFound("abc"));
                    body.put("result", "This name is not in our users ");
                }

            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else if (paramter[1].equals("removeFriend")) {
            try {
                int userID = Integer.parseInt(body.getString("userID"));
                System.out.println("in repository");
              int result =  repository.removeFriend(userID);
              if (result== 1)
                   body.put("result", "success remove friend");
              else
                   body.put("result", "fail remove friend");
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*Aml*/

 /*Aml*/
 /*Aml*/
 /*Ghader*/
        if(paramter[1].equals("collAcceptListRequest")){
                try {
                 System.out.println(body);
                int userId = body.getInt("userId");
                int listId = body.getInt("todoId");
                // 0 -> error to execute query
                // 1 -> is updated 
                int status = repository.addNewCollaboratorToList(userId, listId);
                if (status > 0) {
                   // ClientHandler.notifyCollaborator(notifications);

                }
                return status != -1 ? new JSONObject("{id:" + status + "}") : new JSONObject("{Error:\"Error insert Collaborator \"}");
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
 /*Ghader*/
        if (paramter[1].equals("addNewColl")) {
            try {
                System.out.println("add new coll" + body);
                int userId = body.getInt("userId");
                int listId = body.getInt("todoId");
                int status = repository.addNewCollaboratorToList(userId, listId);
                if (status > 0) {
                  ArrayList<User> allColl  = null;
                  ArrayList<Items> allItems = null;
                  ToDoList sharedlist = null;
                   allColl = repository.selectAllCollaboratorsInList(listId);
                   allItems = repository.selectAllItemsInList(listId);
                 if(allItems != null || allColl != null){  
                   sharedlist = repository.selectSharedList(listId, allItems, allColl);
                   Client.addCollabortor(userId, listId , allColl);
                   Client.addSharedListToNewCollabortor(userId, sharedlist); 
                 }
                }
                return status != -1 ? new JSONObject("{id:" + status + "}") : new JSONObject("{Error:\"Error  \"}");
            } catch (JSONException ex) {
                System.err.println("Request  cannot add new coll");
            }

        }
        if (paramter[1].equals("addNewTaskMember")) {
            try {
                System.out.println("add new task" + body);
                int userId = body.getInt("userId");
                int itemId = body.getInt("ItemId");
                int status = repository.addNewTeamMember(userId, itemId);
                if (status > 0) {
                    // ClientHandler.notifyCollaborator(notifications);

                }
                return status != -1 ? new JSONObject("{id:" + status + "}") : new JSONObject("{Error:\"Error \"}");
            } catch (JSONException ex) {
                System.err.println("Request 169 : cannot add new task member");
            }

        }
        if (paramter[1].equals("addNewFriend")) {
            try {
                System.out.println("add new friend" + body);
                int userId = body.getInt("userId");
                int friendId = body.getInt("friendId");
                int status = repository.addNewFriend(userId, friendId);             
                if (status > 0) {
                    //TODO : add friend real time
                    //Client.
                }
                return status != -1 ? new JSONObject("{result:" + status + "}") : new JSONObject("{Error:\"Error\"}");
            } catch (JSONException ex) {
                System.err.println("Request 185 : cannot add new coll");
            }

        }
        if (paramter[1].equals("sender:list:accept")) {
            int notId = -1;
            try {
                System.out.println("add new not accept to sender/list" + body);
                int fromUserId = body.getInt("fromUserId");
                int toUserId = body.getInt("toUserId");
                int dataId = body.getInt("dataId");
                String fromUserName = body.getString("fromUserName");
                String toUserName = body.getString("toUserName");
                String data = body.getString("data");
                int status = repository.addNewNotToSenderRequest(fromUserId, toUserId, NotificationKeys.ADD_COLLABORATOR, NotificationKeys.SEND_RESPONSE_BACK_TO_SENDER_ACCEPT, dataId);
                if (status > 0) {
                    notId = repository.sendGeneratedId();
                    System.out.println("status: "+status +"notid: "+ notId);
                    Notifications not = new Notifications(notId, fromUserId, toUserId, NotificationKeys.ADD_COLLABORATOR, NotificationKeys.SEND_RESPONSE_BACK_TO_SENDER_ACCEPT, dataId);
                    not.setDataName(data);
                    not.setfromUserName(toUserName);
                    Client.notify(not);
                }
                return notId != -1 ? new JSONObject("{status:" + status + "}") : new JSONObject("{Error:\"Error  \"}");
            } catch (JSONException ex) {
                System.err.println("Request 205 : cannot send not to sender /list");
            }

        }
        if (paramter[1].equals("sender:task:accept")) {
            int notId = -1;
            try {
                System.out.println("add new not accept to sender/task " + body);
                int fromUserId = body.getInt("fromUserId");
                int toUserId = body.getInt("toUserId");
                int dataId = body.getInt("dataId");
                String fromUserName = body.getString("fromUserName");
                String toUserName = body.getString("toUserName");
                String data = body.getString("data");
                int status = repository.addNewNotToSenderRequest(fromUserId, toUserId, NotificationKeys.ASSIGIN_TASK_MEMBER, NotificationKeys.SEND_RESPONSE_BACK_TO_SENDER_ACCEPT, dataId);
                if (status > 0) {
                    notId = repository.sendGeneratedId();
                    Notifications not = new Notifications(notId, fromUserId, toUserId, NotificationKeys.ASSIGIN_TASK_MEMBER, NotificationKeys.SEND_RESPONSE_BACK_TO_SENDER_ACCEPT, dataId);
                    not.setDataName(data);
                    not.setfromUserName(toUserName);
                    Client.notify(not);
                }
                return notId != -1 ? new JSONObject("{status:" + status  + "}") : new JSONObject("{Error:\"Error  \"}");
            } catch (JSONException ex) {
                System.err.println("Request 241 : : cannot send not to sender /task");
            }

        }
        if (paramter[1].equals("sender:friend:accept")) {
            int notId = -1;
            try {
                System.out.println("add new not accept to sender/friend" + body);
                int fromUserId = body.getInt("fromUserId");
                int toUserId = body.getInt("toUserId");
                int dataId = body.getInt("dataId");
                String fromUserName = body.getString("fromUserName");
                String toUserName = body.getString("toUserName");
                int status = repository.addNewNotToSenderRequest(fromUserId, toUserId, NotificationKeys.REQUEST_FRIEND, NotificationKeys.SEND_RESPONSE_BACK_TO_SENDER_ACCEPT, dataId);
                if (status > 0) {
                    notId = repository.sendGeneratedId();
                    Notifications not = new Notifications(notId, fromUserId, toUserId, NotificationKeys.REQUEST_FRIEND, NotificationKeys.SEND_RESPONSE_BACK_TO_SENDER_ACCEPT, dataId);
                    not.setfromUserName(toUserName);
                    Client.notify(not);
                }
                return notId != -1 ? new JSONObject("{status:" + status + "}") : new JSONObject("{Error:\"Error  \"}");
            } catch (JSONException ex) {
                System.err.println("Request 263 : : cannot send not to sender /friend");
            }

        }
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
        if (paramter[1].equals("Assignnotification")) {
            /*   int fromUserId = 0 ;
<<<<<<< HEAD
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
=======
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
>>>>>>> ghadeer/master
                
             try {
             repository.insertNotificationToDataBase(notificationData);
             } catch (SQLException ex) {
             Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            JSONArray notificationArray = null;
            try {
                notificationArray = body.getJSONArray("listOfNotifications");
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<Notifications> notificationList = new ArrayList<Notifications>();
            for (int i = 0; i < notificationArray.length(); i++) {
                JSONObject notification = null;
                try {
                    notification = notificationArray.getJSONObject(i);
                } catch (JSONException ex) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                }
                Notifications notificationData = new Notifications();
                try {
                    notificationData.setFromUserId(notification.getInt("fromUserId"));
                    notificationData.setToUserId(notification.getInt("toUserId"));
                    notificationData.setType(notification.getInt("type"));
                    notificationData.setDataId(notification.getInt("dataId"));
                    notificationData.setStatus(notification.getInt("status"));
                    notificationList.add(notificationData);
                } catch (JSONException ex) {
                    Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            try {
                repository.insertNotificationToDataBase(notificationList);
            } catch (SQLException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            }

        } /*Sara*/ /*Ashraf*/ else if (paramter[1].equals(REQUEST.LOGIN)) {
            try {
                
                User user = getUserFromJson(body);
                JSONObject respond = repository.getUser(user);
                // get last one been add to victor
                if (respond != null && respond.getInt("Code") == RESPOND_CODE.SUCCESS) {
                    //add user to server clients
                    int userId = respond.getInt("ID");
                    String userName = respond.getString("User_name");
                    if(Client.isInVector(userId)){
                        respond.put("Code", RESPOND_CODE.IS_LOGIN);
                        return respond;
                    }
                    //dataCenter.updateOnlineUsers(Client.getclientVector().size());

                    Client client = new Client(userId, userName, handler);
                    ArrayList<User> friends = repository.getUserFriends(userId);
                    Client.notifiyFriends(user, friends, REQUEST.FRIEND_ONLINE);
                    Client.addClient(client);
                    //dataCenter.updateOnlineUsers(Client.getclientVector().size());

                } else {
                    System.out.println("login respond faild, not added to portListener any client");
                    // remove from vector
                }

                System.out.println(Client.getclientVector().size());
                for (Client clientt : Client.getclientVector()) {
                    System.out.println(clientt.getClientName());
                }

                return respond;
            } catch (JSONException ex) {
                Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
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
                // get online friends
                ArrayList<User> onlineFriends = Client.getOnlineUser(friends);
                // get notificaiton
                ArrayList<Notifications> notificationses = repository.getUserNotification(Integer.parseInt(paramter[2]));

                Gson gson = new GsonBuilder().create();
                // convert friendsList to json
                String friendsArray = gson.toJson(friends);

                JSONArray friendsjsonArray = new JSONArray(friendsArray);
                // convert onlineFrined list to json
                String onlineList = gson.toJson(onlineFriends);
                JSONArray onlineJsonArray = new JSONArray(onlineList);
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
                userJosn.put("online_friends",onlineJsonArray);
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
        if (paramter[1].equals("getUser")) {
            try {
                User user = repository.getUserData(Integer.parseInt(paramter[2]));
                JSONObject jsonBbjOfUser = user.getUserAsJson();
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


        if (paramter[1].equals("updateRequestStatus")) {
            try {
                System.out.println(body);

                int id = body.getInt("notId");
                int reqStatus = body.getInt("status");
                // 0 -> error to execute query
                // 1 -> is updated 
                int status = repository.updateNotificationStatus(id, reqStatus);
                System.out.println("updating not status list: "+ status);
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
                String description = (String) body.get("description");
                String deadline = (String) body.get("deadLine");
                String starttime = (String) body.get("startTime");
                String comment = (String) body.get("comment");
                Items item = new Items(titleFromJson, listIdFromJson);
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
        if (paramter[1].equals("collab")) {

            try {
                User user = new User();
                int todoId = Integer.parseInt(paramter[2]);
                user.setId(Integer.parseInt(paramter[3]));
                ArrayList users = new ArrayList();
                users.add(user);
                int result = repository.removeCollab(users, todoId);
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
        if (body.has("id")) {
            oDoList.setId(body.getInt("id"));
        }
        return oDoList;
    }

    private ArrayList<Notifications> getNotificatinArray(JSONArray notiJSONArray) throws JSONException {
        ArrayList<Notifications> notifications = new ArrayList<>();
        for (int i = 0; i < notiJSONArray.length(); i++) {
            JSONObject json = notiJSONArray.getJSONObject(i);
            Notifications notification = new Notifications(json.getInt("fromUserId"), json.getString("fromUserName"), json.getInt("toUserId"), json.getInt("type"), json.getInt("status"), json.getInt("listId"));
            notification.setDataName(json.getString("listTitle"));
            notifications.add(notification);
        }
        return notifications;
    }

    private ArrayList<User> getFriendsList(JSONArray friendsJson) throws JSONException {
        ArrayList<User> friends = new ArrayList<>();
        for (int i = 0; i < friendsJson.length(); i++) {
            JSONObject json = friendsJson.getJSONObject(i);
            friends.add(new User(json.getInt("ID"), json.getString("USER_NAME")));
        }
        return friends;
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

 /*Aml*/
 /*Aml*/
}
