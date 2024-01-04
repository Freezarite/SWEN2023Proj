package baseClasses.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import baseClasses.Card.Card;


public class User {
    private String username;
    private String name;
    private List<Card> collection;
    private List<Card> stack;
    private boolean admin;
    private int userElo;
    private int userWins;
    private int userLosses;
    private int coins;

    public User(String username, int wins, int losses, int elo) {
        this.username = username;
        this.collection = new ArrayList<>();
        this.stack = new ArrayList<>();
        this.userWins = wins;
        this.userLosses = losses;
        this.userElo = elo;
        this.admin = false;
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
    public void editStack(List<Card> newStack) {
        this.stack = newStack;
        //TODO: add edit functionality with update in db
    }
    public int getCoins() {
        return this.coins;
    }
    public void spendCoins(int price) {
        this.coins -= price;
    }

    public void makeAdmin() {
        this.admin = true;
    }
    public boolean getAdminStatus() {
        return this.admin;
    }
    public void removeAdmin() {
        this.admin = false;
    }
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
}