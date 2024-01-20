package iwish;

import client.ClientSocketManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class RechargeSceneController implements Initializable {

    @FXML
    private TextField Amount;
<<<<<<< HEAD
=======

>>>>>>> d4b5a53 (Final Commit)
    private String email;
    @FXML
    private TextField cardNum;
    @FXML
    private TextField CVV;
    @FXML
    private DatePicker ExpireDate;
    @FXML
    private Button confirmButton;

    @FXML
    public void recharge() {
        String amount = Amount.getText();
        String cardNumber = cardNum.getText();
        String cvv = CVV.getText();

        if (!isValidAmount(amount) || !isValidCardNumber(cardNumber) || !isValidCVV(cvv)) {
            showValidationError("Invalid input. Please check your entries.");
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to recharge your balance with " + amount + "?",
                "Confirm Transaction",
                JOptionPane.YES_NO_OPTION
        );

<<<<<<< HEAD
        if (result == JOptionPane.YES_OPTION) {
=======
        if (result.isPresent() && result.get() == ButtonType.OK) {
>>>>>>> d4b5a53 (Final Commit)
            ClientSocketManager.initializeSocket();
            ClientSocketManager.getOutputStream().println("Recharge");
            ClientSocketManager.getOutputStream().println(email);
            ClientSocketManager.getOutputStream().println(amount);
            JOptionPane.showMessageDialog(null, "Recharging has been done successfully, refresh your profile");

            closeWindow();
        }
    }

<<<<<<< HEAD
    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(null, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isValidAmount(String amount) {
        return amount != null && amount.matches("\\d+(\\.\\d+)?") && Double.parseDouble(amount) >= 0;
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches("\\d+") && Long.parseLong(cardNumber) > 0;
    }

    private boolean isValidCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{1,3}");
    }

    /**
     * private void showValidationError(String message) { Alert alert = new
     * Alert(Alert.AlertType.ERROR); alert.setTitle("Validation Error");
     * alert.setContentText(message); alert.showAndWait(); }
     *
     */
    private void closeWindow() {
        Stage stage = (Stage) Amount.getScene().getWindow();
=======
    private void closeWindow() {
        // Get the current stage
        Stage stage = (Stage) Amount.getScene().getWindow();
        // Close the stage
>>>>>>> d4b5a53 (Final Commit)
        stage.close();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
