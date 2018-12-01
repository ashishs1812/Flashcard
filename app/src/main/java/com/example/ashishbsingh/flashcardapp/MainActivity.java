package com.example.ashishbsingh.flashcardapp;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        findViewById(R.id.flashcard_question).setFocusable(true);
        findViewById(R.id.flashcard_answer).setFocusable(false);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {

                //(findViewById(R.id.flashcard_question)).setVisibility(View.INVISIBLE);
                // (findViewById(R.id.flashcard_answer)).setVisibility(View.VISIBLE);
                if (findViewById(R.id.flashcard_question).isFocusable() == true) {
                    View answerSideView = findViewById(R.id.flashcard_answer);
                    View questionSideView = findViewById(R.id.flashcard_question);

                    // get the center for the clipping circle
                    int cx = answerSideView.getWidth() / 2;
                    int cy = answerSideView.getHeight() / 2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(cx, cy);

                    // create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                    // hide the question and show the answer to prepare for playing the animation!
                    questionSideView.setVisibility(View.INVISIBLE);
                    answerSideView.setVisibility(View.VISIBLE);
                    findViewById(R.id.flashcard_question).setFocusable(false);
                    findViewById(R.id.flashcard_answer).setFocusable(true);
                    anim.setDuration(500);
                    anim.start();
                }
                else if(findViewById(R.id.flashcard_answer).isFocusable() == true) {
                    View answerSideView = findViewById(R.id.flashcard_answer);
                    View questionSideView = findViewById(R.id.flashcard_question);

                    // get the center for the clipping circle
                    int cx = answerSideView.getWidth() / 2;
                    int cy = answerSideView.getHeight() / 2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(cx, cy);

                    // create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(questionSideView, cx, cy, 0f, finalRadius);

                    // hide the question and show the answer to prepare for playing the animation!
                    questionSideView.setVisibility(View.VISIBLE);
                    answerSideView.setVisibility(View.INVISIBLE);
                    findViewById(R.id.flashcard_question).setFocusable(true);
                    findViewById(R.id.flashcard_answer).setFocusable(false);
                    anim.setDuration(500);
                    anim.start();


                    //(findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
                    //(findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);

                }
            }
        });
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 10);
                overridePendingTransition(R.anim.right_in, R.anim.left_out); // Screen animation change added
            }

        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex++;
                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                // Allows for wrapping from last card to first
                if (currentCardDisplayedIndex > allFlashcards.size() - 1 && ((TextView) findViewById(R.id.flashcard_question)).getText() != "Add a card!") {
                    currentCardDisplayedIndex = 0;
                }
                // If I wanted cards appear in random order
               // int rand = getRandomNumber(0, allFlashcards.size() - 1);
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex/*would be rand*/).getQuestion());
                        ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex/*would be rand*/).getAnswer());
                        (findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                        (findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
                        findViewById(R.id.flashcard_question).startAnimation(rightInAnim);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                // Sets the text boxes to selected flashcards and sets view to question
                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);

                //((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex/*would be rand*/).getQuestion());
                //((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex/*would be rand*/).getAnswer());
                //(findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                //(findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
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
                allFlashcards = flashcardDatabase.getAllCards();
                if(allFlashcards.size() == 0){
                    currentCardDisplayedIndex = 0;
                    (findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Add a card!");
                    ((TextView) findViewById(R.id.flashcard_answer)).setText("");
                }
                else if(allFlashcards.size() - 1 < currentCardDisplayedIndex) {
                    currentCardDisplayedIndex = 0;
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    (findViewById(R.id.flashcard_question)).setVisibility(View.VISIBLE);
                    (findViewById(R.id.flashcard_answer)).setVisibility(View.INVISIBLE);
                }
                else{
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




