/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwish;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ahmed jdbc:mysql://127.0.0.1:3306/?user=root
 */
public class database {

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/iWish", "root", "root");

            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
