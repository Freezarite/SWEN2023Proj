package baseClasses.DB;

import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDBHandler extends DBBasic {

    public static void saveCard(Card card) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO cards(id, name, damage, element, type) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, card.getId());
                preparedStatement.setString(2, card.getCardName());
                preparedStatement.setInt(3, card.getCardDamage());
                preparedStatement.setString(4, card.getCardElement().toString());
                preparedStatement.setString(5, card instanceof MonsterCard ? "MONSTER" : "SPELL");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Card getCardById(int cardId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM cards WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, cardId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        int damage = resultSet.getInt("damage");
                        Card.elementType element = Card.elementType.valueOf(resultSet.getString("element"));
                        String type = resultSet.getString("type");
                        if ("MONSTER".equals(type)) {
                            MonsterCard.monsterType monsterType = MonsterCard.monsterType.valueOf(resultSet.getString("monster_type"));
                            return new MonsterCard(cardId, name, damage, element, monsterType);
                        } else {
                            return new SpellCard(cardId, name, damage, element);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateCard(Card card) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "UPDATE cards SET name=?, damage=?, element=?, monster_type=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, card.getCardName());
                preparedStatement.setInt(2, card.getCardDamage());
                preparedStatement.setString(3, card.getCardElement().toString());
                preparedStatement.setString(4, (card instanceof MonsterCard) ? ((MonsterCard) card).getMonsterType().toString() : null);
                preparedStatement.setInt(5, card.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCard(int cardId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "DELETE FROM cards WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, cardId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Card> getAllCards() {
        List<Card> cardList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM cards";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        int damage = resultSet.getInt("damage");
                        Card.elementType element = Card.elementType.valueOf(resultSet.getString("element"));
                        String type = resultSet.getString("type");
                        if ("MONSTER".equals(type)) {
                            MonsterCard.monsterType monsterType = MonsterCard.monsterType.valueOf(resultSet.getString("monster_type"));
                            cardList.add(new MonsterCard(id, name, damage, element, monsterType));
                        } else {
                            cardList.add(new SpellCard(id, name, damage, element));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardList;
    }
}
