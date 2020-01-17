/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import connection.Client;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import serverEntity.ToDoList;
import serverEntity.User;
import statisticsManager.DataCenter;
import statisticsManager.Entity.TodoListData;
import statisticsManager.Entity.UserData;
import statisticsManager.TodoList.ListCellFactory;
import statisticsManager.UserList.UserCellFactory;

/**
 *
 * @author Ashraf Mohamed
 */
public class ServerController2 implements Initializable {

    @FXML
    private BorderPane borderPane_id;
    @FXML
    private Circle close_id;
    @FXML
    private Circle restor_down_id;
    @FXML
    private Circle min_id;

    private DataCenter dataCenter;
    private Stage stage;
    private boolean isFull;
    private boolean isStart;
    private double xOffset, yOffset;

    @FXML
    private Button start_id;
    @FXML
    private Button stop_id;
    @FXML
    private ListView<User> userList_id;

    @FXML
    private Label numberOfUsers_id;
    @FXML
    private Label onlineUsers_id;
    @FXML
    private Label numberOfList_id;

    private int users;
    @FXML
    private ListView<ToDoList> todoList_id;
    @FXML
    private BarChart bar_chart_id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initializing method");

        dataCenter = new DataCenter();
        bar_chart_id.autosize();
        isStart = false;
        stop_id.setDisable(true);

        setGeneralData();
        setUserList();
        setToDoList();

        borderPane_id.setOnMousePressed((MouseEvent event) -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        borderPane_id.setOnMouseDragged((MouseEvent event) -> {
            if (!stage.isFullScreen()) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }

        });

    }

    @FXML
    private void handleMouseClicked(MouseEvent event) {
        if (event.getSource().equals(close_id)) {
            closeLoginWindow();
        }

        if (event.getSource().equals(restor_down_id)) {
            System.out.println("restor down ");
            maxmizeLoginWindow();
        }
        if (event.getSource().equals(min_id)) {
            minimzeLoginWindow();

        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void closeLoginWindow() {
        System.exit(0);
    }

    private void minimzeLoginWindow() {
        stage.setIconified(true);
    }

    private void maxmizeLoginWindow() {
        isFull = stage.isFullScreen();
        if (isFull) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }

    }

    @FXML
    private void handleServerOperation(ActionEvent event) {
        if (event.getSource().equals(start_id)) {
            if (!start_id.isDisable()) { // start  disable , server not working
                PortListener.startServer(); // make server work
                start_id.setDisable(true); // disable start button
                stop_id.setDisable(false); // enable stop button
            }
        } else if (event.getSource().equals(stop_id)) {
            if (!stop_id.isDisable()) { //stop not disable , server is working
                PortListener.closeServer(); // shut down server listener
                stop_id.setDisable(true); // make stop button enabled
                start_id.setDisable(false); // make start button disabled
            }
        }
    }

    private void setUserList() {
        try {
            ArrayList<User> list = dataCenter.getListOfUsers();
            users = (list != null) ? list.size() : 0;

            ObservableList<User> users = FXCollections.observableArrayList(list);
            userList_id.setItems(users);
            userList_id.setCellFactory(new UserCellFactory());
            userList_id.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            userList_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
                @Override
                public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                    try {
                        UserData userData = dataCenter.getUserData(newValue.getId());
                        setUserBarChart(userData);
                    } catch (SQLException ex) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setContentText(" No Users in DataBase");
                        alert.show();
                    }
                }

            });
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText(" No Users in DataBase");
            alert.show();
        }
    }

    private void setToDoList() {
        try {
            todoList_id.refresh();
            ArrayList<ToDoList> list = dataCenter.getToDoList();
            System.out.println("lists:" + list.size());
            ObservableList<ToDoList> observableToDoList = FXCollections.observableArrayList(list);
            todoList_id.setCellFactory(new ListCellFactory());
            todoList_id.setItems(observableToDoList);
            todoList_id.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            todoList_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoList>() {
                @Override
                public void changed(ObservableValue<? extends ToDoList> observable, ToDoList oldValue, ToDoList newValue) {
                    try {
                        setToDoListBarChart(dataCenter.getTodoListData(newValue.getId()));
                    } catch (SQLException ex) {
                        TodoListData defaultData = new TodoListData();
                        defaultData.setTitle("title");
                        defaultData.setOwnerName("Owner");
                        defaultData.setNumberOfCollaborator(0);
                        defaultData.setNumberOfItems(0);
                        setToDoListBarChart(defaultData);
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Data Base Problem has occured, Please try again");
                        alert.show();
                    }
                }

            });
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Data Base Problem has occured, Please restart and try again");
            alert.show();
        }
    }

    public void setNumberOfOnlineUsers(int numberOfOnlineUsers) {
        onlineUsers_id.setText(String.valueOf(numberOfOnlineUsers));
    }

    private void setGeneralData() {
        try {

            int numberOfLists = dataCenter.getNumberOfLists();
            int numberOfUsers = dataCenter.getNumberOfUsers();
            int numberOfOnlineUsers = Client.getclientVector().size();

            numberOfList_id.setText(String.valueOf(numberOfLists));
            numberOfUsers_id.setText(String.valueOf(numberOfUsers));
            onlineUsers_id.setText(String.valueOf(numberOfOnlineUsers));

        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("no List Exist in dataBase");
            alert.show();
        }

    }

    /*public void setOnlineUsersNumber(int numberOfOnlineUsers){
       onlineUsers_id.setText(String.valueOf(numberOfOnlineUsers));
   }*/
    //set user bar Chart 
    private void setUserBarChart(UserData data) {
       
        bar_chart_id.getData().clear();
        bar_chart_id.getXAxis().setLabel("User Data");
        bar_chart_id.getYAxis().setLabel("Number");

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("User Statistics");
        dataSeries.getData().add(new XYChart.Data("Friends", data.getNumberOfFriends()));
        dataSeries.getData().add(new XYChart.Data("Lists", data.getNumberOfLists()));
        dataSeries.getData().add(new XYChart.Data("Tasks", data.getNumberOfItemAssign()));
        bar_chart_id.getData().add(dataSeries);
    }

    //set todolist bar Chart 
    private void setToDoListBarChart(TodoListData data) {
        bar_chart_id.getData().clear();
        bar_chart_id.getXAxis().setLabel("ToDo List Data");
        bar_chart_id.getYAxis().setLabel("Number");

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("title: " + data.getTitle() + "   Owner: " + data.getOwnerName());
        dataSeries.getData().add(new XYChart.Data("Items", data.getNumberOfItems()));
        dataSeries.getData().add(new XYChart.Data("Collaborators", data.getNumberOfCollaborator()));
        bar_chart_id.getData().add(dataSeries);
    }
}
