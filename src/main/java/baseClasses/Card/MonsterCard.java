package baseClasses.Card;

public class MonsterCard extends Card{

    public enum monsterType {
        KNIGHT,
        GOBLIN,
        DRAGON,
        ELVES,
        WIZZARD,
        ORK,
    }

    private monsterType monsterType;

    public MonsterCard(String name, int damage, elementType element, monsterType monsterType) {
        super(name, damage, element);
        this.monsterType = monsterType;
    }
}
