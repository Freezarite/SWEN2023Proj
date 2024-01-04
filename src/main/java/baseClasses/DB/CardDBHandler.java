package baseClasses.DB;

import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;
import baseClasses.DB.DBBasic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardDBHandler implements DBBasic {
    private Connection connection;

    public CardDBHandler() {
        try {
            this.connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create
    public void addMonsterCard(MonsterCard monsterCard) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO cards (card_id, card_name, damage, element, card_type, monster_type) VALUES (?, ?, ?, ?, 'Monster', ?)")) {
            statement.setObject(1, monsterCard.getId());
            statement.setString(2, monsterCard.getCardName());
            statement.setInt(3, monsterCard.getCardDamage());
            statement.setString(4, monsterCard.getCardElement().toString());
            statement.setString(5, monsterCard.getMonsterType().toString());
            statement.executeUpdate();
        }
    }

    public void addSpellCard(SpellCard spellCard) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO cards (card_id, card_name, damage, element, card_type) VALUES (?, ?, ?, ?, 'Spell')")) {
            statement.setObject(1, spellCard.getId());
            statement.setString(2, spellCard.getCardName());
            statement.setInt(3, spellCard.getCardDamage());
            statement.setString(4, spellCard.getCardElement().toString());
            statement.executeUpdate();
        }
    }

    // Read
    public Card getCard(UUID cardId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM cards WHERE card_id = ?")) {
            statement.setObject(1, cardId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String cardType = resultSet.getString("card_type");
                    if ("Monster".equals(cardType)) {
                        return new MonsterCard(
                                UUID.fromString(resultSet.getString("card_id")),
                                resultSet.getString("card_name"),
                                resultSet.getInt("damage"),
                                Card.elementType.valueOf(resultSet.getString("element")),
                                MonsterCard.monsterType.valueOf(resultSet.getString("monster_type"))
                        );
                    } else if ("Spell".equals(cardType)) {
                        return new SpellCard(
                                UUID.fromString(resultSet.getString("card_id")),
                                resultSet.getString("card_name"),
                                resultSet.getInt("damage"),
                                Card.elementType.valueOf(resultSet.getString("element"))
                        );
                    }
                }
            }
        }
        return null;
    }

    public List<Card> getAllCards() throws SQLException {
        List<Card> cards = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM cards")) {
            while (resultSet.next()) {
                String cardType = resultSet.getString("card_type");
                if ("Monster".equals(cardType)) {
                    cards.add(new MonsterCard(
                            UUID.fromString(resultSet.getString("card_id")),
                            resultSet.getString("card_name"),
                            resultSet.getInt("damage"),
                            Card.elementType.valueOf(resultSet.getString("element")),
                            MonsterCard.monsterType.valueOf(resultSet.getString("monster_type"))
                    ));
                } else if ("Spell".equals(cardType)) {
                    cards.add(new SpellCard(
                            UUID.fromString(resultSet.getString("card_id")),
                            resultSet.getString("card_name"),
                            resultSet.getInt("damage"),
                            Card.elementType.valueOf(resultSet.getString("element"))
                    ));
                }
            }
        }
        return cards;
    }

    // Update
    public void updateCard(Card card) throws SQLException {
        if (card instanceof MonsterCard) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE cards SET card_name=?, damage=?, element=?, monster_type=? WHERE card_id=?")) {
                statement.setString(1, card.getCardName());
                statement.setInt(2, card.getCardDamage());
                statement.setString(3, card.getCardElement().toString());
                statement.setString(4, ((MonsterCard) card).getMonsterType().toString());
                statement.setObject(5, card.getId());
                statement.executeUpdate();
            }
        } else if (card instanceof SpellCard) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE cards SET card_name=?, damage=?, element=? WHERE card_id=?")) {
                statement.setString(1, card.getCardName());
                statement.setInt(2, card.getCardDamage());
                statement.setString(3, card.getCardElement().toString());
                statement.setObject(4, card.getId());
                statement.executeUpdate();
            }
        }
    }

    // Delete
    public void deleteCard(UUID cardId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM cards WHERE card_id=?")) {
            statement.setObject(1, cardId);
            statement.executeUpdate();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
