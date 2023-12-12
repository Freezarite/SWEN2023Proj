package baseClasses.Card;

public abstract class Card {

    public enum elementType {
        NORMAL,
        WATER,
        FIRE,
    }

    private elementType cardElement;

    private String cardName;

    private final int cardDamage;

    public Card(String name, int damage, elementType element) {
        this.cardElement = element;
        this.cardName = name;
        this.cardDamage = damage;
    }
}
