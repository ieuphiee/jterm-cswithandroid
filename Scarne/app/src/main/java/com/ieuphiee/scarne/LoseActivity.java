package com.ieuphiee.scarne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        Intent intent = getIntent();
        String score = intent.getStringExtra(ScarneActivity.userScore);
        ((TextView) findViewById(R.id.score)).setText(String.format("You scored %d", score));

        ((Button) findViewById(R.id.playAgain)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
