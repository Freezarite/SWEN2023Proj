package CardTest;

import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpellCardTest {

    @Test
    public void testNoDamageToKraken() {
        SpellCard spell = new SpellCard(1, "FireSpell", 10, Card.elementType.FIRE);
        MonsterCard krakenMonster = new MonsterCard(2, "Kraken", 20, Card.elementType.WATER, MonsterCard.monsterType.KRAKEN);

        assertEquals(0, spell.getBattleDamage(krakenMonster));
    }

    @Test
    public void testFireElementAgainstWaterElement() {
        SpellCard fireSpell = new SpellCard(3, "FireSpell", 15, Card.elementType.FIRE);
        MonsterCard waterMonster = new MonsterCard(4, "WaterMonster", 10, Card.elementType.WATER, MonsterCard.monsterType.ORK);

        assertEquals(7, fireSpell.getBattleDamage(waterMonster));
    }

    @Test
    public void testFireElementAgainstPlantElement() {
        SpellCard fireSpell = new SpellCard(5, "FireSpell", 20, Card.elementType.FIRE);
        MonsterCard plantMonster = new MonsterCard(6, "PlantMonster", 12, Card.elementType.PLANT, MonsterCard.monsterType.GOBLIN);

        assertEquals(40, fireSpell.getBattleDamage(plantMonster));
    }

    @Test
    public void testPlantElementAgainstFireElement() {
        SpellCard plantSpell = new SpellCard(7, "PlantSpell", 18, Card.elementType.PLANT);
        MonsterCard fireMonster = new MonsterCard(8, "FireMonster", 25, Card.elementType.FIRE, MonsterCard.monsterType.DRAGON);

        assertEquals(9, plantSpell.getBattleDamage(fireMonster));
    }

    @Test
    public void testPlantElementAgainstWaterElement() {
        SpellCard plantSpell = new SpellCard(9, "PlantSpell", 22, Card.elementType.PLANT);
        MonsterCard waterMonster = new MonsterCard(10, "WaterMonster", 30, Card.elementType.WATER, MonsterCard.monsterType.ELVES);

        assertEquals(44, plantSpell.getBattleDamage(waterMonster));
    }

    @Test
    public void testWaterElementAgainstPlantElement() {
        SpellCard waterSpell = new SpellCard(11, "WaterSpell", 25, Card.elementType.WATER);
        MonsterCard plantMonster = new MonsterCard(12, "PlantMonster", 15, Card.elementType.PLANT, MonsterCard.monsterType.WIZZARD);

        assertEquals(12, waterSpell.getBattleDamage(plantMonster));
    }

    @Test
    public void testWaterElementAgainstFireElement() {
        SpellCard waterSpell = new SpellCard(13, "WaterSpell", 30, Card.elementType.WATER);
        MonsterCard fireMonster = new MonsterCard(14, "FireMonster", 18, Card.elementType.FIRE, MonsterCard.monsterType.GOBLIN);

        assertEquals(60, waterSpell.getBattleDamage(fireMonster));
    }

    @Test
    public void testEqualElementsNoEffect() {
        SpellCard fireSpell = new SpellCard(15, "FireSpell", 25, Card.elementType.FIRE);
        MonsterCard fireMonster = new MonsterCard(16, "FireMonster", 20, Card.elementType.FIRE, MonsterCard.monsterType.KNIGHT);

        assertEquals(25, fireSpell.getBattleDamage(fireMonster));
    }
}