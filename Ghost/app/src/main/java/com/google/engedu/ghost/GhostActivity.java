/* Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static java.lang.Thread.sleep;


public class GhostActivity extends AppCompatActivity {
    private static final String TAG = "GhostActivity";

    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private static final String KEY_USER_TURN = "keyUserTurn";
    private static final String KEY_CURRENT_WORD = "keyCurrentWord";
    private static final String KEY_SAVED_STATUS = "keySavedStatus";

    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private String currentWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            // Initialize your dictionary from the InputStream.
            dictionary = new SimpleDictionary(inputStream);

        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
    }
        //onStart(null);
    public boolean reset(View view) {
        userTurn = random.nextBoolean();
        currentWord = "";
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }


    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        // checks if currentWord is a complete word

        // ghost words
        TextView text = (TextView) findViewById(R.id.ghostText);

        if(dictionary.isWord(currentWord)){
            text.setText(currentWord);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameOver();
        }
        else{

            // possible word
            String possibleWord = dictionary.getAnyWordStartingWith(currentWord);
            if(possibleWord == null){
                // user spelt wrongly

                text.setText(currentWord + " is not a valid word. You lost!");
            }
            else{
                String addLetter = possibleWord.substring(currentWord.length(),currentWord.length()+1);
                currentWord = currentWord + addLetter;
                text.setText(currentWord);
            }
            // if computer completes word
//            if(dictionary.isWord(currentWord)){
//
//                gameOver();
//            }

            // after computer turn
            userTurn = true;
            label.setText(USER_TURN);
            // make it implement user turn


        }

    }

    public void gameOver(){
        TextView textView = (TextView) findViewById(R.id.ghostText);
        textView.setText("GAME OVER");
    }
}