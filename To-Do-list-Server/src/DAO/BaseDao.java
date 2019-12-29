/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import to.pkgdo.list.server.db.entity.Entity;

/**
 *
 * @author Ehab mohamed
 */
public interface BaseDao {
    
    public void insert(Entity entity);
    
    public void delete(Entity entity);
    
    public void select (Entity entity);
}
