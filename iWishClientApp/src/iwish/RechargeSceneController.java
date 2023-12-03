package iwish;

import client.ClientSocketManager;
import dto.DataHolder;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class RechargeSceneController implements Initializable {

    private TextField Amount;
    
    private String email;
    @FXML
    private TableView<?> contributers_tableview;
    @FXML
    private TableColumn<?, ?> contributers_firstname_column;
    @FXML
    private TableColumn<?, ?> contributers_lastname_column;
    @FXML
    private TableColumn<?, ?> contributers_email_column;
    @FXML
    private TableColumn<?, ?> contributers_amount_column;
    /**
     * Initializes the controller class.
     */
    public void recharge() {
        String amount = Amount.getText();

        if (amount == null || amount.isEmpty() || !amount.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid amount (numbers only)");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Transaction");
        String s = "Are you sure you want to recharge your balance with " + amount + "?";
        alert.setContentText(s);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Initialize the socket and output stream
            ClientSocketManager.initializeSocket();
            // Send recharge request and data to the server
            ClientSocketManager.getOutputStream().println("Recharge");
            ClientSocketManager.getOutputStream().println(email); // user email 
            ClientSocketManager.getOutputStream().println(amount); // amount
            JOptionPane.showMessageDialog(null, "Recharging has been done successfully, refresh your profile");

        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
