package com.ieuphiee.scarne;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class ScarneActivity extends AppCompatActivity {
    /** User's overall score state */
    private int userTotal;
    /** User's turn score */
    private int userCurrent;
    /** Computer's overall score state */
    private int compTotal;
    /** Computer's turn score */
    private int compCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scarne);
    }

    /**
     * Randomly rolls dice
     */
    public void rollDice(View view) {
        Random random = new Random();
        int ranNumber = random.nextInt(6 - 1 + 1) + 1;
        ImageView diceContainer = (ImageView) findViewById(R.id.dice);
        TextView textContainer = (TextView) findViewById(R.id.score);
        Log.d("Testing", Integer.toString(ranNumber));
        if (ranNumber == 1) {
            diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
            userCurrent = 0;
        } else if (ranNumber == 2) {
            diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
            userCurrent += 2;
        } else if (ranNumber == 3) {
            diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
            userCurrent += 3;
        } else if (ranNumber == 4) {
            diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
            userCurrent += 4;
        } else if (ranNumber == 5) {
            diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
            userCurrent += 5;
        } else {
            diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
            userCurrent += 6;
        }
        //Log.d("Testing", Integer.toString(userCurrent));
        textContainer.setText("Your score (fill in) Computer score: (fill in)\nYour turn Score: "+userCurrent);
    }
}
