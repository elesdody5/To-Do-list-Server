/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticsManager.UserList;



import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import serverEntity.User;


public class UserCell extends ListCell<User> {

    @FXML
    private Label userName_id;
    @FXML
    private Label userId_id;

    @FXML
    private AnchorPane anchor_id;

    private FXMLLoader loader;

    public UserCell() {
        if (loader == null) {
            
            loader = new FXMLLoader(getClass().getResource("/statisticsManager/userItemFXML.fxml"));
            loader.setController(this);
        }
        
        try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(UserCell.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            userName_id.setText(item.getUserName());
            userId_id.setText(String.valueOf(item.getId()));

            setText(null);
            setGraphic(anchor_id);
        }
    }

}

