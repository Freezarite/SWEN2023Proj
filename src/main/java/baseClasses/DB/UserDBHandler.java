package baseClasses.DB;

import baseClasses.Card.Card;
import baseClasses.Card.CardData;
import baseClasses.User.User;
import baseClasses.User.UserData;

import java.sql.*;
import java.util.*;

public class UserDBHandler implements DBBasic{

    private Connection connection;

    public UserDBHandler() {

        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createUser(User user, String password) {
        try {
            if (userExists(user.getUsername())) {
                System.out.println("User with username " + user.getUsername() + " already exists.");
                return false;
            }

            // If the user doesn't exist, proceed with the insertion
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (username, name, password, user_wins, user_losses, user_elo, coins, admin) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getName()); // Assuming you have a getName() method in your User class
                statement.setString(3, password); // Assuming you have a getPassword() method in your User class
                statement.setInt(4, user.getUserWins());
                statement.setInt(5, user.getUserLosses());
                statement.setInt(6, user.getUserElo());
                statement.setInt(7, 20);
                statement.setBoolean(8, user.getAdminStatus());
                // Assuming you have appropriate methods for collection_id and stack_id in your User class

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User with username " + user.getUsername() + " created successfully.");
                    return true;
                } else {
                    System.out.println("Failed to create user with username " + user.getUsername() + ".");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
            return false;
        }
    }

    public boolean createUser(String username, String password) {
        try {
            if (userExists(username)) {
                System.out.println("User with username " + username + " already exists.");
                return false;
            }

            // If the user doesn't exist, proceed with the insertion
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (username, password, user_wins, user_losses, user_elo, coins, admin) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setInt(3, 0);
                statement.setInt(4, 0);
                statement.setInt(5, 1000);
                statement.setInt(6, 20);
                statement.setBoolean(7, false);
                //generally huge security risk, but wanted in the script
                if(username.equals("admin"))
                    statement.setBoolean(7, true);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User with username " + username + " created successfully.");
                    return true;
                } else {
                    System.out.println("Failed to create user with username " + username + ".");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean userExists(String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) AS count FROM users WHERE username = ?")) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        return false;
    }

    public UserData getUserDataFromDB(String username) {
        try(PreparedStatement statement = connection.prepareStatement(
                "SELECT name, bio, image FROM users WHERE username = ?")) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new UserData(resultSet.getString("name"), resultSet.getString("bio"), resultSet.getString("image"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User readUser(String username) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?")) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int wins = resultSet.getInt("user_wins");
                int losses = resultSet.getInt("user_losses");
                int elo = resultSet.getInt("user_elo");

                return new User(username, wins, losses, elo);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
        return null;
    }

    public void updateUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET user_wins = ?, user_losses = ?, user_elo = ? WHERE username = ?")) {
            statement.setInt(1, user.getUserWins());
            statement.setInt(2, user.getUserLosses());
            statement.setInt(3, user.getUserElo());
            statement.setString(4, user.getUsername());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }

    public void deleteUser(String username) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE username = ?")) {
            statement.setString(1, username);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }

    public boolean loginUser(String username, String password) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if there is a matching user with the given username and password
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
        return false;
    }
    public boolean isAdmin(String username) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT admin FROM users WHERE username = ?")) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("admin");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
        return false;
    }

    public void updateUserDataOfSpecificUser(String username, UserData userData) {
        try(PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET name = ?, bio = ?, image = ? WHERE username = ?"
        )) {

            statement.setString(1, userData.Name());
            statement.setString(2, userData.Bio());
            statement.setString(3, userData.Image());
            statement.setString(4, username);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public List<CardData> getAllCardsFromUser(String username) {
        try(PreparedStatement statement = connection.prepareStatement(
                "SELECT cards.card_id, cards.card_name, cards.damage\n" +
                        "FROM users\n" +
                        "JOIN cards ON cards.card_id = ANY(users.collection_id)\n" +
                        "WHERE users.username = ?"
        )) {
            statement.setString(1, username);

           ResultSet resultSet = statement.executeQuery();
           List<CardData> output = new ArrayList<>();

           while(resultSet.next()) {
               output.add(new CardData((UUID) resultSet.getObject("card_id"),
                       resultSet.getString("card_name"), resultSet.getInt("damage")));
           }

           System.out.println(output);

            return output;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfCardsBelongToUser(List<UUID> cardList, String username) {
        /*try(PreparedStatement statement = connection.prepareStatement(
                "SELECT cards.card_id" +
                        "FROM users\n" +
                        "JOIN cards ON cards.card_id = ANY(users.collection_id)\n" +
                        "WHERE users.username = ?"
        )) {
            ResultSet resultSet = statement.executeQuery();
            List<UUID> collectionList = new ArrayList<>();

            boolean notEmpty = false;

            while(resultSet.next()) {
                notEmpty = true;
                collectionList.add((UUID) resultSet.getObject("card_id"));
            }

            if(!notEmpty)
                return notEmpty;

            System.out.println("In db class" + collectionList);

            for(UUID card : cardList) {
                if(!collectionList.contains(card))
                    return false;
            }

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       */

        List<CardData> collectionList = getAllCardsFromUser(username);
        for(UUID card : cardList) {
            boolean contains = false;
            for(CardData data : collectionList) {
                if(data.id().equals(card))
                    contains = true;
            }
            if(!contains)
                return false;
        }
        return true;
    }

    public void updateStackOfUser(List<UUID> newStack, String username) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE users SET stack_id = ? WHERE username = ?"
        )) {
            preparedStatement.setArray(1, connection.createArrayOf("UUID", newStack.toArray()));
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
            System.out.println("Updated deck!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

