package baseClasses.DB;

import baseClasses.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBHandler extends DBBasic{

    // Create a new user in the database
    public static void createUser(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO users(username, wins, losses, elo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setInt(2, user.getUserWins());
                preparedStatement.setInt(3, user.getUserLosses());
                preparedStatement.setInt(4, user.getUserElo());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read user details from the database based on username
    public static User readUser(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int wins = resultSet.getInt("wins");
                        int losses = resultSet.getInt("losses");
                        int elo = resultSet.getInt("elo");
                        return new User(username, wins, losses, elo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update user details in the database
    public static void updateUser(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "UPDATE users SET wins=?, losses=?, elo=? WHERE username=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, user.getUserWins());
                preparedStatement.setInt(2, user.getUserLosses());
                preparedStatement.setInt(3, user.getUserElo());
                preparedStatement.setString(4, user.getUsername());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete user from the database
    public static void deleteUser(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "DELETE FROM users WHERE username=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all users from the database
    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM users";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        String username = resultSet.getString("username");
                        int wins = resultSet.getInt("wins");
                        int losses = resultSet.getInt("losses");
                        int elo = resultSet.getInt("elo");
                        userList.add(new User(username, wins, losses, elo));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

}

