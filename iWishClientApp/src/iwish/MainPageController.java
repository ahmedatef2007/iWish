package iwish;

import client.ClientSocketManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import dto.UserWishListDTO;
import dto.ItemDTO;
import dto.MyContributersDTO;
import dto.MyWishlistItemDTO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPageController {

    public String email;
    private ObservableList<MyWishlistItemDTO> myWishlistItemsData;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label nameLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<MyWishlistItemDTO> wishlistTable;

    private TableColumn<UserWishListDTO, String> itemNameColumn;

    private TableColumn<UserWishListDTO, Double> itemPriceColumn;

    private TableColumn<UserWishListDTO, Double> friendsContributionColumn;

    @FXML
    private TableView<ItemDTO> itemsTable;

    @FXML
    private TableColumn<ItemDTO, String> itemCategoryColumn;

    @FXML
    private TableColumn<ItemDTO, String> itemDescriptionColumn;

    @FXML
    private TableColumn<ItemDTO, Double> itemPriceColumnInItemsTab;

    @FXML
    private Label notificationsLabel;

    @FXML
    private ListView<String> notificationsListView;
    @FXML
    private VBox root;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button logout_btn;
    @FXML
    private Label friendsLabel1;
    @FXML
    private ListView<?> friendsListView1;
    @FXML
    private TableView<?> itemsTable11;
    @FXML
    private TableColumn<?, ?> itemCategoryColumn11;
    @FXML
    private TableColumn<?, ?> itemPriceColumnInItemsTab11;
    @FXML
    private TableColumn<?, ?> itemCategoryColumn111;
    @FXML
    private Label friendsLabel11;
    @FXML
    private TableColumn<?, ?> itemCategoryColumn1;
    @FXML
    private TableColumn<?, ?> itemPriceColumnInItemsTab1;
    // atef ///////////////////////////////////////////////////////////////
    @FXML
    private Tab homePage;
    @FXML
    private Label userNameLabel;
    @FXML
    private TableColumn<MyWishlistItemDTO, String> wishlist_itemNameColumn;
    @FXML
    private TableColumn<MyWishlistItemDTO, String> wishlist_itemPriceColumn;
    @FXML
    private TableColumn<MyWishlistItemDTO, String> wishlist_friendsContributionColumn;
    @FXML
    private Button showContributersButton;
    ///////////////////////////////////////////////////////////////
    @FXML
    private Tab friendsPage;
    @FXML
    private Tab allItemsPage;
    @FXML
    private Tab connectPage;
    @FXML
    private Tab notificationsPage;
    @FXML
    private ComboBox<?> itemCategoryComboBox;
    @FXML
    private TextField itemNameTf;
    @FXML
    private TextField itemPriceTf;
    @FXML
    private Button addToWishlistButton;
    @FXML
    private Button clearButton;
    @FXML
    private Label allContactsLabel;
    @FXML
    private Button addFriendButton;
    @FXML
    private TableView<?> allUsersTable;
    @FXML
    private TextField friendSearchTf;
    @FXML
    private Button rechargeBalanceButton;
    @FXML
    private Label userEmailLabel;
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

    private ObservableList<MyContributersDTO> myContributersList;

    public void initialize() {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        //initializeconnectPage();
        //initializeNotificationsListView();
        setUserEmail(email);  // Set the email first
    }

    @FXML
    public void logout() {
        Stage currentStage = (Stage) logout_btn.getScene().getWindow();
        currentStage.hide();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginRegisterFXMLDocument.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("I-Wish App");

            // Set the close request handler
            stage.setOnCloseRequest(event -> {
                Platform.exit(); // Close the JavaFX application
                System.exit(0);  // Terminate the Java Virtual Machine
            });

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void openRechargeWindow() {

        try {
            FXMLLoader rechargeSceneLoader = new FXMLLoader(getClass().getResource("RechargeScene.fxml"));
            Parent rechargeSceneRoot = rechargeSceneLoader.load();
            RechargeSceneController rechargeSceneController = rechargeSceneLoader.getController();
            rechargeSceneController.setEmail(userEmailLabel.getText());

            Stage stage = new Stage();
            Scene scene = new Scene(rechargeSceneRoot);
            stage.setScene(scene);
            stage.setTitle("Recharge Balance");

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUserEmail(String email) {
        userEmailLabel.setText(email);
        System.out.println("Received data in MainPageController: " + email);
        initializeUserWishlist(email);
        showMywishlistItemsData();
    }

    // marina 
    private void initializeconnectPage() {
        ClientSocketManager.getOutputStream().println("getallusersRequest");// request type
        try {
            String response = ClientSocketManager.getInputStream().readLine();
            System.out.println(response);
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ObservableList<MyWishlistItemDTO> initializeUserWishlist(String email) {
        ObservableList<MyWishlistItemDTO> uiWishlistItems = FXCollections.observableArrayList();

        ClientSocketManager.getOutputStream().println("getUserWishlist");// request type
        ClientSocketManager.getOutputStream().println(email); // email
        try {
            String jsonWishlist = ClientSocketManager.getInputStream().readLine();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<MyWishlistItemDTO>>() {
            }.getType();

            List<MyWishlistItemDTO> wishlistItems = gson.fromJson(jsonWishlist, listType);

            // Process the received wishlistItems
            if (wishlistItems != null && !wishlistItems.isEmpty()) {
                // Add wishlist items to the UI observable list
                uiWishlistItems.addAll(wishlistItems);

                // Update UI with the wishlistItems
                for (MyWishlistItemDTO item : wishlistItems) {
                    System.out.println("Item Name: " + item.getItem_name());
                    System.out.println("Item Price: " + item.getItem_price());
                    System.out.println("Total Contribution: " + item.getTotal_contribution());
                }
            } else {
                System.out.println("Wishlist is empty or couldn't be retrieved.");
            }

        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uiWishlistItems;
    }

    public void showMywishlistItemsData() {
        myWishlistItemsData = initializeUserWishlist(email);
        wishlist_itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        wishlist_itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        wishlist_friendsContributionColumn.setCellValueFactory(new PropertyValueFactory<>("total_contribution"));
        wishlistTable.setItems(myWishlistItemsData);
    }

    @FXML
    public void openContributersWindow() {
        ObservableList<MyContributersDTO> myContributersList = FXCollections.observableArrayList();
        
        MyWishlistItemDTO myWishlistItemDTO = wishlistTable.getSelectionModel().getSelectedItem();
        String id = myWishlistItemDTO.getId();
        
        try {
            ClientSocketManager.initializeSocket();
            ClientSocketManager.getOutputStream().println("ShowContributers"); // request type
            ClientSocketManager.getOutputStream().println(email); // user email
            ClientSocketManager.getOutputStream().println(id); // item id

            String jsonContributerslist = ClientSocketManager.getInputStream().readLine();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<MyContributersDTO>>() {
            }.getType();

            List<MyContributersDTO> contributersList = gson.fromJson(jsonContributerslist, listType);
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
        //contributers_firstname_column.setCellValueFactory(new PropertyValueFactory<>("contributor_first_name"));
        //contributers_lastname_column.setCellValueFactory(new PropertyValueFactory<>("contributor_last_name"));
        contributers_email_column.setCellValueFactory(new PropertyValueFactory<>("contributor_email"));
        contributers_amount_column.setCellValueFactory(new PropertyValueFactory<>("contribution_amount"));
        contributers_tableview.setItems(myContributersList);
        /*
        try {
            MyWishlistItemDTO myWishlistItemDTO = wishlistTable.getSelectionModel().getSelectedItem();
            String id = myWishlistItemDTO.getId(); 
            
            
            FXMLLoader contributersSceneLoader = new FXMLLoader(getClass().getResource("ContributersList.fxml"));
            Parent contributersSceneRoot = contributersSceneLoader.load();
            ContributersListController contributersListController = contributersSceneLoader.getController();
            contributersListController.setEmail(email);
            contributersListController.setId(id);
            Stage stage = new Stage();
            Scene scene = new Scene(contributersSceneRoot);
            stage.setScene(scene);
            stage.setTitle("Contributers List");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    /*private void initializeNotificationsListView() {
        // Create dummy data for the notificationsListView
        ObservableList<String> notifications = FXCollections.observableArrayList(
                "Notification 1",
                "Notification 2",
                "Notification 3"
        // Add more notifications as needed
        );

        // Set the dummy data to the notificationsListView
        notificationsListView.setItems(notifications);
    }*/
    public void addToWishlist(String email) {

    }
}
