/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticsManager.TodoList;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import serverEntity.ToDoList;

public class TodoListCell extends ListCell<ToDoList> {

    @FXML
    private Label listTitle_id;
    @FXML
    private Label listId_id;

    @FXML
    private AnchorPane anchor_id;

    private FXMLLoader loader;

    public TodoListCell() {
        if (loader == null) {
            loader = new FXMLLoader(getClass().getResource("/statisticsManager/TodoList/listItemDesign.fxml"));
            loader.setController(this);
        }

        try {
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void updateItem(ToDoList item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            listTitle_id.setText(item.getTitle());
            listId_id.setText(String.valueOf(item.getId()));

            setText(null);
            setGraphic(anchor_id);

        }

    }
}
