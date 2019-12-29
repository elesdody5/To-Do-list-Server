/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import org.json.JSONObject;

/**
 *
 * @author Elesdody
 */
public interface HttpRequest {

    public JSONObject post(String[] paramter, JSONObject body);

    public JSONObject get(String[] paramter);

    public int put(String[] paramter, JSONObject body);

    public int delete(String[] paramter);

}
