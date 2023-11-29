/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmed
 */
public class DataAccessLayer {

    Connection connect;
    PreparedStatement prepare;
    ResultSet result;

    public boolean login(String username, String password) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/iWish", "root", "root");
            String sql = "SELECT * FROM user where username = ? and password = ? ;";
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, username);
                prepare.setString(2, password);
                result = prepare.executeQuery();
                if (result.next()) {
                    // correct username and password
                    //System.out.println("Login Success");
                    return true;
                } else {
                    //incorrect username or password
                    //System.out.println("Login Failed");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
}
