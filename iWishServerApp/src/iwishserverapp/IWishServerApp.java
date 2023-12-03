package iwishserverapp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dto.MyContributersDTO;
import dto.MyWishlistItemDTO;
import dto.UserDTO;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class IWishServerApp {

    private ServerSocket server;
    private Vector<ClientHandler> clientsVector;

    public IWishServerApp() {
        clientsVector = new Vector<>();

        try {
            server = new ServerSocket(5005);
            while (true) {
                Socket clientSocket = server.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientsVector.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new IWishServerApp();
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
                    } else if (str.equals("loginRequest")) {
                        Gson gson = new Gson();
                        // Read the JSON object string from the client
                        String jsonUser = reader.readLine();
                        // Convert the JSON string to a UserDTO object
                        UserDTO userDTO = gson.fromJson(jsonUser, UserDTO.class);
                        boolean auth = new db.DataAccessLayer().login(userDTO.getEmail(), userDTO.getPassword());
                        if (auth) {
                            sendMessage("succeed");
                        } else {
                            sendMessage("failed");
                        }
                    } else if (str.equals("registerRequest")) {
                        try {
                            Gson gson = new Gson();
                            UserDTO newUser = gson.fromJson(reader.readLine(), UserDTO.class);

                            boolean isRegistered = new db.DataAccessLayer().register(
                                    newUser.getUsername(), newUser.getPassword(), newUser.getEmail(),
                                    newUser.getFirstName(), newUser.getLastName(), String.valueOf(newUser.getBalance()));
                            if (isRegistered) {
                                sendMessage("succeed");
                            } else {
                                sendMessage("failed");
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            sendMessage("failed");
                        }
                    } else if (str.equals("Recharge")) { // email , amount
                        int addedBalance = new db.DataAccessLayer().rechargeBalance(reader.readLine(), reader.readLine());
                        if (addedBalance == 1) {
                            System.out.println("Balance Recharge Done");
                        } else {
                            System.out.println("Balance Recharge is not done");
                        }
                    } else if (str.equals("getUserWishlist")) {
                        try {
                            Gson gson = new Gson();
                            String userEmail = reader.readLine();
                            System.out.println("User Email: " + userEmail);

                            Vector<MyWishlistItemDTO> wishlistItems = new db.DataAccessLayer().getUserWishlist(userEmail);

                            System.out.println("Wishlist Items: " + wishlistItems);

                            String jsonWishlist = gson.toJson(wishlistItems);
                            sendMessage(jsonWishlist);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            sendMessage("failed");
                        }
                    } //
                    else if (str.equals("ShowContributers")) {
                        Gson gson = new Gson();
                        String userEmail = reader.readLine();
                        String userId = reader.readLine();
                        Vector<MyContributersDTO> contributersList
                                = new db.DataAccessLayer().getContributerslist(userEmail, userId);
                        String jsonWishlist = gson.toJson(contributersList);
                        sendMessage(jsonWishlist);

                    } else if (str.equals("getallusersRequest")) {
                        Vector<UserDTO> users = new db.DataAccessLayer().retrieveallusers();
                        Gson gson = new Gson();

                        String json = gson.toJson(users);
                        //System.out.println(users);
                        sendMessage(users.toString());

                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                closeClient();
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
