/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwish;

import client.ClientSocketManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.MyContributersDTO;
import dto.MyWishlistItemDTO;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class ContributersListController implements Initializable {

    @FXML
    private TableView<MyContributersDTO> contributers_tableview;
    @FXML
    private TableColumn<MyContributersDTO, String> contributers_firstname_column;
    @FXML
    private TableColumn<MyContributersDTO, String> contributers_lastname_column;
    @FXML
    private TableColumn<MyContributersDTO, String> contributers_email_column;
    @FXML
    private TableColumn<MyContributersDTO, String> contributers_amount_column;

    private String id;
    private String email;
    private ObservableList<MyContributersDTO> myContributersList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showContributers();
    }

    public void showContributers() {
        ObservableList<MyContributersDTO> myContributersList = FXCollections.observableArrayList();

        try {
            ClientSocketManager.initializeSocket();
            ClientSocketManager.getOutputStream().println("ShowContributers"); // request type
            ClientSocketManager.getOutputStream().println(email); // user email
            ClientSocketManager.getOutputStream().println(id); // item id

            String jsonWishlist = ClientSocketManager.getInputStream().readLine();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<MyContributersDTO>>() {
            }.getType();

            List<MyContributersDTO> contributersList = gson.fromJson(jsonWishlist, listType);
            System.out.println(contributersList);
            for (MyContributersDTO c : contributersList) {
                System.out.println("contributor_first_name: " + c.getContributor_first_name());
                System.out.println("contributor_last_name: " + c.getContributor_last_name());
                System.out.println("contributor_email: " + c.getContributor_email());
                System.out.println("contribution_amount: " + c.getContribution_amount());
            }
            myContributersList.addAll(contributersList);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        contributers_firstname_column.setCellValueFactory(new PropertyValueFactory<>("contributor_first_name"));
        contributers_lastname_column.setCellValueFactory(new PropertyValueFactory<>("contributor_last_name"));
        contributers_email_column.setCellValueFactory(new PropertyValueFactory<>("contributor_email"));
        contributers_amount_column.setCellValueFactory(new PropertyValueFactory<>("contribution_amount"));
        contributers_tableview.setItems(myContributersList);
    }

}
