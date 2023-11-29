package iwish;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    // client
    static Socket socket;
    static DataInputStream dis;
    static PrintStream ps;
    Alert alert;

    protected final BorderPane login_form;
    protected final AnchorPane anchorPane;
    protected final Button si_createAccountBtn;
    protected final Text text;
    protected final Label label;
    protected final AnchorPane anchorPane0;
    protected final Label label0;
    protected final TextField si_username;
    protected final PasswordField si_password;
    protected final Button si_loginBtn;
    protected final BorderPane sifnup_form;
    protected final AnchorPane anchorPane1;
    protected final Button su_loginAccountBtn;
    protected final Text text0;
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
        label = new Label();
        anchorPane0 = new AnchorPane();
        label0 = new Label();
        si_username = new TextField();
        si_password = new PasswordField();
        si_loginBtn = new Button();
        sifnup_form = new BorderPane();
        anchorPane1 = new AnchorPane();
        su_loginAccountBtn = new Button();
        text0 = new Text();
        label1 = new Label();
        anchorPane2 = new AnchorPane();
        label2 = new Label();
        su_username = new TextField();
        su_password = new PasswordField();
        signup_btn = new Button();

        setMaxHeight(USE_PREF_SIZE);

        setMaxWidth(USE_PREF_SIZE);

        setMinHeight(USE_PREF_SIZE);

        setMinWidth(USE_PREF_SIZE);

        setPrefHeight(
                501.0);
        setPrefWidth(
                740.0);

        login_form.setLayoutX(
                10.0);
        login_form.setLayoutY(
                10.0);
        login_form.setPrefHeight(
                200.0);
        login_form.setPrefWidth(
                200.0);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);

        anchorPane.setPrefHeight(
                501.0);
        anchorPane.setPrefWidth(
                335.0);
        anchorPane.getStyleClass()
                .add("other-form");
        anchorPane.getStylesheets()
                .add("/iwish/design.css");

        si_createAccountBtn.setLayoutX(
                69.0);
        si_createAccountBtn.setLayoutY(
                431.0);
        si_createAccountBtn.setMnemonicParsing(
                false);
        si_createAccountBtn.setPrefHeight(
                32.0);
        si_createAccountBtn.setPrefWidth(
                196.0);
        si_createAccountBtn.getStyleClass()
                .add("create-btn");
        si_createAccountBtn.setText(
                "Register");

        text.setFill(javafx.scene.paint.Color.WHITE);

        text.setLayoutX(
                130.0);
        text.setLayoutY(
                417.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);

        text.setStrokeWidth(
                0.0);
        text.setText(
                "Register now");

        label.setLayoutX(
                98.0);
        label.setLayoutY(
                137.0);
        label.getStyleClass()
                .add("register");
        label.setText(
                "i-Wish App");
        login_form.setLeft(anchorPane);

        BorderPane.setAlignment(anchorPane0, javafx.geometry.Pos.CENTER);

        anchorPane0.setPrefHeight(
                501.0);
        anchorPane0.setPrefWidth(
                406.0);

        label0.setLayoutX(
                129.0);
        label0.setLayoutY(
                133.0);
        label0.getStyleClass()
                .add("login");
        label0.getStylesheets()
                .add("/iwish/design.css");
        label0.setText(
                "Log In  Form");
        label0.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        si_username.setLayoutX(
                148.0);
        si_username.setLayoutY(
                237.0);
        si_username.setPromptText(
                "Enter Your Username");

        si_password.setLayoutX(
                148.0);
        si_password.setLayoutY(
                296.0);
        si_password.setPromptText(
                "Password");

        si_loginBtn.setLayoutX(
                142.0);
        si_loginBtn.setLayoutY(
                353.0);
        si_loginBtn.setMnemonicParsing(
                false);
        si_loginBtn.setPrefHeight(
                37.0);
        si_loginBtn.setPrefWidth(
                131.0);
        si_loginBtn.getStylesheets()
                .add("/iwish/design.css");
        si_loginBtn.setText(
                "Login");
        login_form.setRight(anchorPane0);

        sifnup_form.setLayoutX(
                10.0);
        sifnup_form.setLayoutY(
                10.0);
        sifnup_form.setPrefHeight(
                200.0);
        sifnup_form.setPrefWidth(
                200.0);
        sifnup_form.setVisible(
                false);

        BorderPane.setAlignment(anchorPane1, javafx.geometry.Pos.CENTER);

        anchorPane1.setPrefHeight(
                501.0);
        anchorPane1.setPrefWidth(
                335.0);
        anchorPane1.getStyleClass()
                .add("other-form");
        anchorPane1.getStylesheets()
                .add("/iwish/design.css");

        su_loginAccountBtn.setLayoutX(
                69.0);
        su_loginAccountBtn.setLayoutY(
                431.0);
        su_loginAccountBtn.setMnemonicParsing(
                false);
        su_loginAccountBtn.setPrefHeight(
                32.0);
        su_loginAccountBtn.setPrefWidth(
                196.0);
        su_loginAccountBtn.getStyleClass()
                .add("create-btn");
        su_loginAccountBtn.setText(
                "Log in");

        text0.setFill(javafx.scene.paint.Color.WHITE);

        text0.setLayoutX(
                69.0);
        text0.setLayoutY(
                420.0);
        text0.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);

        text0.setStrokeWidth(
                0.0);
        text0.setText(
                "Already Have an Acount??");
        text0.setWrappingWidth(
                181.31640625);

        label1.setLayoutX(
                98.0);
        label1.setLayoutY(
                137.0);
        label1.getStyleClass()
                .add("register");
        label1.setText(
                "i-Wish App");
        sifnup_form.setLeft(anchorPane1);

        BorderPane.setAlignment(anchorPane2, javafx.geometry.Pos.CENTER);

        anchorPane2.setPrefHeight(
                501.0);
        anchorPane2.setPrefWidth(
                406.0);

        label2.setLayoutX(
                118.0);
        label2.setLayoutY(
                133.0);
        label2.getStyleClass()
                .add("login");
        label2.getStylesheets()
                .add("/iwish/design.css");
        label2.setText(
                "Register Form");

        su_username.setLayoutX(
                148.0);
        su_username.setLayoutY(
                237.0);
        su_username.setPromptText(
                "Enter Your Username");

        su_password.setLayoutX(
                148.0);
        su_password.setLayoutY(
                296.0);
        su_password.setPromptText(
                "Password");

        signup_btn.setLayoutX(
                142.0);
        signup_btn.setLayoutY(
                353.0);
        signup_btn.setMnemonicParsing(
                false);
        signup_btn.setPrefHeight(
                37.0);
        signup_btn.setPrefWidth(
                131.0);
        signup_btn.getStyleClass()
                .add("other-form");
        signup_btn.getStylesheets()
                .add("/iwish/design.css");
        signup_btn.setText(
                "Create Account");
        sifnup_form.setRight(anchorPane2);

        anchorPane.getChildren()
                .add(si_createAccountBtn);
        anchorPane.getChildren()
                .add(text);
        anchorPane.getChildren()
                .add(label);
        anchorPane0.getChildren()
                .add(label0);
        anchorPane0.getChildren()
                .add(si_username);
        anchorPane0.getChildren()
                .add(si_password);
        anchorPane0.getChildren()
                .add(si_loginBtn);
        getChildren()
                .add(login_form);
        anchorPane1.getChildren()
                .add(su_loginAccountBtn);
        anchorPane1.getChildren()
                .add(text0);
        anchorPane1.getChildren()
                .add(label1);
        anchorPane2.getChildren()
                .add(label2);
        anchorPane2.getChildren()
                .add(su_username);
        anchorPane2.getChildren()
                .add(su_password);
        anchorPane2.getChildren()
                .add(signup_btn);
        getChildren()
                .add(sifnup_form);

        // db  connection
        si_loginBtn.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                loginAccount();
            }
        }
        );

    }

    public void loginAccount() {
        try (Socket socket = new Socket("127.0.0.1", 5005);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                PrintStream ps = new PrintStream(socket.getOutputStream())) {

            if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill in your username and password");
                return;
            }

            ps.println("loginRequest");
            ps.println(si_username.getText());
            ps.println(si_password.getText());

            // Wait for the response from the server
            String response = dis.readLine();

            if (response.equals("succeed")) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Login Success!");
            } else if (response.equals("failed")) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Incorrect username or password");
            }

        } catch (ConnectException conex) {
            handleConnectionError();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void handleConnectionError() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Problem");
            alert.setHeaderText("Server not responding");
            String s = "Please try again";
            alert.setContentText(s);
            alert.show();
        });
    }
}
