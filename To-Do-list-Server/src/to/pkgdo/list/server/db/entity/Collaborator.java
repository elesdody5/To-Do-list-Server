/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.server.db.entity;

import java.util.List;
import to.pkgdo.list.server.db.DAO;

/**
 *
 * @author Elesdody
 */
public class Collaborator implements DAO<Collaborator>{
    private int userId;
    private int toDoId;

    public Collaborator(int userId, int toDoId) {
        this.userId = userId;
        this.toDoId = toDoId;
    }

    public int getUserId() {
        return userId;
    }

    public int getToDoId() {
        return toDoId;
    }

    @Override
    public List<Collaborator> getAllData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collaborator getItem(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(Collaborator object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Collaborator object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
