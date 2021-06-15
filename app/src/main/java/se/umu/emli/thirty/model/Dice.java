package se.umu.emli.thirty.model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * MODEL CLASS -- Do not change UI elements here
 */
public class Dice {
    private final int diceNr;
    private int diceValue;
    private int diceColor;

    public Dice(int diceNr) {
        this.diceNr = diceNr;
        diceColor= Constants.WHITE;
    }

    public int getDiceNr(){ return diceNr;}

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
