package baseClasses.Card;

import java.util.UUID;

public class SpellCard extends Card{
    public SpellCard(UUID id, String name, int damage, elementType element) {
        super(id, name, damage, element);
    }

    @Override
    public int getBattleDamage(Card enemyCard) {
        if (enemyCard instanceof MonsterCard enemyMonster) {
            if (enemyMonster.getMonsterType().equals(MonsterCard.monsterType.KRAKEN))
                return 0;
        }

        if(this.getCardElement().equals(enemyCard.getCardElement()))
            return this.getCardDamage();

        switch (this.getCardElement()) {
            case FIRE:
                if (enemyCard.getCardElement().equals(elementType.WATER))
                    return getCardDamage() / 2;
                if(enemyCard.getCardElement().equals(elementType.PLANT))
                    return getCardDamage() * 2;

            case PLANT:
                if (enemyCard.getCardElement().equals(elementType.FIRE))
                    return getCardDamage() / 2;
                if(enemyCard.getCardElement().equals(elementType.WATER))
                    return getCardDamage() * 2;

            case WATER:
                if(enemyCard.getCardElement().equals(elementType.PLANT))
                    return getCardDamage() / 2;
                if (enemyCard.getCardElement().equals(elementType.FIRE))
                    return getCardDamage() * 2;
        }
        return this.getCardDamage();
    }

    @Override
    public boolean beAttacked(Card enemy) {
        return false;
    }
}
