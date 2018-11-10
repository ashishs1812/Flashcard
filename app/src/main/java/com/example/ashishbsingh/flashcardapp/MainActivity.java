package com.example.ashishbsingh.flashcardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase; // Allows for entire class to have access
    List<Flashcard> allFlashcards; // holds all flashcard objects
    int currentCardDisplayedIndex = 0;

    // returns a random number between minNumber and maxNumber, inclusive.
    // for example, if i called getRandomNumber(1, 3), there's an equal chance of it returning either 1, 2, or 3.
    // Not currently being used
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplication()); //If initialized before create, returns null
        allFlashcards = flashcardDatabase.getAllCards();

        // After first Flashcard is saved, we can see if the object went to the database or not
        if(allFlashcards != null && allFlashcards.size() > 0){
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

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

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex++;

                // Allows for wrapping from last card to first
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }
                // If I wanted cards appear in random order
               // int rand = getRandomNumber(0, allFlashcards.size() - 1);

                // Sets the text boxes to selected flashcards and sets view to question
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex/*would be rand*/).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex/*would be rand*/).getAnswer());
                (findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                (findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
            }



        });


        // When pressed, the trash button deletes the flashcard based on the question and sends user to next card
        // If list has 0 cards after delete, sets question to default text
        // If deleted card was last card in list, set count = 0 and set question to allFlashcards(0)
        // If neither previous conditions true, then count-- and set question to allFunctions(count--)
        findViewById(R.id.trash_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                flashcardDatabase.deleteCard(((TextView)findViewById(R.id.flashcard_question)).getText().toString());
                if(allFlashcards.size() == 0){
                    currentCardDisplayedIndex = 0;
                    allFlashcards = flashcardDatabase.getAllCards();
                    (findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Add a card!");
                }
                else if(allFlashcards.size() - 1 < currentCardDisplayedIndex) {
                    currentCardDisplayedIndex = 0;
                    allFlashcards = flashcardDatabase.getAllCards();
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    (findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
                }
                else{
                    allFlashcards = flashcardDatabase.getAllCards();
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    (findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
                }


            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            String string1 = data.getExtras().getString("string1");
            String string2 = data.getExtras().getString("string2");

            ((TextView) findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(string2);
            flashcardDatabase.insertCard(new Flashcard(string1 /*question*/, string2 /*answer*/));

        }


    }

    }




