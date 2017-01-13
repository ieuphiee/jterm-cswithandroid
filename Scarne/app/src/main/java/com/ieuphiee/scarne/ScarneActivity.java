package com.ieuphiee.scarne;

import android.content.Intent;
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
     * Displays you lost page
     */
    public static final String computerScore = "com.ieuphiee.scarne.compTotal";
    public void computerWins() {
        Intent intent = new Intent(this, LoseActivity.class);
        intent.putExtra(computerScore, String.valueOf(compTotal+compCurrent));
        startActivity(new Intent(this, LoseActivity.class));
        reset();
    }

    /**
     * Displays you win page
     */
    public static final String userScore = "com.ieuphiee.scarne.userTotal";
    public void playerWins() {
        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra(userScore, String.valueOf(userTotal+userCurrent));
        startActivity(intent);
        reset();
    }

    /**
     * Randomly rolls dice
     */
    public void rollDice(View view) {
        Random random = new Random();
        int ranNumber = random.nextInt(6 - 1 + 1) + 1;
        ImageView diceContainer = (ImageView) findViewById(R.id.dice);
        TextView textContainer = (TextView) findViewById(R.id.score);
        if (userTotal >= 20) {
            playerWins();
        } else {
            if (ranNumber == 1) {
                diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                diceContainer.setContentDescription("One");
                userCurrent = 0;
                hold();
                // User's turn ends, and everything that has been added up simply becomes 0
            } else if (ranNumber == 2) {
                diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                diceContainer.setContentDescription("Two");
                userCurrent += 2;
            } else if (ranNumber == 3) {
                diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                diceContainer.setContentDescription("Three");
                userCurrent += 3;
            } else if (ranNumber == 4) {
                diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                diceContainer.setContentDescription("Four");
                userCurrent += 4;
            } else if (ranNumber == 5) {
                diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                diceContainer.setContentDescription("Five");
                userCurrent += 5;
            } else {
                diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                diceContainer.setContentDescription("Six");
                userCurrent += 6;
            }
        }
        textContainer.setText("Your score "+userTotal+" Computer score: "+compTotal+"\nYour turn Score: "+userCurrent+" " +
                "Computer turn score "+compCurrent);
    }


    /**
     * Updates user's total score, resets user current, and updates label
     */
    public void hold() {
        userTotal+= userCurrent;
        userCurrent = 0;
        TextView textContainer = (TextView) findViewById(R.id.score);
        textContainer.setText("Your score "+userTotal+" Computer score: "+compTotal+"\nYour turn Score: "+userCurrent+" " +
                "Computer turn score "+compCurrent);

        computerTurn();
    }

    /**
     * Resets global variables to 0
     */
    public void reset() {
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
    public void computerTurn() {
        final Handler mHandler = new Handler();
        final Button roll = (Button) findViewById(R.id.roll);
        final Button hold = (Button) findViewById(R.id.hold);
        roll.setEnabled(false);
        hold.setEnabled(false);
        final Runnable mRunnable = new Runnable() {
            boolean okContinue = true;
            @Override
            public void run() {
                ImageView diceContainer = (ImageView) findViewById(R.id.dice);
                TextView textContainer = (TextView) findViewById(R.id.score);
                if (compTotal+compCurrent >= 20) {
                    computerWins();
                }
                if (okContinue && compCurrent < 20) {
                    Random random = new Random();
                    int ranNumber = random.nextInt(6 - 1 + 1) + 1;
                    if (ranNumber == 1) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                        diceContainer.setContentDescription("One");
                        okContinue = false;
                        compCurrent = 0;
                        roll.setEnabled(true);
                        hold.setEnabled(true);
                    } else if (ranNumber == 2) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                        diceContainer.setContentDescription("Two");
                        compCurrent += 2;
                    } else if (ranNumber == 3) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                        diceContainer.setContentDescription("Three");
                        compCurrent += 3;
                    } else if (ranNumber == 4) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                        diceContainer.setContentDescription("Four");
                        compCurrent += 4;
                    } else if (ranNumber == 5) {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                        diceContainer.setContentDescription("Five");
                        compCurrent += 5;
                    } else {
                        diceContainer.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                        diceContainer.setContentDescription("Six");
                        compCurrent += 6;
                    }
                    textContainer.setText("Your score " + userTotal + " Computer score: " + compTotal + "\nYour turn Score: " + userCurrent + " " +
                            "Computer turn score " + compCurrent);
                    mHandler.postDelayed(this, 1000);
                } else {
                    compTotal += compCurrent;
                    textContainer.setText("Your score " + userTotal + " Computer score: " + compTotal + "\nYour turn Score: " + userCurrent + " " +
                            "Computer turn score " + compCurrent);
                    compCurrent = 0;
                    roll.setEnabled(true);
                    hold.setEnabled(true);
                }
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }
}