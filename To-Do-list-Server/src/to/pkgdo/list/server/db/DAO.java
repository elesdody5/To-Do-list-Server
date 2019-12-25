/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to.pkgdo.list.server.db;

import java.util.List;

/**
 *
 * @author Elesdody
 * @param <T>
 */
public interface DAO <T>{
    // return all data in table
    List<T> getAllData();
    // return object with given id 
    T getItem(int id);
    // return id of  inserted object 
    int insert(T object);
    // return id of  deleted object
    int delete(T object);
    
}
