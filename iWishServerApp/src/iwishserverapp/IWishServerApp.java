package iwishserverapp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dto.FriendRequestDTO;
import dto.ItemDTO;
import dto.MyContributersDTO;
import dto.MyWishlistItemDTO;
import dto.NotificationDTO;
import dto.UserDTO;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class IWishServerApp extends Application {

    private ServerSocket server;
    private Vector<ClientHandler> clientsVector;
    private boolean serverRunning;
    private ServerTask serverTask;

    private JFrame frame;
    private JButton startButton;
    private JButton stopButton;
    String cuemail;

    public IWishServerApp() {
        clientsVector = new Vector<>();
        serverRunning = false;

        // Create the GUI
        frame = new JFrame("IWish Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }

        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        frame.add(startButton);
        frame.add(stopButton);

        frame.setVisible(true);
    }

    public void startServer() {
        if (!serverRunning) {
            serverTask = new ServerTask();
            serverTask.execute();
        }
    }

    public void stopServer() {
        if (serverRunning) {
            try {
                if (serverRunning) {
                    serverTask.cancel(true);
                    server.close();
                    serverRunning = false;

                    // Enable start button and disable stop button
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private class ServerTask extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() {
            try {
                server = new ServerSocket(5005);
                serverRunning = true;

                SwingUtilities.invokeLater(() -> {
                    // Disable start button and enable stop button
                    startButton.setEnabled(false);
                    stopButton.setEnabled(true);
                });

                while (!isCancelled()) {
                    Socket clientSocket = server.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    clientsVector.add(clientHandler);
                    clientHandler.start();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                SwingUtilities.invokeLater(() -> {
                    // Enable start button and disable stop button
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                });
            }
            return null;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IWishServerApp();
            }
        });
    }

    private class ClientHandler extends Thread {

        private Socket clientSocket;
        private BufferedReader reader; // ear 
        private DataInputStream dis;
        private PrintStream ps; // mouth

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ps = new PrintStream(clientSocket.getOutputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String str = reader.readLine();

                    if (str == null || str.equals("close")) {
                        closeClient();
                        break;
                    }

                    switch (str) {
                        case "loginRequest":
                            handleLoginRequest();
                            break;
                        case "registerRequest":
                            handleRegisterRequest();
                            break;
                        case "getCurrentUserData":
                            handleGetCurrentUserData();
                            break;
                        case "Recharge":
                            handleRecharge();
                            break;
                        case "getUserWishlist":
                            handleGetUserWishlist();
                            break;
                        case "ShowContributers":
                            handleShowContributers();
                            break;
                        case "clearUserWishlist":
                            handleClearUserWishlist();
                            break;
                        case "removeItemFromWishlist":
                            handleRemoveItemFromWishlist();
                            break;
                        case "getUserNotifications":
                            handleGetUserNotifications();
                            break;
                        case "removeNotification":
                            handleRemoveNotification();
                            break;
                        case "clearNotifications":
                            handleClearNotifications();
                            break;
                        case "getAllItems":
                            handleGetAllItems();
                            break;
                        case "addToWishlist":
                            handleAddToWishlist();
                            break;
                        case "getallusersRequest":
                            handleGetAllUsersRequest();
                            break;
                        case "addfriendRequest":
                            handleAddFriendRequest();
                            break;
                        case "AllfriendRequest":
                            handleAllFriendRequest();
                            break;
                        case "cancelfriendRequest":
                            handleCancelFriendRequest();
                            break;
                        case "AcceptfriendRequest":
                            handleAcceptFriendRequest();
                            break;
                        case "SearchUsers":
                            handleSearchUsers();
                            break;
                        case "getUserfriendlist":
                            handleGetUserFriendList();
                            break;
                        case "getUserFriendWishlist":
                            handleGetUserFriendWishlist();
                            break;
                        case "ContributetoFriend":
                            handleContributeToFriend();
                            break;
                        case "RemoveFriend":
                            handleRemoveFriend();
                            break;
                        case "getUserbalance":
                            handleGetUserBalance();
                            break;
                        case "SearchItems":
                            handleSearchItems();
                            break;
                        default:
                            // Handle unknown command or do nothing
                            break;
                    }
                }
            } catch (IOException ex) {
                // Handle SocketException: Connection reset
                System.err.println("Client disconnected abruptly: " + ex.getMessage());
            } finally {
                closeClient();
            }
        }

        private void handleLoginRequest() {
            try {
                Gson gson = new Gson();
                String jsonUser = reader.readLine();
                UserDTO userDTO = gson.fromJson(jsonUser, UserDTO.class);
                cuemail = userDTO.getEmail();

                boolean auth = new db.DataAccessLayer().login(userDTO.getEmail(), userDTO.getPassword());
                sendMessage(auth ? "succeed" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleRegisterRequest() {
            try {
                Gson gson = new Gson();
                UserDTO newUser = gson.fromJson(reader.readLine(), UserDTO.class);

                boolean isRegistered = new db.DataAccessLayer().register(
                        newUser.getUsername(), newUser.getPassword(), newUser.getEmail(),
                        newUser.getFirstName(), newUser.getLastName(), String.valueOf(newUser.getBalance()));
                sendMessage(isRegistered ? "succeed" : "failed");
            } catch (JsonSyntaxException | IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleGetCurrentUserData() {
            try {
                String userEmail = reader.readLine();
                UserDTO currentUser = new db.DataAccessLayer().getCurrentUserData(userEmail);
                Gson gson = new Gson();
                sendMessage(gson.toJson(currentUser));
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleRecharge() {
            try {
                int addedBalance = new db.DataAccessLayer().rechargeBalance(reader.readLine(), reader.readLine());
                if (addedBalance == 1) {
                    //System.out.println("Balance Recharge Done");
                } else {
                    //System.out.println("Balance Recharge is not done");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleGetUserWishlist() {
            try {
                Gson gson = new Gson();
                String userEmail = reader.readLine();
                Vector<MyWishlistItemDTO> wishlistItems = new db.DataAccessLayer().getUserWishlist(userEmail);
                String jsonWishlist = gson.toJson(wishlistItems);
                sendMessage(jsonWishlist);
            } catch (JsonSyntaxException | IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleShowContributers() {
            try {
                Gson gson = new Gson();
                String userEmail = reader.readLine();
                String itemName = reader.readLine();
                Vector<MyContributersDTO> contributersList = new db.DataAccessLayer().getContributerslist(userEmail, itemName);
                String jsonWishlist = gson.toJson(contributersList);
                sendMessage(jsonWishlist);
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleClearUserWishlist() {
            try {
                String userEmail = reader.readLine();
                boolean success = new db.DataAccessLayer().clearUserWishlist(userEmail);
                sendMessage(success ? "success" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleRemoveItemFromWishlist() {
            try {
                String userEmail = reader.readLine();
                String itemId = reader.readLine();
                boolean success = new db.DataAccessLayer().removeItemFromWishlist(userEmail, itemId);
                sendMessage(success ? "success" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleGetUserNotifications() {
            try {
                Gson gson = new Gson();
                String userEmail = reader.readLine();
                Vector<NotificationDTO> notifications = new db.DataAccessLayer().getUserNotifications(userEmail);
                String jsonNotifications = gson.toJson(notifications);
                sendMessage(jsonNotifications);
            } catch (JsonSyntaxException | IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleRemoveNotification() {
            try {
                int notificationId = Integer.parseInt(reader.readLine());
                boolean removed = new db.DataAccessLayer().removeNotification(notificationId);
                sendMessage(removed ? "success" : "failed");
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleClearNotifications() {
            try {
                String userEmail = reader.readLine();
                boolean cleared = new db.DataAccessLayer().clearUserNotifications(userEmail);
                sendMessage(cleared ? "success" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleGetAllItems() {
            Gson gson = new Gson();
            Vector<ItemDTO> allitemlist = new db.DataAccessLayer().getAllItems();
            String jsonAllItems = gson.toJson(allitemlist);
            sendMessage(jsonAllItems);
        }

        private void handleAddToWishlist() {
            try {
                String id = reader.readLine();
                String userEmail = reader.readLine();
                boolean isItemAdded = new db.DataAccessLayer().addToWishlist(id, userEmail);
                sendMessage(isItemAdded ? "succeed" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleGetAllUsersRequest() {
            Vector<UserDTO> users = new db.DataAccessLayer().retrieveallusers(cuemail);
            Gson gson = new Gson();
            sendMessage(gson.toJson(users));
        }

        private void handleAddFriendRequest() {
            try {
                String cuemail = reader.readLine();
                String toUserEmail = reader.readLine();
                boolean resultaddfriend = new db.DataAccessLayer().addfriend(cuemail, toUserEmail);
                sendMessage(resultaddfriend ? "succeed" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleAllFriendRequest() {
            try {
                String cuemail = reader.readLine();
                Vector<FriendRequestDTO> allFriendRequest = new db.DataAccessLayer().AllfriendRequest(cuemail);
                Gson gson = new Gson();
                sendMessage(gson.toJson(allFriendRequest));
            } catch (IOException ex) {
                Logger.getLogger(IWishServerApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void handleCancelFriendRequest() {
            try {
                String fromUserEmail = reader.readLine();
                boolean cancelfriendRequest = new db.DataAccessLayer().cancelfriendRequest(fromUserEmail, cuemail);
                sendMessage(cancelfriendRequest ? "succeed" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleAcceptFriendRequest() {
            try {
                String fromUserEmail = reader.readLine();
                boolean acceptFriendRequest = new db.DataAccessLayer().AcceptfriendRequest(fromUserEmail, cuemail);
                sendMessage(acceptFriendRequest ? "succeed" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleSearchUsers() {
            try {
                String emaillike = reader.readLine();
                Gson gson = new Gson();
                Vector<UserDTO> searchUsers = new db.DataAccessLayer().SearchUsers(cuemail, emaillike);
                sendMessage(gson.toJson(searchUsers));
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleGetUserFriendList() {
            try {
                Gson gson = new Gson();
                String userEmail = reader.readLine();
                Vector<UserDTO> friendlist = new db.DataAccessLayer().getFriends(userEmail);
                String jsonfriendlist = gson.toJson(friendlist);
                sendMessage(jsonfriendlist);
            } catch (JsonSyntaxException | IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleGetUserFriendWishlist() {
            try {
                Gson gson = new Gson();
                String femail = reader.readLine();
                Vector<ItemDTO> friendwishlist = new db.DataAccessLayer().getFriendwhislist(femail);
                sendMessage(gson.toJson(friendwishlist));
            } catch (IOException ex) {
                Logger.getLogger(IWishServerApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void handleContributeToFriend() {
            try {
                int contributionResult = new db.DataAccessLayer().contributiontofriend(
                        reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine());

                sendMessage(contributionResult == 1 ? "succeed" : "failed");
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleRemoveFriend() {
            try {
                cuemail = reader.readLine();
                String femail = reader.readLine();
                new db.DataAccessLayer().removeFriend(cuemail, femail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleGetUserBalance() {
            try {
                Gson gson = new Gson();
                String userEmail = reader.readLine();
                Vector<UserDTO> userbalance = new db.DataAccessLayer().getBalance(userEmail);

                double balance = userbalance.isEmpty() ? 0.0 : userbalance.get(0).getBalance();
                sendMessage(gson.toJson(balance));
            } catch (JsonSyntaxException | IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void handleSearchItems() {
            try {
                String itemlike = reader.readLine();
                Gson gson = new Gson();
                Vector<ItemDTO> allitemlist = new db.DataAccessLayer().SearchItems(itemlike);
                sendMessage(gson.toJson(allitemlist));
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage("failed");
            }
        }

        private void sendMessage(String message) {
            ps.println(message);
        }

        private void closeClient() {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (ps != null) {
                    ps.close();
                }
                clientsVector.remove(this);
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
