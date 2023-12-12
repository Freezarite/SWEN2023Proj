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

    public User(String username) {
        this.username = username;
        this.collection = new ArrayList<>();
        this.stack = new ArrayList<>();
    }

    public void editStack() {
        //TODO: add edit functionality with update in db
    }
}
