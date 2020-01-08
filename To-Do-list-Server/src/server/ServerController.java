/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author Ashraf Mohamed
 */
public class ServerController implements Initializable {

    @FXML
    private BorderPane borderPane_id;
    @FXML
    private Circle close_id;
    @FXML
    private Circle restor_down_id;
    @FXML
    private Circle min_id;
    @FXML
    private StackPane stack_pane_id;
    @FXML
    private Pane sign_in_pane_id;
    @FXML
    private ListView<?> list_view_id;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    
    }    

    @FXML
    private void handleMouseClicked(MouseEvent event) {
    }
    
}
