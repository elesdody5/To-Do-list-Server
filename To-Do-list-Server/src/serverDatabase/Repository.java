/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import serverEntity.User;
import Enum.RESPOND_CODE;
import java.sql.Statement;
import java.util.ArrayList;

import serverEntity.Items;
import serverEntity.Notifications;
import serverEntity.ToDoList;

/**
 *
 * @author Elesdody
 */
public class Repository {

    private Connection db;

    public Repository() {
        try {
            db = DataBase.getDatabase();

        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // TODO write query methods (select ,update ,insert ,delete )

    /*Aml*/
    public int insertUser(String userName, String password) {
        int x = -10;
        try {
            String insertString = "INSERT INTO USER_TABLE  (USER_NAME, PASSWORD)  VALUES  (" + "'" + userName + "' , " + "'" + password + "'" + ")";
            //String insertString = "INSERT INTO CONTACTS  (NAME, PHONENUMBER)  VALUES  (" + "'" + "xyzz" + "' , " + "'" + "8888888888888" + "'" + ")";

//        Statement statment = db.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //    connection.prepareStatement(queryString);
            //       x = statment.executeUpdate(queryString);
            PreparedStatement pst = db.prepareStatement(insertString);
            x = pst.executeUpdate();

            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return x;

        // TODO write query methods (select ,update ,insert ,delete )
    }

    /*Aml*/

 /*Elesdody*/
    public int insertList(ToDoList list) throws SQLException {
        PreparedStatement pre = db.prepareStatement("insert into TODO_LIST (title,ownerId,startDate,deadLine,color,Descreption)VALUES (?,?,?,?,?,?)");
        pre.setString(1, list.getTitle());
        pre.setInt(2, list.getOwnerId());
        pre.setString(3, list.getStartTime());
        pre.setString(4, list.getDeadLine());
        pre.setString(5, list.getColor());
        pre.setString(6, list.getDescription());
        int result = pre.executeUpdate();
        pre.close();
        System.out.println(result);
        if (result != 0) {
            return getListWithTitle(list.getTitle());
        } else {
            return -1;
        }

    }

    public int getListWithTitle(String title) throws SQLException {
        PreparedStatement pre = db.prepareStatement("Select id from TODO_LIST where title = ?");
        pre.setString(1, title);
        int id;
        try (ResultSet set = pre.executeQuery()) {
            set.next();
            id = set.getInt(1);
        }
        pre.close();
        return id;
    }

    // return todo information and taskes in it belongs to user with id
    public ArrayList<ToDoList> getUserToDo(int id) throws SQLException {
        PreparedStatement pre = db.prepareStatement("Select * from TODO_LIST where ownerId = ?");
        pre.setInt(1, id);
        ResultSet set = pre.executeQuery();
        ArrayList<ToDoList> todoList = new ArrayList<>();
        while (set.next()) {
            ToDoList todo = new ToDoList(set.getString("title"), set.getInt("ownerId"), set.getString("startDate"), set.getString("deadLine"), set.getString("color"));
            int toDoId = set.getInt("ID");
            todo.setId(toDoId);
            PreparedStatement sencond_pre = db.prepareStatement("Select * from Item where TodoId = ?");
            sencond_pre.setInt(1, toDoId);
            ResultSet item_set = sencond_pre.executeQuery();
            ArrayList<Items> itemList = new ArrayList<>();
            while (item_set.next()) {
                int taskId = item_set.getInt("ID");
                Items item = new Items(taskId, toDoId, item_set.getString("title"), item_set.getString("startDate"), item_set.getString("deadLine"));
                item.setComment(item_set.getString("comment") != null ? item_set.getString("comment") : "");
                item.setDescription(item_set.getString("descreption") != null ? item_set.getString("descreption") : "");
                itemList.add(item);

            }
            item_set.close();
            todo.setTaskes(itemList);
            todoList.add(todo);

        }

        set.close();
        return todoList;

    }

    public User getUserData(int id) throws SQLException {
        PreparedStatement pre = db.prepareStatement("Select * from User_table where id = ?");
        pre.setInt(1, id);
        User user;
        try (ResultSet set = pre.executeQuery()) {
            set.next();
            user = new User(id, set.getString("user_name"), set.getString("password"));
        }
        pre.close();

        return user;

    }

    public int updateList(ToDoList list) throws SQLException {
        PreparedStatement pre = db.prepareStatement("Update TODO_List set Title=?,OwnerId=?,StartDate= ?,deadLine=?,Color=?,Descreption=? where id = ?");
        pre.setString(1, list.getTitle());
        pre.setInt(2, list.getOwnerId());
        pre.setString(3, list.getStartTime());
        pre.setString(4, list.getDeadLine());
        pre.setString(5, list.getColor());
        pre.setString(6, list.getDescription());
        pre.setInt(7, list.getId());
        int result = pre.executeUpdate();
        System.out.println(result);;
        pre.close();
        if (result != 0) {

            return getListWithTitle(list.getTitle());
        } else {
            return -1;
        }
    }

    public int deleteList(int id) throws SQLException {
        PreparedStatement pre = db.prepareStatement("Delete from TODO_List where id=?");
        pre.setInt(1, id);
        int result = pre.executeUpdate();
        pre.close();
        return result;
    }

    public ArrayList<User> getUserFriends(int id) throws SQLException {
        PreparedStatement pre = db.prepareStatement("select * from User_table where ID IN ( SELECT friendId FROM friends where userId = ?)");
        pre.setInt(1, id);
        ArrayList<User> friends = new ArrayList<>();
        ResultSet friends_set = pre.executeQuery();
        while (friends_set.next()) {
            friends.add(new User(friends_set.getInt("ID"), friends_set.getString("User_name")));
        }
        friends_set.close();
        return friends;

    }

    public int insertTodoNotification(ArrayList<Notifications> notifications) throws SQLException {
        int x = 0;
        for (Notifications notification : notifications) {
            try (PreparedStatement pre = db.prepareStatement("Insert into notification (fromUserId,toUserId,Type,DataId) Values(?,?,?,?) ")) {
                pre.setInt(1, notification.getFromUserId());
                pre.setInt(2, notification.getToUserId());
                pre.setInt(3, notification.getType());
                pre.setInt(4, notification.getDataId());
                x = pre.executeUpdate();
                
            }
        }
        return x;

    }

    /*Elesdody*/
 /*Ghader*/
 /*Ghader*/
 /*Ashraf*/
    
    //get number of users
    public int getNumberOfUsers(){
        int numberOfUsers = 0;
        
        Statement st = null;
        String query = "select count(id) as users  from user_Table ;";
        
        try{
            st = db.createStatement();
            ResultSet set = st.executeQuery(query);
            numberOfUsers = set.getInt("users");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return numberOfUsers;
    }
    public void insertUser(User user) {
        if (user == null) {
            return;
        }
        try {
            PreparedStatement statement = db.prepareStatement("INSERT INTO UserTable (USER_NAME , PASSWORD) VALUES(?,?)");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());

        } catch (SQLException ex) {
            System.out.println("insert exception");
            System.out.println(ex.getCause());
        }
    }
    //get user with user object
    public JSONObject getUser(User user) {
        JSONObject respondJson = new JSONObject();
        if (user == null) {
            try {
                respondJson.put("Code", RESPOND_CODE.FAILD);
                return respondJson;
            } catch (JSONException ex) {
                System.out.println("get user exception");
                Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try {

                String userName = user.getUserName();
                String password = user.getPassword();

                PreparedStatement statement = db.prepareStatement("SELECT * FROM User_table WHERE User_name =? AND Password =?");
                statement.setString(1, userName);
                statement.setString(2, password);
                ResultSet result = statement.executeQuery();
                boolean hasRow = result.next();
                if (hasRow) {
                    int userId = result.getInt("ID");
                    String resultUserName = result.getString("User_name");
                    String resultPassword = result.getString("Password");
                    respondJson.put("Code", RESPOND_CODE.SUCCESS);
                    respondJson.put("ID", userId);
                    respondJson.put("User_name", resultUserName);
                    respondJson.put("password", resultPassword);

                } else {
                    respondJson.put("Code", RESPOND_CODE.FAILD);

                    System.out.println("no row with this email or passwords");
                }


            } catch (SQLException | JSONException ex) {
                System.out.println("Repository class , getUser method exception");
            } 
        }
        return respondJson;
    }

    public JSONObject getUser(String[] userDataParam, JSONObject body) {

        JSONObject respondJson = new JSONObject();
        if (userDataParam == null && body == null) {
            try {
                respondJson.put("Code", RESPOND_CODE.FAILD);
                return respondJson;
            } catch (JSONException ex) {
                System.out.println("get user exception");

                Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            try {

                String userName = body.getString("USER_NAME");
                String password = body.getString("PASSWORD");

                PreparedStatement statement = db.prepareStatement("SELECT * FROM User_table WHERE User_name =? AND Password =?");
                statement.setString(1, userName);
                statement.setString(2, password);
                ResultSet result = statement.executeQuery();
                boolean hasRow = result.next();
                if (hasRow) {
                    int userId = result.getInt("ID");
                    String resultUserName = result.getString("User_name");
                    String resultPassword = result.getString("Password");
                    respondJson.put("Code", RESPOND_CODE.SUCCESS);
                    respondJson.put("ID", userId);
                    respondJson.put("User_name", resultUserName);
                    respondJson.put("password", resultPassword);

                } else {
                    respondJson.put("Code", RESPOND_CODE.FAILD);

                    System.out.println("no row with this email or passwords");
                }

                result.close();
            } catch (SQLException | JSONException ex) {
                System.out.println("Repository class , getUser method exception");
            } 
        }

        return respondJson;
    }

    /*Ashraf*/
 /*Ghader*/
    public int updateUserName(String id, String name) {
        if (isNameNotFound(name) == 1) {
            String sql = "Update User_table set User_name = ? where ID= ?";
            PreparedStatement stmt;
            try {
                int ident = Integer.parseInt(id);
                stmt = db.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setInt(2, ident);
                stmt.executeUpdate();
                stmt.close();
                return 1;
            } catch (SQLException ex) {

                Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (isNameNotFound(name) == 0) {
            return 2;
        }

        return 0;
    }

    private int isNameNotFound(String name) {
        String sql = " select User_name from User_table where User_name = ?";
        PreparedStatement stmt;
        try {
            ResultSet rs;
            stmt = db.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            //name is found so cannot change the name
            if (rs.next()) {
                rs.close();
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 1;
    }

    public int updatePassword(String id, String password) {

        String sql = "Update User_table set password = ? where ID= ?";
        PreparedStatement stmt;
        try {
            int ident = Integer.parseInt(id);
            stmt = db.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setInt(2, ident);
            stmt.executeUpdate();
            stmt.close();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    /*Ghader*/
 /*Sara*/
 /*Sara*/
    public void insertItemToDataBase(Items item) throws SQLException {

        String sql = "INSERT INTO Item(title,TodoId,Descreption,DeadLine,StartDate,Comment) VALUES(? , ?,?,?,?,?)";

        PreparedStatement pstmt = db.prepareStatement(sql);
        pstmt.setString(1, item.getTitle());

        pstmt.setInt(2, item.getListId());
        pstmt.setString(3, item.getDescription());
        pstmt.setString(4, item.getDeadLine());
        pstmt.setString(5, item.getStartTime());
        pstmt.setString(6, item.getComment());

        pstmt.executeUpdate();

    }

    public ArrayList<Items> getTaskFromDataBase() throws SQLException {
        String sqlstatment = "select * from Item where TodoId=1";
        PreparedStatement pstmt = db.prepareStatement(sqlstatment);
        ResultSet resultSet = pstmt.executeQuery();
        ArrayList<Items> todoListItems = new ArrayList<>();
        while (resultSet.next()) {
            //  int taskId = resultSet.getInt("ID");
            Items item = new Items(resultSet.getString("title"), resultSet.getInt("toDoId"));
            todoListItems.add(item);

        }
        resultSet.close();

        return todoListItems;
    }

    public ArrayList<User> getTeamMemberFromDataBase() throws SQLException {
        String sqlstatment = "SELECT * FROM  User_table ,Collab where ID=UserId ";
        PreparedStatement pstmt = db.prepareStatement(sqlstatment);
        ResultSet resultSet = pstmt.executeQuery();
        ArrayList<User> teamMemberList = new ArrayList<>();
        while (resultSet.next()) {
            //  int taskId = resultSet.getInt("ID");
            User teamMember = new User();
            teamMember.setUserName(resultSet.getString("User_name"));
            teamMemberList.add(teamMember);

        }
        resultSet.close();

        return teamMemberList;
    }
    /*Sara*/

}
