package se.umu.emli.thirty.model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * MODEL CLASS -- Do not change UI elements here
 */
public class Dice {
    private final int diceId;
    private int diceValue;
    private int diceColor;

    public Dice(int diceId) {
        this.diceId = diceId;
        diceColor= Constants.WHITE;
    }

    public int getDiceId(){ return diceId;}

    public int getDiceValue(){return diceValue;}

    public int getDiceColor(){return diceColor;}

    //Only rolls uncolored dices, AKA white dices.
    public void rollDice(){
        if(diceIsNotColored()){
            this.diceValue = ThreadLocalRandom.current().nextInt(1, 7);
        }
    }

    public void colorDice(int diceColor){this.diceColor= diceColor;}

    private boolean diceIsNotColored(){
        if(diceColor== Constants.WHITE){
            return true;
        }
        else{
            return false;
        }
    }

}
