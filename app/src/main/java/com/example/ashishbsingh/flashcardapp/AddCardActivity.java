package com.example.ashishbsingh.flashcardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);



    findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View b){
            Intent data = new Intent();
            data.putExtra("string1", ((EditText)findViewById(R.id.questionBox)).getText().toString());
            data.putExtra("string2", ((EditText)findViewById(R.id.answerBox)).getText().toString());
            setResult(RESULT_OK, data);
            finish();

        }

    });
        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View b) {
               finish();



            }
        });

    }



    }

