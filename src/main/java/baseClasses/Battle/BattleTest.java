package baseClasses.Battle;

import baseClasses.User.User;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class BattleTest {
    @Test
    public void testEloCalculationOfEqualUsers() {
        //Arrange players
        User player1 = new User("Player1", 0, 0, 1000);
        User player2 = new User("Player2", 0, 0, 1000);
        Battle newBattle = new Battle(player1, player2);

        //Update elo
        try {
            Method updateElo = Battle.class.getDeclaredMethod("updateELOofUsers", int.class);
            updateElo.setAccessible(true);

            updateElo.invoke(newBattle, 1);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertEquals(player1.getUserElo(), 1010);
        assertEquals(player2.getUserElo(), 990);
    }

    @Test
    public void testEloCalculationsOfDifferentUsers() {
        //Arrange players
        User player1 = new User("Player1", 0, 0, 600);
        User player2 = new User("Player2", 0, 0, 300);
        Battle newBattle = new Battle(player1, player2);

        //Update elo
        try {
            Method updateElo = Battle.class.getDeclaredMethod("updateELOofUsers", int.class);
            updateElo.setAccessible(true);

            updateElo.invoke(newBattle, 2);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        assertEquals(player1.getUserElo(), 583);
        assertEquals(player2.getUserElo(), 317);
    }
}
