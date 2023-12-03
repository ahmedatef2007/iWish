package db;

import dto.ItemDTO;
import dto.MyContributersDTO;
import dto.UserDTO;
import dto.MyWishlistItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataAccessLayer {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public Connection ConnectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/iWish", "root", "root");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }

    public boolean login(String email, String password) {
        try {
            connect = ConnectDb();
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, email);
                prepare.setString(2, password);
                result = prepare.executeQuery();
                return result.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            closeDatabaseResources();
        }
        return false;
    }

    public boolean register(String username, String password, String email, String firstName, String lastName, String balance) {
        try {
            connect = ConnectDb();

            String checkUserExists = "SELECT * FROM users WHERE username = ? OR email = ?";
            String insertUser = "INSERT INTO users (username, password, email, firstName, lastName, balance) VALUES (?, ?, ?, ?, ?, ?)";

            try {
                prepare = connect.prepareStatement(checkUserExists);
                prepare.setString(1, username);
                prepare.setString(2, email);
                result = prepare.executeQuery();

                if (result.next()) {
                    return false;
                }

                prepare = connect.prepareStatement(insertUser);
                prepare.setString(1, username);
                prepare.setString(2, password);
                prepare.setString(3, email);
                prepare.setString(4, firstName);
                prepare.setString(5, lastName);
                prepare.setString(6, balance);
                int rowsAffected = prepare.executeUpdate();

                return rowsAffected > 0;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            closeDatabaseResources();
        }
        return false;
    }

    public int rechargeBalance(String user, String amount) {
        try {
            connect = ConnectDb();
            String updateBalance = "UPDATE users SET balance = balance + ?\n"
                    + "WHERE email = ?;";
            prepare = connect.prepareStatement(updateBalance);
            prepare.setString(1, amount);
            prepare.setString(2, user);
            prepare.executeUpdate();
            prepare.close();
            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /*
    public List<ItemDTO> showItems() {
        List<ItemDTO> items = new ArrayList<>();
        String selectData = "SELECT item_name, item_category, item_price FROM items";
        try {
            connect = ConnectDb();
            prepare = connect.prepareStatement(selectData);
            result = prepare.executeQuery();

            while (result.next()) {
                ItemDTO item = new ItemDTO(result.getString("item_name"),
                        result.getString("item_category"),
                        result.getDouble("item_price")
                );
                items.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeDatabaseResources();
        }
        return items;
    }
     */
    public void closeDatabaseResources() {
        try {
            if (result != null) {
                result.close();
            }
            if (prepare != null) {
                prepare.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Vector<UserDTO> retrieveallusers() {
        connect = ConnectDb();
        Vector<UserDTO> alluser = null;
        try {
            alluser = new Vector<UserDTO>();
            prepare = connect.prepareStatement("SELECT * FROM users");
            result = prepare.executeQuery();

            while (result.next()) {
                UserDTO udto = new UserDTO();
                udto.setFirstName(result.getString(1));

                udto.setLastName(result.getString(2));

                udto.setEmail(result.getString(3));
                alluser.add(udto);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return alluser;

    }

    public void getAllItems() {

    }

    public void addItemToWishlist() {

    }

    public Vector<MyWishlistItemDTO> getUserWishlist(String email) {
        Vector<MyWishlistItemDTO> wishlistItems = new Vector<>();

        try {
            connect = ConnectDb();
            String getWishlist = "SELECT\n"
                    + "	i.item_id,\n"
                    + "    i.item_name,\n"
                    + "    i.item_price,\n"
                    + "    COALESCE(SUM(c.contribution_amount), 0) AS total_friends_contribution\n"
                    + "FROM\n"
                    + "    user_wish_list uw\n"
                    + "JOIN\n"
                    + "    items i ON uw.item_id = i.item_id\n"
                    + "LEFT JOIN\n"
                    + "    contributions c ON uw.item_id = c.item_id\n"
                    + "WHERE\n"
                    + "    uw.user_email = ?\n"
                    + "GROUP BY\n"
                    + "	i.item_id,\n"
                    + "    i.item_name,\n"
                    + "    i.item_price;";

            try (PreparedStatement preparedStatement = connect.prepareStatement(getWishlist)) {
                preparedStatement.setString(1, email);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String item_id = resultSet.getString("item_id");
                        String itemName = resultSet.getString("item_name");
                        String itemPrice = resultSet.getString("item_price");
                        double totalContribution = resultSet.getDouble("total_friends_contribution");

                        MyWishlistItemDTO wishlistItem = new MyWishlistItemDTO(item_id, itemName, itemPrice, totalContribution);
                        wishlistItems.add(wishlistItem);
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Print the exception details
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close resources (connect) here if needed
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return wishlistItems;
    }

    public Vector<MyContributersDTO> getContributerslist(String email, String id) {
        Vector<MyContributersDTO> contributersList = new Vector<>();
        try {
            connect = ConnectDb();
            String getContributersList = "SELECT\n"
                    + "    u.firstName AS contributor_first_name,\n"
                    + "    u.lastName AS contributor_last_name,\n"
                    + "    u.email AS contributor_email,\n"
                    + "    c.contribution_amount\n"
                    + "FROM\n"
                    + "    users u\n"
                    + "JOIN\n"
                    + "    contributions c ON u.email = c.user_email\n"
                    + "JOIN\n"
                    + "    user_wish_list uw ON c.item_id = uw.item_id\n"
                    + "WHERE\n"
                    + "    uw.user_email = ?\n"
                    + "    AND uw.item_id = ?; ";

            try (PreparedStatement preparedStatement = connect.prepareStatement(getContributersList)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String contributor_first_name = resultSet.getString("contributor_first_name");
                        String contributor_last_name = resultSet.getString("contributor_last_name");
                        String contributor_email = resultSet.getString("contributor_email");
                        double contribution_amount = resultSet.getDouble("contribution_amount");

                        MyContributersDTO contributer = new MyContributersDTO(contributor_first_name, contributor_last_name,
                                contributor_email, contribution_amount);
                        contributersList.add(contributer);
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return contributersList;
    }
}
