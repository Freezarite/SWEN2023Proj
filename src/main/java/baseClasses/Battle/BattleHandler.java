package baseClasses.Battle;

import baseClasses.User.User;

import java.util.ArrayList;
import java.util.List;

public class BattleHandler {
    private static volatile BattleHandler instance;
    private User waitingUser;
    private List<Battle> runningBattles;
    private boolean userAlreadyWaiting;

    public BattleHandler() {
        runningBattles = new ArrayList<>();
        waitingUser = null;
        userAlreadyWaiting = false;
    }

    public void createOrJoinBattle(User newUser) {

        if(!userAlreadyWaiting) {
            this.waitingUser = newUser;
            this.userAlreadyWaiting = true;
        }

        Battle newBattle = new Battle(waitingUser, newUser);
        runningBattles.add(newBattle);
    }

    public static BattleHandler getInstance() {
        if(instance != null)
            return instance;

        synchronized (BattleHandler.class) {
            if(instance == null)
                instance = new BattleHandler();
        }

        return instance;
    }
}
