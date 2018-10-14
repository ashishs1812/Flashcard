package com.example.ashishbsingh.flashcardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                (findViewById(R.id.flashcard_question)).setVisibility(View.INVISIBLE);
                (findViewById(R.id.flashcard_answer)).setVisibility(View.VISIBLE);

            }
        });




    }
}