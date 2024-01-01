package baseClasses.Card;

public abstract class Card {

    public enum elementType {
        NORMAL,
        WATER,
        FIRE,
        PLANT,
    }

    private final int id;

    private final elementType cardElement;

    private final String cardName;

    private final int cardDamage;

    public elementType getCardElement() {
        return this.cardElement;
    }

    public int getCardDamage()
    {
        return this.cardDamage;
    }

    //base classes meant to be overwritten
    public int getBattleDamage(Card enemy) {
        return 0;
    }

    public boolean beAttacked(Card enemy) {
        return true;
    }

    public Card(int id, String name, int damage, elementType element) {
        this.id = id;
        this.cardElement = element;
        this.cardName = name;
        this.cardDamage = damage;
    }

    public String getCardName() {
        return this.cardName;
    }

    public int getId() {
        return this.id;
    }
}

