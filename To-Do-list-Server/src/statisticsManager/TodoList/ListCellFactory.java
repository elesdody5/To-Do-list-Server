/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statisticsManager.TodoList;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import serverEntity.ToDoList;

/**
 *
 * @author Ashraf mohamed
 */
public class ListCellFactory implements Callback<ListView<ToDoList>, ListCell<ToDoList>> {

    @Override
    public ListCell<ToDoList> call(ListView<ToDoList> param) {
        return new TodoListCell();
    }
    
}
