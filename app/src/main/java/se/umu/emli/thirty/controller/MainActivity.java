package se.umu.emli.thirty.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import se.umu.emli.thirty.R;
import se.umu.emli.thirty.model.ChosenColor;
import se.umu.emli.thirty.model.Constants;
import se.umu.emli.thirty.model.Dice;
import se.umu.emli.thirty.model.PointCounter;
import se.umu.emli.thirty.model.ThrowCounter;

/**
 * Controller CLASS -- Do not change make calculation here,
 * only control the model and UI
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Dice> diceBank;
    private ChosenColor chosenColor;
    private PointCounter pointCounter;

    private ThrowCounter throwCounter;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceBank = new ArrayList<>(Arrays.asList(
                new Dice(R.id.dice1),
                new Dice(R.id.dice2),
                new Dice(R.id.dice3),
                new Dice(R.id.dice4),
                new Dice(R.id.dice5),
                new Dice(R.id.dice6)
        ));

        setUpDiceListeners();
        setUpColorListeners();
        setUpSpinner();


        pointCounter = new PointCounter();
        throwCounter = new ThrowCounter(0);
        chosenColor = new ChosenColor();

        //Listener for throw dices button. If button is clicked @throwDices is called.
        findViewById(R.id.throw_dices).setOnClickListener(v -> throwDices());


        //Listener for the collect points button. If button is clicked @collectPoints is called.
        findViewById(R.id.collect_points).setOnClickListener(v -> collectPoints());

    }

    /**
     * Sets up spinner for choosing which round to be played.
     */
    private void setUpSpinner() {
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rounds_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // se mer om spinner och hur vi hämtar info från den: https://developer.android.com/guide/topics/ui/controls/spinner

    }

    /**
     * Sets up listeners for the color buttons. If they are clicked then that color is set to
     * be chosen.
     */
    private void setUpColorListeners() {
        findViewById(R.id.blue_button).setOnClickListener( v -> setChosenColor(R.id.blue_button,
                Constants.BLUE));
        findViewById(R.id.green_button).setOnClickListener( v -> setChosenColor(R.id.green_button,
                Constants.GREEN));
        findViewById(R.id.yellow_button).setOnClickListener( v -> setChosenColor(R.id.yellow_button,
                Constants.YELLOW));
        findViewById(R.id.red_button).setOnClickListener( v -> setChosenColor(R.id.red_button,
                Constants.RED));
        findViewById(R.id.purple_button).setOnClickListener( v -> setChosenColor(R.id.purple_button,
                Constants.PURPLE));
        findViewById(R.id.brown_button).setOnClickListener( v -> setChosenColor(R.id.brown_button,
                Constants.BROWN));
    }

    /**
     * Sets up listeners for the dices. If they are clicked then the dice is colored.
     */
    private void setUpDiceListeners() {
        findViewById(R.id.dice1).setOnClickListener(v -> setDiceColor(diceBank.get(0)));
        findViewById(R.id.dice2).setOnClickListener(v -> setDiceColor(diceBank.get(1)));
        findViewById(R.id.dice3).setOnClickListener(v -> setDiceColor(diceBank.get(2)));
        findViewById(R.id.dice4).setOnClickListener(v -> setDiceColor(diceBank.get(3)));
        findViewById(R.id.dice5).setOnClickListener(v -> setDiceColor(diceBank.get(4)));
        findViewById(R.id.dice6).setOnClickListener(v -> setDiceColor(diceBank.get(5)));
    }

    /**
     * Sets the chosen color to that of the users choice, and updates the button
     * with a checkmark.If an old color is chosen that button is unchecked.
     * @param buttonId, the button that represents the color.
     * @param color, the color to be set.
     */
    private void setChosenColor(int buttonId, int color){
        chosenColor.setChosenColor(color);
        chosenColor.setColorButtonId(buttonId);
        ImageButton newColor = findViewById(buttonId);
        newColor.setImageResource(R.drawable.check);

        if(chosenColor.hasOldColorButton()){
            ImageButton oldColorButton = findViewById(chosenColor.getOldColorButtonId());
            oldColorButton.setImageResource(android.R.color.transparent);
        }

        chosenColor.setOldColorButtonId(buttonId);
    }

    /**
     * Sets dice color to the chosen color of the moment.
     * If user tries to color dices before they have been thrown the dice is not colored.
     * @param dice, the dice which is to be colored.
     */
    private void setDiceColor(Dice dice){
        if(throwCounter.firstThrowsBeenMade()){
            dice.setDiceColor(chosenColor.getChosenColor());
            updateDice(dice);
        }
        else{
            Toast toast = Toast.makeText(this,"You have not thrown your" +
                    " dices yet! Cheater! ", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Throws the dices and updates image for dices accordingly.
     */
    private void throwDices(){

        if(throwCounter.throwsAreUp()){
            Toast toast = Toast.makeText(this,"You have exceeded" +
                    " your number of throws for this round. Collect your points!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            for(Dice dice : diceBank){
                dice.rollDice();
                updateDice(dice);
            }
        }
    }

    /**
     * Updates the dice image, value and color.
     * @param dice, the dice to be updated, with new value and color.
     */
    private void updateDice(Dice dice){
        Drawable newDiceImage = getNewDiceImage(dice);

        ImageButton image = findViewById(dice.getDiceId());
        image.setImageDrawable(newDiceImage);
    }

    /**
     * Gets the new image for the dice.
     * @param dice, the dice for which a new image is to be found.
     * @return a Drawable to be set for the dice.
     */
    private Drawable getNewDiceImage(Dice dice){
        int value = dice.getDiceValue();
        int color = dice.getDiceColor();
        String newDiceImageName = "";

        switch (value){
            case 1:
                newDiceImageName= updateDiceColor("one", color);
                break;
            case 2:
                newDiceImageName= updateDiceColor("two", color);
                break;
            case 3:
                newDiceImageName= updateDiceColor("three", color);
                break;
            case 4:
                newDiceImageName= updateDiceColor("four", color);
                break;
            case 5:
                newDiceImageName= updateDiceColor("five", color);
                break;
            case 6:
                newDiceImageName= updateDiceColor("six", color);
                break;
        }

        return ResourcesCompat.getDrawable(getResources(),
                getResources().getIdentifier(newDiceImageName,"drawable",
                        this.getPackageName()), null);
    }

    /**
     * Forms a string of value and color put together, which is the ID for the new image of the
     * dice.
     * @param value, a string which contains the value of the dice.
     * @param color, a int representing the color of the dice.
     * @return a string which represents the updated image for the dice.
     */
    private String updateDiceColor(String value, int color){

        StringBuilder builder = new StringBuilder();

        switch (color){
            case Constants.WHITE:
                break;
            case Constants.BLUE:
                builder.append("blue");
                break;
            case Constants.GREEN:
                builder.append("green");
                break;
            case Constants.YELLOW:
                builder.append("yellow");
                break;
            case Constants.RED:
                builder.append("red");
                break;
            case Constants.PURPLE:
                builder.append("purple");
                break;
            case Constants.BROWN:
                builder.append("brown");
                break;
            default:
                System.out.println("Something went wrong in colors.");
                break;
        }

        return builder.append(value).toString();
    }

    private void collectPoints(){
        // just nu om man klickar en gång till på räkna så blir round point = 0. bra men lägger ju
        //värdet i hashmapen i pointcounter. 
        //TODO: Fixa så man ej kan trycka så den räknar flera gånger för en omgång. Kanske fixas av->
        // TODO: Uppdatera spinnern så de alternativet ej finnns med.
        String text = spinner.getSelectedItem().toString();
        System.out.println(text);

        pointCounter.countRoundPoint(text, diceBank);
        updatePoints();
        throwCounter.resetThrows();
        clearDiceColors();
    }

    private void updatePoints(){
        TextView totalScore = findViewById(R.id.total_score);
        TextView roundScore = findViewById(R.id.round_score);

        totalScore.setText(pointCounter.getTotalPointsString());
        roundScore.setText(pointCounter.getLatestRoundPointsString());
    }

    private void clearDiceColors(){
        for (Dice dice: diceBank){
            dice.setDiceColor(Constants.WHITE);
        }
    }

}