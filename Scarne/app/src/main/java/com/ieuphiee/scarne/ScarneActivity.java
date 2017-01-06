package com.ieuphiee.scarne;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
            hold(view);
            // User's turn ends, and everything that has been added up simply becomes 0
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
        textContainer.setText("Your score "+userTotal+" Computer score: "+compTotal+"\nYour turn Score: "+userCurrent+" " +
                "Computer turn score "+compCurrent);    }

    /**
     * Updates user's total score, resets user current, and updates label
     */
    public void hold(View view) {
        userTotal+= userCurrent;
        userCurrent = 0;
        TextView textContainer = (TextView) findViewById(R.id.score);
        textContainer.setText("Your score "+userTotal+" Computer score: "+compTotal+"\nYour turn Score: "+userCurrent+" " +
                "Computer turn score "+compCurrent);

        computerTurn(view);
    }

    /**
     * Resets global variables to 0
     */
    public void reset(View view) {
        userTotal = 0;
        userCurrent = 0;
        compCurrent = 0;
        compTotal = 0;
        TextView textContainer = (TextView) findViewById(R.id.score);
        textContainer.setText("Your score "+userTotal+" Computer score: "+compTotal+"\nYour turn Score: "+userCurrent+" " +
                "Computer turn score "+compCurrent);    }

    /**
     * Helper method for computer's moves
     */
    public void computerTurn(View view) {
        final Handler mHandler = new Handler();
        final Button roll = (Button) findViewById(R.id.roll);
        final Button hold = (Button) findViewById(R.id.hold);
        roll.setEnabled(false);
        hold.setEnabled(false);
        final Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if (compCurrent < 20) {
                    Random random = new Random();
                    int ranNumber = random.nextInt(6 - 1 + 1) + 1;
                    ImageView diceContainer = (ImageView) findViewById(R.id.dice);
                    TextView textContainer = (TextView) findViewById(R.id.score);
                    if (ranNumber == 1) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                        compCurrent = 0;
                        roll.setEnabled(true);
                        hold.setEnabled(true);
                    } else if (ranNumber == 2) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                        compCurrent += 2;
                    } else if (ranNumber == 3) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                        compCurrent += 3;
                    } else if (ranNumber == 4) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                        compCurrent += 4;
                    } else if (ranNumber == 5) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                        compCurrent += 5;
                    } else {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                        compCurrent += 6;
                    }
                    textContainer.setText("Your score " + userTotal + " Computer score: " + compTotal + "\nYour turn Score: " + userCurrent + " " +
                            "Computer turn score " + compCurrent);
                    mHandler.postDelayed(this, 1000);
                } else {
                    roll.setEnabled(true);
                    hold.setEnabled(true);
                }
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }
}
