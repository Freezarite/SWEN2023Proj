package baseClasses.Battle;

import baseClasses.DB.UserDBHandler;
import baseClasses.Server.SessionHandler;
import baseClasses.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BattleHandler {
    private static BattleHandler instance;
    private User user1;
    private User user2;
    private boolean userAlreadyWaiting;
    private final Lock lock = new ReentrantLock();

    private UserDBHandler userDBHandler = new UserDBHandler();

    Battle battle = new Battle(null, null);

    public BattleHandler() {
    }

    public String createOrJoinBattle(String username) throws InterruptedException {

        boolean locked = false;
        while(!locked)
            locked = lock.tryLock();

        if(user1 == null) {
            user1 = userDBHandler.getUserInstanceFromDB(username);
            battle.addUser1(user1);
            lock.unlock();
            wait();
            userDBHandler.updateUserBasedOnInstance(user1, username);
            lock.unlock();
            return this.battle.getBattleLog();
        }

        battle.addUser2(user2);
        battle.commenceBattle();
        userDBHandler.updateUserBasedOnInstance(user2, username);
        notifyAll();
        return this.battle.getBattleLog();

    }

    public static BattleHandler getInstance() {
        if(instance == null) {
            instance = new BattleHandler();
        }
        return instance;
    }
}

