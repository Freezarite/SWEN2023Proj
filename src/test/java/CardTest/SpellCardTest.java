package CardTest;

import baseClasses.Card.Card;
import baseClasses.Card.MonsterCard;
import baseClasses.Card.SpellCard;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class SpellCardTest {

    @Test
    public void testNoDamageToKraken() {
        SpellCard spell = new SpellCard(UUID.randomUUID(), "FireSpell", 10, Card.elementType.FIRE);
        MonsterCard krakenMonster = new MonsterCard(UUID.randomUUID(), "Kraken", 20, Card.elementType.WATER, MonsterCard.monsterType.KRAKEN);

        assertEquals(0, spell.getBattleDamage(krakenMonster));
    }

    @Test
    public void testFireElementAgainstWaterElement() {
        SpellCard fireSpell = new SpellCard(UUID.randomUUID(), "FireSpell", 15, Card.elementType.FIRE);
        MonsterCard waterMonster = new MonsterCard(UUID.randomUUID(), "WaterMonster", 10, Card.elementType.WATER, MonsterCard.monsterType.ORK);

        assertEquals(7, fireSpell.getBattleDamage(waterMonster));
    }

    @Test
    public void testFireElementAgainstPlantElement() {
        SpellCard fireSpell = new SpellCard(UUID.randomUUID(), "FireSpell", 20, Card.elementType.FIRE);
        MonsterCard plantMonster = new MonsterCard(UUID.randomUUID(), "PlantMonster", 12, Card.elementType.PLANT, MonsterCard.monsterType.GOBLIN);

        assertEquals(40, fireSpell.getBattleDamage(plantMonster));
    }

    @Test
    public void testPlantElementAgainstFireElement() {
        SpellCard plantSpell = new SpellCard(UUID.randomUUID(), "PlantSpell", 18, Card.elementType.PLANT);
        MonsterCard fireMonster = new MonsterCard(UUID.randomUUID(), "FireMonster", 25, Card.elementType.FIRE, MonsterCard.monsterType.DRAGON);

        assertEquals(9, plantSpell.getBattleDamage(fireMonster));
    }

    @Test
    public void testPlantElementAgainstWaterElement() {
        SpellCard plantSpell = new SpellCard(UUID.randomUUID(), "PlantSpell", 22, Card.elementType.PLANT);
        MonsterCard waterMonster = new MonsterCard(UUID.randomUUID(), "WaterMonster", 30, Card.elementType.WATER, MonsterCard.monsterType.ELVES);

        assertEquals(44, plantSpell.getBattleDamage(waterMonster));
    }

    @Test
    public void testWaterElementAgainstPlantElement() {
        SpellCard waterSpell = new SpellCard(UUID.randomUUID(), "WaterSpell", 25, Card.elementType.WATER);
        MonsterCard plantMonster = new MonsterCard(UUID.randomUUID(), "PlantMonster", 15, Card.elementType.PLANT, MonsterCard.monsterType.WIZZARD);

        assertEquals(12, waterSpell.getBattleDamage(plantMonster));
    }

    @Test
    public void testWaterElementAgainstFireElement() {
        SpellCard waterSpell = new SpellCard(UUID.randomUUID(), "WaterSpell", 30, Card.elementType.WATER);
        MonsterCard fireMonster = new MonsterCard(UUID.randomUUID(), "FireMonster", 18, Card.elementType.FIRE, MonsterCard.monsterType.GOBLIN);

        assertEquals(60, waterSpell.getBattleDamage(fireMonster));
    }

    @Test
    public void testEqualElementsNoEffect() {
        SpellCard fireSpell = new SpellCard(UUID.randomUUID(), "FireSpell", 25, Card.elementType.FIRE);
        MonsterCard fireMonster = new MonsterCard(UUID.randomUUID(), "FireMonster", 20, Card.elementType.FIRE, MonsterCard.monsterType.KNIGHT);

        assertEquals(25, fireSpell.getBattleDamage(fireMonster));
    }
}