package se.umu.emli.thirty.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

import se.umu.emli.thirty.R;

public class ScoreboardActivity extends AppCompatActivity {

    private HashMap<String, Integer> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Intent intent = getIntent();
        points = (HashMap<String, Integer>)intent.getSerializableExtra("points");

        setPoints();

        findViewById(R.id.play_again).setOnClickListener(v -> startAnotherRound());

    }

    private void setPoints(){
        int point;
        String round;
        StringBuilder builder = new StringBuilder();

        for(HashMap.Entry<String, Integer> pointRound : points.entrySet()) {
            point = pointRound.getValue();
            round = pointRound.getKey();
            builder.setLength(0);

            builder.append("score_");
            builder.append(round);
            String roundToBeUpdated = builder.toString();

            int id = getResources().getIdentifier(roundToBeUpdated, "id", this.getPackageName());

            TextView pointText = findViewById(id);
            pointText.setText(String.format(Locale.GERMAN,"%d",point));
        }
    }

    private void startAnotherRound(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}