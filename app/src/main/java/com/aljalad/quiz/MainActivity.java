package com.aljalad.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewHighScore;
    private Spinner spinnerDiffculity, spinnerCategory;

    private static  final int Request_Code = 1;
    private static  final String Shared_pref = "SHARED_PREF";
    private static  final String Key_HighScore = "KEY_HIGHSCORE";
    public static  final String Extra_Diffculity = "EXTRA_DIFFCULITY";
    public static  final String Extra_Category= "EXTRA_CATEGORY";

    private int highScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHighScore = findViewById(R.id.text_high_score);
        spinnerDiffculity = findViewById(R.id.spinner_difficulity);
        spinnerCategory = findViewById(R.id.spinner_category);

        loadSpinnerDiffculity();

        loadSpinnerCategory();

        loadHighScore();


    }


    public void start_quiz(View view) {

        String diffculity = spinnerDiffculity.getSelectedItem().toString();  // GET THE SELECTED ITEM IN THE SPINNER AND SET IT IN A STRING (diffculity)
        String category =   spinnerCategory.getSelectedItem().toString();  // GET THE SELECTED ITEM IN THE SPINNER AND SET IT IN A STRING (diffculity)


        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra(Extra_Diffculity, diffculity);
        intent.putExtra(Extra_Category, category);
        startActivityForResult(intent, Request_Code);


                   /*
                                          startActivityForResult()
                    THIS FUNCTION EXPECTS TO RECEIVE RESULT FEOM QUIZACTIVITY WITH A SPECIFIC Request_Code
                    */

    }




                      /*******************************************************************

                       THIS FUNCTION PROCESSES RESULT AFTER RECEIVING IT FROM QUIZACTIVITY

                      *******************************************************************/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Request_Code){
            if (resultCode == RESULT_OK){

               int score = data.getIntExtra(QuizActivity.Extra_Score,0);

               if (score > highScore){

                   updateHighScore(score);
               }
            }
        }
    }




                                           //loadHighScore()

                    /*******************************************************************

                     THIS FUNCTION IS TO GET THE RESULT SAVED IN SharedPreferences
                     AND SET IT IN textViewHighScore WHEN THE MainActivity OPENS

                    *******************************************************************/


    private void loadHighScore() {

        SharedPreferences preferences = getSharedPreferences(Shared_pref, MODE_PRIVATE);
        highScore = preferences.getInt(Key_HighScore, 0);
        textViewHighScore.setText("High Score: "+ highScore);

    }



    private void loadSpinnerDiffculity() {

        String[] diffculityLevels = Question.getALLDiffculityLevels();  //  THIS FUNCTION IS TO GET ALL Diffculity Levels AND SET IT IN Array Of String (diffculityLevels)
        ArrayAdapter<String> adapterDiffculity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, diffculityLevels);
        adapterDiffculity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiffculity.setAdapter(adapterDiffculity);   //SET ALL Diffculity Levels IN SPINNER

    }


    private void loadSpinnerCategory() {

        String[] category = Question.getALLCategories();  //  THIS FUNCTION IS TO GET ALL Diffculity Levels AND SET IT IN Array Of String (diffculityLevels)
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);   //SET ALL Diffculity Levels IN SPINNER

    }



                                         // updateHighScore()

                    /*******************************************************************

                     THIS FUNCTION IS TO SET THE RESULT RECEIVED FROM QUIZACTIVITY
                     (HIHG_SCORE) IN textViewHighScore AND SAVES IT IN SharedPreferences

                    *******************************************************************/


    private void updateHighScore(int newHighScore) {

        highScore = newHighScore;
        textViewHighScore.setText("High Score: " +highScore);

        SharedPreferences preferences = getSharedPreferences(Shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Key_HighScore, highScore);
        editor.apply();

    }
}
