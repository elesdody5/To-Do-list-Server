/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.implementation;

import DAO.UserDao;
import serverDatabase.Repository;
import serverEntity.Entity;
import serverEntity.User;

/**
 *
 * @author Ehab mohamed
 */
public class UserOperation implements UserDao{
    Repository repo;
    
    
    public UserOperation(){
        repo = new Repository();
    }
    @Override
    public void insert(User user) {
        repo.insertUser(user);
    }

    @Override
    public void delete(User user) {
        
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void select(User user) {
        repo.getUser(user);
    }
    
    
}
