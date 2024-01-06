package BattleTest;

import baseClasses.Battle.Battle;
import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;
import baseClasses.User.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BattleTest {

    User player1;
    User player2;
    Battle newBattle;

    List<Card> userDeck1 = new ArrayList<>();

    @Before
    public void setup() {
        //create test Users and Battle instance
        player1 = new User("Player1", 0, 0, 500);
        player2 = new User("Player2", 0, 0, 500);
        newBattle = new Battle(player1, player2);

        //create Decks for testing purposes
        MonsterCard goblin = new MonsterCard(UUID.randomUUID(), "Goblin-Warrior", 10, Card.elementType.NORMAL, MonsterCard.monsterType.GOBLIN);
        MonsterCard dragon = new MonsterCard(UUID.randomUUID(), "Fire-Dragon", 15, Card.elementType.FIRE, MonsterCard.monsterType.DRAGON);
        SpellCard waveCrash = new SpellCard(UUID.randomUUID(), "Wave-Crash", 20, Card.elementType.WATER);

        List<Card> userDeck2 = new ArrayList<>();

        userDeck1.add(goblin);
        userDeck1.add(goblin);
        userDeck1.add(goblin);

        userDeck2.add(dragon);
        userDeck2.add(dragon);
        userDeck2.add(waveCrash);

        player1.editStack(userDeck1);
        player2.editStack(userDeck2);
    }

    @Test
    public void testEloCalculationOfEqualUsers() {

        //Calc ELO changes
        try {
            Method updateElo = Battle.class.getDeclaredMethod("updateELOofUsers", int.class);
            updateElo.setAccessible(true);

            updateElo.invoke(newBattle, 1);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertEquals(player1.getUserElo(), 490);
        assertEquals(player2.getUserElo(), 510);
    }

    @Test
    public void testEloCalculationsOfDifferentUsers() {
        //updates ELOs to make it different between the users
        player1.updateUserElo(100);
        player2.updateUserElo(-200);

        //Update elo
        try {
            Method updateElo = Battle.class.getDeclaredMethod("updateELOofUsers", int.class);
            updateElo.setAccessible(true);

            updateElo.invoke(newBattle, 2);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertEquals(583, player1.getUserElo());
        assertEquals(317, player2.getUserElo());
    }

    @Test
    public void testIfDrawIsPossible() {
        //change Deck of player2 so only a draw is possible
        player2.editStack(userDeck1);

        newBattle.commenceBattle();

        assertEquals(player1.getUserElo(), player2.getUserElo());
        assertTrue(newBattle.getBattleLog().contains("This battle ended in a Draw! ELOs have not been changed!"));
    }

    @Test
    public void checkIfUsersAreUpdatedCorrectly() {
        newBattle.commenceBattle();

        assertEquals(player1.getUserLosses(), 1);
        assertEquals(player2.getUserWins(), 1);

        //check if BattleLog is containing the winner as well
        assertTrue(newBattle.getBattleLog().contains(player2.getUsername() + " wins! ELOs have been changed accordingly!"));
    }
}
