package DBTest;

import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;
import baseClasses.DB.CardDBHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CardDBHandlerTest {
    /*

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private CardDBHandler cardDBHandler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cardDBHandler = new CardDBHandler();
        connection = cardDBHandler.getConnection();
    }

    @Test
    public void addMonsterCardTest() throws SQLException {
        // Arrange
        MonsterCard monsterCard = new MonsterCard(UUID.randomUUID(), "Monster1", 10, Card.elementType.FIRE, MonsterCard.monsterType.KNIGHT);

        // Mock the behavior of connection.prepareStatement
        when(connection.prepareStatement(eq("INSERT INTO cards (card_id, card_name, damage, element, card_type, monster_type) VALUES (?, ?, ?, ?, 'Monster', ?)"))).thenReturn(preparedStatement);

        doNothing().when(preparedStatement).setObject(anyInt(), any());
        doNothing().when(preparedStatement).setString(anyInt(), any());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act
        cardDBHandler.addMonsterCard(monsterCard);

        // Assert
        // Use Mockito.verify to check if connection.prepareStatement is invoked with the correct SQL query.
        verify(connection, times(1)).prepareStatement(eq("INSERT INTO cards (card_id, card_name, damage, element, card_type, monster_type) VALUES (?, ?, ?, ?, 'Monster', ?)"));
        verify(preparedStatement, times(1)).setObject(anyInt(), any());
        verify(preparedStatement, times(1)).setString(anyInt(), any());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void addSpellCardTest() throws SQLException {
        SpellCard spellCard = new SpellCard(UUID.randomUUID(), "Spell1", 8, Card.elementType.WATER);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setObject(anyInt(), any());
        doNothing().when(preparedStatement).setString(anyInt(), any());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        cardDBHandler.addSpellCard(spellCard);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setObject(anyInt(), any());
        verify(preparedStatement, times(1)).setString(anyInt(), any());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void getCardTest() throws SQLException {
        UUID cardId = UUID.randomUUID();
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("card_type")).thenReturn("Monster");
        when(resultSet.getString("monster_type")).thenReturn("KNIGHT");

        Card card = cardDBHandler.getCard(cardId);

        assertEquals(MonsterCard.class, card.getClass());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setObject(anyInt(), any());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
    }

    @Test
    public void getAllCardsTest() throws SQLException {
        when(connection.createStatement()).thenReturn(mock(Statement.class));
        when(connection.createStatement().executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("card_type")).thenReturn("Monster");

        List<Card> cards = cardDBHandler.getAllCards();

        assertEquals(1, cards.size());
        assertEquals(MonsterCard.class, cards.get(0).getClass());

        verify(connection, times(1)).createStatement();
        verify(connection.createStatement(), times(1)).executeQuery(anyString());
        verify(resultSet, times(2)).next();
    }

    @Test
    public void updateCardTest() throws SQLException {
        MonsterCard monsterCard = new MonsterCard(UUID.randomUUID(), "Monster1", 10, Card.elementType.FIRE, MonsterCard.monsterType.KNIGHT);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(anyInt(), any());
        doNothing().when(preparedStatement).setObject(anyInt(), any());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        cardDBHandler.updateCard(monsterCard);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(anyInt(), any());
        verify(preparedStatement, times(1)).setObject(anyInt(), any());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void deleteCardTest() throws SQLException {
        UUID cardId = UUID.randomUUID();
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setObject(anyInt(), any());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        cardDBHandler.deleteCard(cardId);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setObject(anyInt(), any());
        verify(preparedStatement, times(1)).executeUpdate();
    }
    */
}
