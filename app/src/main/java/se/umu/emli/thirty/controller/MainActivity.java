package se.umu.emli.thirty.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import se.umu.emli.thirty.R;
import se.umu.emli.thirty.model.ChosenColor;
import se.umu.emli.thirty.model.Constants;
import se.umu.emli.thirty.model.Dice;
import se.umu.emli.thirty.model.PointCounter;
import se.umu.emli.thirty.model.ThrowCounter;

/** Main activity controller class. Throws dices, collects and keeps track of scores.
 * Color a dice to lock it and to count it.
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Dice> diceBank;
    private ChosenColor chosenColor;
    private PointCounter pointCounter;

    private ThrowCounter throwCounter;
    private Spinner spinner;
    private int spinnerPos;
    private ArrayList<String> rounds;

    private boolean pointsAreCollected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            setUpNew();
        }
        else{
           setUpPrevious(savedInstanceState);
           savedInstanceState.clear();
        }

        setUpDiceListeners();
        setUpColorListeners();
        setUpSpinner();
        if(spinnerPos != 0){
            spinner.setSelection(spinnerPos);
        }

        findViewById(R.id.throw_dices).setOnClickListener(v -> throwDices());
        findViewById(R.id.collect_points).setOnClickListener(v -> collectPoints());
    }

    @Override
    protected void onSaveInstanceState (@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("Dices",diceBank);
        outState.putStringArrayList("Rounds",rounds);
        outState.putInt("SpinnerPos", spinner.getSelectedItemPosition());

        outState.putSerializable("Points", pointCounter.getAllPoints());
        outState.putInt("LatestPoint",pointCounter.getLatestRoundPointsInt());

        outState.putInt("NrOfThrows",throwCounter.getNrOfThrows());

        outState.putInt("ChosenColor",chosenColor.getChosenColor());
        outState.putInt("ChosenColorButton",chosenColor.getColorButtonId());

        outState.putBoolean("PointsAreCollected",pointsAreCollected);
    }

    /**
     * Sets up a new game.
     */
    private void setUpNew(){
        diceBank = new ArrayList<>(Arrays.asList(
                new Dice(R.id.dice1,1),
                new Dice(R.id.dice2,2),
                new Dice(R.id.dice3,3),
                new Dice(R.id.dice4,4),
                new Dice(R.id.dice5,5),
                new Dice(R.id.dice6,6)
        ));

        rounds = new ArrayList<>(Arrays.asList(
                "Low",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "11",
                "12"
        ));
        spinnerPos =0;

        pointCounter = new PointCounter();

        throwCounter = new ThrowCounter(0);

        chosenColor = new ChosenColor();

        pointsAreCollected = false;
    }

    /**
     * Sets up a game from Bundle.
     * @param savedInstanceState, the bundle containing game info.
     */
    private void setUpPrevious(Bundle savedInstanceState){

        diceBank = savedInstanceState.getParcelableArrayList("Dices");
        for(Dice dice : diceBank){
            updateDice(dice);
        }

        rounds = savedInstanceState.getStringArrayList("Rounds");
        spinnerPos= savedInstanceState.getInt("SpinnerPos");


        HashMap points = (HashMap) savedInstanceState.getSerializable("Points");
        int latestPoint = savedInstanceState.getInt("LatestPoint");
        pointCounter = new PointCounter(points,latestPoint);
        updatePointsInView();

        throwCounter = new ThrowCounter(savedInstanceState.getInt("NrOfThrows"));
        chosenColor = new ChosenColor(savedInstanceState.getInt("ChosenColor"),
                savedInstanceState.getInt("ChosenColorButton"));
        updateChosenColorButtons();

        pointsAreCollected = savedInstanceState.getBoolean("PointsAreCollected");

    }

    /**
     * Sets up spinner for choosing which round to be played.
     */
    private void setUpSpinner() {
        spinner = findViewById(R.id.spinner);
        makeSpinner();
    }

    /**
     * Makes a spinner out of the rounds arraylist.
     */
    private void makeSpinner(){
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,rounds);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
     * with a checkmark. If an old color is chosen that button is unchecked.
     * @param buttonId, the button that represents the color.
     * @param color, the color to be set.
     */
    private void setChosenColor(int buttonId, int color){
        chosenColor.setChosenColor(color);
        chosenColor.setColorButtonId(buttonId);
        updateChosenColorButtons();
    }

    private void updateChosenColorButtons(){
        if(chosenColor.getColorButtonId() != 0){
            ImageButton newColor = findViewById(chosenColor.getColorButtonId());
            newColor.setImageResource(R.drawable.check);

            if(chosenColor.hasOldColorButton()){
                ImageButton oldColorButton = findViewById(chosenColor.getOldColorButtonId());
                oldColorButton.setImageResource(android.R.color.transparent);
            }
            chosenColor.setOldColorButtonId(chosenColor.getColorButtonId());
        }
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
            Toast toast = Toast.makeText(this,R.string.cheater, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Throws the dices and updates image for dices accordingly.
     */
    private void throwDices(){

        if(throwCounter.throwsAreUp()){
            Toast toast = Toast.makeText(this,R.string.collect_your_points, Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            if(pointsAreCollected){
                clearDiceColors();
                pointsAreCollected = false;
            }
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

    /**
     * Collects the points of dices if the user has thrown the dices at least once.
     */
    private void collectPoints(){

        if(rounds.isEmpty()){
            goToScoreBoard();
        }
        else if(!throwCounter.firstThrowsBeenMade()){
            Toast toast = Toast.makeText(this,R.string.cheater, Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            String text = spinner.getSelectedItem().toString();

            pointCounter.countRoundPoint(text, diceBank);
            throwCounter.resetThrows();
            pointsAreCollected= true;
            rounds.remove(text);

            makeSpinner();
            updatePointsInView();
        }
    }

    /**
     * Updates the points in the view for the user to see. Also updates collect points button
     * to read "See scoreboard" if all rounds are played.
     */
    private void updatePointsInView(){
        TextView totalScore = findViewById(R.id.total_score);
        TextView roundScore = findViewById(R.id.round_score);

        totalScore.setText(pointCounter.getTotalPointsString());
        roundScore.setText(pointCounter.getLatestRoundPointsString());

        if(rounds.isEmpty()){
            Button collect = findViewById(R.id.collect_points);
            collect.setText(R.string.go_to_scoreboard);
        }
    }

    /**
     * Clears all dice colors.
     */
    private void clearDiceColors(){
        for (Dice dice: diceBank){
            dice.setDiceColor(Constants.WHITE);
        }
    }

    /**
     * Starts the ScoreBoard activity and sends it the points.
     */
    private void goToScoreBoard(){
        Intent intent = new Intent(this, ScoreboardActivity.class);
        intent.putExtra("points", pointCounter.getAllPoints());
        startActivity(intent);
    }
}