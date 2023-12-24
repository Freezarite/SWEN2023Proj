package baseClasses.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import baseClasses.Card.Card;


public class User {
    private String username;
    private List<Card> collection;
    private List<Card> stack;

    private int userElo;
    private int userWins;
    private int userLosses;

    public User(String username, int wins, int losses, int elo) {
        this.username = username;
        this.collection = new ArrayList<>();
        this.stack = new ArrayList<>();
        this.userWins = wins;
        this.userLosses = losses;
        this.userElo = elo;
    }

    public List<Card> getStack() {
        return this.stack;
    }

    public String getUsername() {
        return this.username;
    }

    public void wins() {this.userWins++;}
    public void loses() {this.userLosses++;}

    public void updateUserElo(int amount) {this.userElo += amount;}

    public int getUserWins() {return this.userWins;}
    public int getUserLosses() {return this.userLosses;}
    public int getUserElo() {return this.userElo;}
    public void editStack() {
        //TODO: add edit functionality with update in db
    }
}
