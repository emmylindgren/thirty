package se.umu.emli.thirty.model;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class DiceTest {

    Dice dice;

    @org.junit.Before
    public void setUp() throws Exception {
        dice = new Dice(1, 1);
    }

    /**
     * getDiceNr should return diceNr that was sent in.
     */

    @org.junit.Test
    public void getDiceNr() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 100);
        Dice randomDice = new Dice(randomNum, 1);

        assertEquals(1,dice.getDiceId());
        assertEquals(randomNum,randomDice.getDiceId());
    }

    /**
     * Dice value should return 0 when first created.
     */
    @org.junit.Test
    public void getDiceValue() {

       assertEquals(0,dice.getDiceValue());
    }

    /**
     * Dice color should be white when first created.
     */
    @org.junit.Test
    public void getDiceColor() {
        assertEquals(Constants.WHITE,dice.getDiceColor());
    }

    /**
     * Dice value should update when dice is rolled.
     */
    @org.junit.Test
    public void rollDice() {
        int before = dice.getDiceValue();
        dice.rollDice();
        assertNotEquals(before,dice.getDiceValue());
    }
}