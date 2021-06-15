package se.umu.emli.thirty.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import se.umu.emli.thirty.R;
import se.umu.emli.thirty.model.Dice;
import se.umu.emli.thirty.model.ThrowCounter;

/**
 * Controller CLASS -- Do not change make calculation here,
 * only control the model and UI
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Dice> diceBank = new ArrayList<>(Arrays.asList(
            new Dice(1),
            new Dice(2),
            new Dice(3),
            new Dice(4),
            new Dice(5),
            new Dice(6)
    ));

    private Button throwDiceButton;
    private Button collectPointsButton;
    private ThrowCounter throwCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void throwDices(){

        if(throwCounter.throwsAreUp()){
            Toast toast = Toast.makeText(this,"You have exceeded" +
                    " your number of throws for this round. Collect your points!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            //varje tärning ska rullas. för varje i dicebank --> rulla & uppdatera bilderna = egen metod.
            diceBank.get(1).rollDice();

            // detta eventuellt kan få vara i throwcounter när man kollar om de är slut så kan
            //man uppa?
            throwCounter.upNrOfThrows();
        }
    }

    private void collectPoints(){
        //nollställning av counter här, eller de får vi göra i points.
    }

}