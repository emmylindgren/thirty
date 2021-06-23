package se.umu.emli.thirty.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.ThreadLocalRandom;

/** Dice model class. Keeps track of dice and which id, value and color belongs to that dice.
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class Dice implements Parcelable {
    private final int diceId;
    private int diceValue;
    private int diceColor;

    public Dice(int diceId, int diceValue) {
        this.diceId = diceId;
        diceColor= Constants.WHITE;
        this.diceValue = diceValue;
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
        if(!diceIsColored()){
            this.diceValue = ThreadLocalRandom.current().nextInt(1, 7);
        }
    }

    /**
     * Checks if the dice is colored. The dice is only colored if the color of the dice is
     * anything other than white.
     * @return boolean to tell if the dice is colored or not.
     */
    public boolean diceIsColored(){
        return diceColor != Constants.WHITE;
    }

    protected Dice(Parcel in) {
        diceId = in.readInt();
        diceValue = in.readInt();
        diceColor = in.readInt();
    }

    public static final Creator<Dice> CREATOR = new Creator<Dice>() {
        @Override
        public Dice createFromParcel(Parcel in) {
            return new Dice(in);
        }

        @Override
        public Dice[] newArray(int size) {
            return new Dice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(diceId);
        dest.writeInt(diceValue);
        dest.writeInt(diceColor);
    }


}
