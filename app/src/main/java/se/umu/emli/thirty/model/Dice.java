package se.umu.emli.thirty.model;

import java.util.concurrent.ThreadLocalRandom;

/** Dice model class. Keeps track of dice and which id, value and color belongs to that dice.
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class Dice {
    private final int diceId;
    private int diceValue;
    private int diceColor;

    public Dice(int diceId) {
        this.diceId = diceId;
        diceColor= Constants.WHITE;
    }

    public int getDiceId(){return diceId;}

    public int getDiceValue(){return diceValue;}

    public int getDiceColor(){return diceColor;}

    /**
     * Sets the dice color.
     * @param color which to be set.
     */
    public void setDiceColor(int color){
        if(color == diceColor){
            diceColor= Constants.WHITE;
        }
        else{
            diceColor=color;
        }
    }

    /**
     * Rolls the dice if and only if the dice is colored white, AKA uncolored.
     */
    public void rollDice(){
        if(diceIsNotColored()){
            this.diceValue = ThreadLocalRandom.current().nextInt(1, 7);
        }
    }

    /**
     * Checks if the dice is colored. The dice is only colored if the color of the dice is
     * anything other than white.
     * @return boolean to tell if the dice is colored or not.
     */
    private boolean diceIsNotColored(){
        return diceColor == Constants.WHITE;
    }

}
