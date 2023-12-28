package baseClasses.Card;

public class MonsterCard extends Card{

    public enum monsterType {
        KNIGHT,
        GOBLIN,
        DRAGON,
        ELVES,
        WIZZARD,
        ORK,
        KRAKEN,
    }

    private final monsterType typ;

    public monsterType getMonsterType() {
        return typ;
    }

    public MonsterCard(int id,String name, int damage, elementType element, monsterType mTyp) {
        super(id, name, damage, element);
        typ = mTyp;
    }

    @Override
    public boolean beAttacked(Card attacker) {
        //this function returns true if an attack is an instant kill, false when it is not

        if(attacker instanceof MonsterCard attackerMonster) {
            switch (attackerMonster.getMonsterType())
            {

                case ORK:
                    if (typ.equals(monsterType.WIZZARD)) return true;
                    return false;

                case ELVES:
                    if (!typ.equals(monsterType.DRAGON))
                        if (this.getCardElement().equals(attackerMonster.getCardElement()))
                            return true;

                    return false;

                case GOBLIN:
                    return false;

                case DRAGON:

                    if(typ == monsterType.GOBLIN) {
                        return true;
                    }

                    return false;
            }
        }

        if (attacker instanceof SpellCard attackerSpell) {
            if (typ.equals(monsterType.KNIGHT) && attackerSpell.getCardElement().equals(elementType.WATER)) return true;

        }

        return false;
    }

    @Override
    public int getBattleDamage(Card enemyCard) {
        return this.getCardDamage();
    }

}
