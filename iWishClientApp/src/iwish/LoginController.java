package iwish;

import client.ClientSocketManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class LoginController extends StackPane {

    private Alert alert;

    protected final BorderPane login_form;
    protected final AnchorPane anchorPane;
    protected final Button si_createAccountBtn;
    protected final Text text;
    protected final Button si_loginBtn;
    protected final BorderPane sifnup_form;
    protected final AnchorPane anchorPane1;
    protected final Button su_loginAccountBtn;
    protected final Text text0;
    protected final TextField si_username;
    protected final PasswordField si_password;
    protected final Label label1;
    protected final AnchorPane anchorPane2;
    protected final Label label2;
    protected final TextField su_username;
    protected final PasswordField su_password;
    protected final Button signup_btn;

    public LoginController() {

        login_form = new BorderPane();
        anchorPane = new AnchorPane();
        si_createAccountBtn = new Button();
        text = new Text();
        si_loginBtn = new Button();
        sifnup_form = new BorderPane();
        anchorPane1 = new AnchorPane();
        su_loginAccountBtn = new Button();
        text0 = new Text();
        si_username = new TextField();
        si_password = new PasswordField();
        label1 = new Label();
        anchorPane2 = new AnchorPane();
        label2 = new Label();
        su_username = new TextField();
        su_password = new PasswordField();
        signup_btn = new Button();

        // Setting up UI elements...

        // db  connection
        si_loginBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginAccount();
            }
        });
    }

    public void loginAccount() {
        try {
            ClientSocketManager.initializeSocket();

            if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill in your username and password");
                return;
            }

            ClientSocketManager.getOutputStream().println("loginRequest");
            ClientSocketManager.getOutputStream().println(si_username.getText());
            ClientSocketManager.getOutputStream().println(si_password.getText());

            // Wait for the response from the server
            String response = ClientSocketManager.getInputStream().readLine();

            if (response.equals("succeed")) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Login Success!");
            } else if (response.equals("failed")) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Incorrect username or password");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ClientSocketManager.closeSocket();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Platform.runLater(() -> {
            alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
