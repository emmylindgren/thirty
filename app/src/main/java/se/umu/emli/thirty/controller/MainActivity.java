package se.umu.emli.thirty.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import se.umu.emli.thirty.R;
import se.umu.emli.thirty.model.ChosenColor;
import se.umu.emli.thirty.model.Constants;
import se.umu.emli.thirty.model.Dice;
import se.umu.emli.thirty.model.ThrowCounter;

/**
 * Controller CLASS -- Do not change make calculation here,
 * only control the model and UI
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Dice> diceBank;

   /* private ImageButton dice1;
    private ImageButton dice2;
    private ImageButton dice3;
    private ImageButton dice4;
    private ImageButton dice5;
    private ImageButton dice6;*/

    private ChosenColor chosenColor;

    private Button throwDiceButton;
    private Button collectPointsButton;
    private ThrowCounter throwCounter;


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

        chosenColor = new ChosenColor();
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


        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rounds_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // se mer om spinner och hur vi hämtar info från den: https://developer.android.com/guide/topics/ui/controls/spinner

        //Tar in en int för att underlätta vid rotation.
        throwCounter = new ThrowCounter(0);

        //Listener for throw dices button. If button is clicked @throwDices is called.
        throwDiceButton = findViewById(R.id.throw_dices);
        throwDiceButton.setOnClickListener(v -> throwDices());

        //Listener for the collect points button. If button is clicked @collectPoints is called.
        collectPointsButton=findViewById(R.id.collect_points);
        collectPointsButton.setOnClickListener(v -> collectPoints());

    }

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
        //nollställning av counter här, eller de får vi göra i points.
    }

}