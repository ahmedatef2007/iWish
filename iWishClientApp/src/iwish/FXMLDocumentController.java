/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwish;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
