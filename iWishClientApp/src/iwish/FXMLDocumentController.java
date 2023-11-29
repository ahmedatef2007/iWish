/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwish;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author ahmed
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private BorderPane login_form;

    @FXML
    private Button si_createAccountBtn;

    @FXML
    private TextField si_username;

    @FXML
    private PasswordField si_password;

    @FXML
    private Button si_loginBtn;

    @FXML
    private BorderPane sifnup_form;

    @FXML
    private Button su_loginAccountBtn;

    @FXML
    private TextField su_username;

    @FXML
    private PasswordField su_password;

    @FXML
    private Button signup_btn;
    // db  connection 
/*
        private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void loginAccount() {
        String sql = "SELECT * FROM user where username = ? and password = ? ;";
        connect = database.connect();

        try {
            Alert alert;
            if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill your User name and Password");
                alert.showAndWait();
            } else {

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    // correct username and password
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Success !");
                    alert.showAndWait();
                } else {
                    //incorrect username or password
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect User name or Password");
                    alert.showAndWait();
                }

            }

        } catch (Exception e) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
