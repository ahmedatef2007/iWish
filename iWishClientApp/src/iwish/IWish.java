/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwish;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author ahmed
 */
public class IWish extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new LoginController();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/iWish", "root", "root");
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("Select * from user");
            while (rs.next()) {
                System.out.println(rs.getString("username") + "-" + rs.getString("password"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(IWish.class.getName()).log(Level.SEVERE, null, ex);
        }

         */
        launch(args);
    }

}
