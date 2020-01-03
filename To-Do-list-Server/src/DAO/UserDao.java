/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import serverEntity.Entity;
import serverEntity.User;

/**
 *
 * @author Ehab mohamed
 */
public interface UserDao extends BaseDao{
    
    public void insert(User user);
    public void delete(User user);
    public void update(User user);
    public void select(User user);
    
    
    
}
