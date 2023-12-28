import baseClasses.Battle.Battle;
import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;
import baseClasses.User.User;

import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String args[]) {

        MonsterCard goblin = new MonsterCard(1, "Goblin-Warrior", 10, Card.elementType.NORMAL, MonsterCard.monsterType.GOBLIN);
        MonsterCard dragon = new MonsterCard(2, "Fire-Dragon", 15, Card.elementType.FIRE, MonsterCard.monsterType.DRAGON);
        SpellCard waveCrash = new SpellCard(3, "Wave-Crash", 20, Card.elementType.WATER);

        List<Card> userDeck1 = new ArrayList<>();
        List<Card> userDeck2 = new ArrayList<>();

        userDeck1.add(goblin);
        userDeck1.add(goblin);
        userDeck1.add(goblin);

        userDeck2.add(dragon);
        userDeck2.add(dragon);
        userDeck2.add(waveCrash);

        User user1 = new User("Player1", 0, 0, 600);
        User user2 = new User("Player2", 0,0, 300);

        user1.editStack(userDeck1);
        user2.editStack(userDeck2);

        Battle newBattle = new Battle(user1, user2);

        newBattle.commenceBattle();
        user1 = newBattle.returnPlayer1();
        user2 = newBattle.returnPlayer2();
        System.out.println(user1.getUserElo());
        System.out.println(user2.getUserElo());
    }
}
