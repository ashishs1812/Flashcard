package com.example.ashishbsingh.flashcardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                if((findViewById(R.id.flashcard_question)).hasFocusable())
                {
                    (findViewById(R.id.flashcard_question)).setVisibility(View.INVISIBLE);
                    (findViewById(R.id.flashcard_answer)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.flashcard_question)).setFocusable(false);
                }
                else{
                    (findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
                    (findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.flashcard_question)).setFocusable(true);

                }

            }
        });
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                    Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                    MainActivity.this.startActivityForResult(intent, 10);


                  }
            });

        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 10 && resultCode == RESULT_OK){
            String string1 = data.getExtras().getString("string1");
            String string2 = data.getExtras().getString("string2");

            ((TextView) findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(string2);
            }

        }





    }




