/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import serverEntity.ToDoList;
import serverEntity.User;
import statisticsManager.DataCenter;

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
    private ListView<ToDoList> todoList_id;
    @FXML
    private Pane userPane_id;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userList_id = new ListView<>();
        todoList_id = new ListView<>();
        
        dataCenter = new DataCenter();
        isStart = false;
        stop_id.setDisable(true);
        
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
        if(event.getSource().equals(start_id)){
            if(!start_id.isDisable()){ // start  disable , server not working
                PortListener.startServer(); // make server work
                start_id.setDisable(true); // disable start button
                stop_id.setDisable(false); // enable stop button
            }
        }else if(event.getSource().equals(stop_id)){ 
            if(!stop_id.isDisable()){ //stop not disable , server is working
                PortListener.closeServer(); // shut down server listener
                stop_id.setDisable(true); // make stop button enabled
                start_id.setDisable(false); // make start button disabled
            }
        }
    }

}
