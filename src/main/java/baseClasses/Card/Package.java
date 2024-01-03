package baseClasses.Card;

import baseClasses.User.User;

import java.util.ArrayList;
import java.util.List;

public class Package {
    private final String packageName;
    private final List<Card> content;

    private final int price;

    public Package(String name, int price,List<Card> content) {
        this.packageName = name;
        this.content = content;
        this.price = price;
    }

    public void userBuysMe(User user) {

        if(user.getCoins() >= this.price) {
            //TODO: Make DB call to add content to Users collection;
            return;
        }
        System.out.println("User doesnt have enough Coins!");
    }

    public String getPackageName() {
        return this.packageName;
    }
}
