package CardTest;

import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;
import org.junit.Test;
import static org.junit.Assert.*;

public class MonsterCardTest {

    @Test
    public void testInstantKillOrkVsWizzard() {
        MonsterCard orkMonster = new MonsterCard(1, "Ork", 10, Card.elementType.FIRE, MonsterCard.monsterType.ORK);
        MonsterCard wizzardMonster = new MonsterCard(2, "Wizzard", 8, Card.elementType.FIRE, MonsterCard.monsterType.WIZZARD);

        assertTrue(wizzardMonster.beAttacked(orkMonster));
    }

    @Test
    public void testElementalAdvantageElvesVsMatchingElementDragon() {
        MonsterCard elvesMonster = new MonsterCard(3, "Elves", 12, Card.elementType.FIRE, MonsterCard.monsterType.ELVES);
        MonsterCard elementDragon = new MonsterCard(4, "MatchingElementDragon", 15, Card.elementType.FIRE, MonsterCard.monsterType.DRAGON);

        assertTrue(elementDragon.beAttacked(elvesMonster));
    }

    @Test
    public void testElementalAdvantageElvesVsNonMatchingElementDragon() {
        MonsterCard elvesMonster = new MonsterCard(5, "Elves", 15, Card.elementType.WATER, MonsterCard.monsterType.ELVES);
        MonsterCard nonMatchingElementDragonMonster = new MonsterCard(6, "NonMatchingElementDragon", 10, Card.elementType.FIRE, MonsterCard.monsterType.DRAGON);

        assertFalse(elvesMonster.beAttacked(nonMatchingElementDragonMonster));
    }

    @Test
    public void testGoblinVsAnyMonster() {
        MonsterCard goblinMonster = new MonsterCard(7, "Goblin", 18, Card.elementType.FIRE, MonsterCard.monsterType.GOBLIN);
        MonsterCard anyMonster = new MonsterCard(8, "AnyMonster", 10, Card.elementType.WATER, MonsterCard.monsterType.ORK);

        assertFalse(goblinMonster.beAttacked(anyMonster));
    }

    @Test
    public void testDragonVsGoblinSpecialCase() {
        MonsterCard dragonMonster = new MonsterCard(9, "Dragon", 20, Card.elementType.FIRE, MonsterCard.monsterType.DRAGON);
        MonsterCard goblinMonster = new MonsterCard(10, "Goblin", 15, Card.elementType.FIRE, MonsterCard.monsterType.GOBLIN);

        assertTrue(goblinMonster.beAttacked(dragonMonster));
    }

    @Test
    public void testKnightVsWaterSpellSpecialCase() {
        MonsterCard knightMonster = new MonsterCard(11, "Knight", 22, Card.elementType.FIRE, MonsterCard.monsterType.KNIGHT);
        SpellCard waterSpell = new SpellCard(12, "WaterSpell", 10, Card.elementType.WATER);

        assertTrue(knightMonster.beAttacked(waterSpell));
    }

    @Test
    public void testNonMonsterAttackerShouldAlwaysReturnFalse() {
        MonsterCard monster = new MonsterCard(13, "Monster", 15, Card.elementType.PLANT, MonsterCard.monsterType.DRAGON);
        SpellCard spell = new SpellCard(14, "Spell", 10, Card.elementType.FIRE);

        assertFalse(monster.beAttacked(spell));
    }

    @Test
    public void testBattleDamageCalculation() {
        MonsterCard monster1 = new MonsterCard(15, "Monster1", 25, Card.elementType.WATER, MonsterCard.monsterType.KRAKEN);
        MonsterCard monster2 = new MonsterCard(16, "Monster2", 18, Card.elementType.FIRE, MonsterCard.monsterType.ELVES);

        assertEquals(monster1.getBattleDamage(monster2), monster1.getCardDamage());
    }
}

