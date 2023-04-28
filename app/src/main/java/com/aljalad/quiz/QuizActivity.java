package com.aljalad.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion,textViewscore,textViewQuestioncount,textViewcountdown;//textViewDiffculity, textViewCategory;
    private RadioGroup radioGroup;
    private RadioButton radioButton1,radioButton2,radioButton3;
    private Button buttonConfirm;

    private static final long countdown_In_MilliS = 60000;
    public static final String Extra_Score = "EXTRA_SCORE";
    private  int questionCounter,questionCountTotal,score;
    private  boolean answered;
    private long backPressedTime,timeLeft_In_MilliS;

    private  Question currentQuestion;
    private ColorStateList textcolordefault_Radiobutton,textcolordefault_Countdown;
    private List<Question> questionList;
    private CountDownTimer countDownTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);



          initalization_fun();             //    THIS FUNCTION IS TO INITIALIZE ALL COMPONENTS IN QuizActivity



        Intent intent = getIntent();
        String diffculity = intent.getStringExtra(MainActivity.Extra_Diffculity);  //  THIS FUNCTION IS TO GET THE Diffculity OF THE QUIZ WHICH SELECTED IN THE SPINNER AND SET IT IN A STRING
        String category =   intent.getStringExtra(MainActivity.Extra_Category);


         //textViewDiffculity.setText("Diffculity: " + diffculity);                  //  THIS FUNCTION IS TO SET THE Diffculity OF QUIZ textViewDiffculity
        //  textViewCategory.setText("Category: "+category);


        textcolordefault_Radiobutton = radioButton1.getTextColors();        //   THIS FUNCTION IS TO GET DEFAULT TEXT COLOR OF radioButton1
        textcolordefault_Countdown = textViewcountdown.getTextColors();     //   THIS FUNCTION IS TO GET DEFAULT TEXT COLOR OF textViewcountdown

        QuizdbHelber quizdbHelber = new QuizdbHelber(this);

        questionList = quizdbHelber.getQuestions(diffculity, category);                    //   THIS FUNCTION IS TO GET ALL INSIDE DATABASE AND SET IT IN questionList

        questionCountTotal = questionList.size();                        //   THIS FUNCTION IS TO GET THE QUESTIONS NUMBER IN questionList
        Collections.shuffle(questionList);                              //   THIS FUNCTION IS TO GET RANDOM QUESTIONS FROM questionList


        showNextQuestion();            // THIS FUNCTION IS TO INITIALIZE ALL (QUESTIONS AND OPTIONS) AT THE BEGINNING OF THIS ACTIVITY


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                       // DO THIS OPERATIONS IF  buttonConfirm IS CLICKED

            if (!answered){                         // DO THIS OPERATION IF ANSWERED = FALSE


                if (radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked()){     // DO THIS OPERATION IF ANY BUTTON IS SELECTED


                    checkhAnswer();     // THIS FUNCTION IS TO CHECK OF THE ANSWER


                }else {                           // DO THIS OPERATION IF ANY BUTTON IS NOT SELECTED

                    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();

                }

            }else {

                showNextQuestion();              // DO THIS OPERATION IF ANSWERED = TRUE

            } }

        });
    }




                                      //initalization_fun()

                 /*******************************************************************

                      THIS FUNCTION IS TO INITIALIZE ALL COMPONENTS IN QuizActivity

                *******************************************************************/

    private void initalization_fun() {

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewscore = findViewById(R.id.text_view_score);
        textViewQuestioncount = findViewById(R.id.text_view_question_count);
        textViewcountdown = findViewById(R.id.text_view_countdown);
        radioGroup = findViewById(R.id.radio_group);
        radioButton1 = findViewById(R.id.radio_button1);
        radioButton2 = findViewById(R.id.radio_button2);
        radioButton3 = findViewById(R.id.radio_button3);
        buttonConfirm = findViewById(R.id.button_confirm);
        //textViewDiffculity = findViewById(R.id.text_view_diffculity);
        //textViewCategory =   findViewById(R.id.text_view_category);
    }



                                                  // showNextQuestion()

                             /**********************************************************************

                                   // THIS FUNCTION IS TO INITIALIZE ALL (QUESTIONS AND OPTIONS)

                             **********************************************************************/
    private void showNextQuestion() {

        radioButton1.setTextColor(textcolordefault_Radiobutton);       //   THIS FUNCTION IS TO SET DEFAULT TEXT COLOR OF radioButton1
        radioButton2.setTextColor(textcolordefault_Radiobutton);       //   THIS FUNCTION IS TO SET DEFAULT TEXT COLOR OF radioButton2
        radioButton3.setTextColor(textcolordefault_Radiobutton);       //   THIS FUNCTION IS TO SET DEFAULT TEXT COLOR OF radioButton3
        textViewcountdown.setTextColor(textcolordefault_Countdown);    //   THIS FUNCTION IS TO SET DEFAULT TEXT COLOR OF textViewcountdown

        radioGroup.clearCheck();                                       //   THIS FUNCTION IS TO CLEAR THE CHECK ANY BUTTON

        if (questionCounter < questionCountTotal){

            currentQuestion = questionList.get(questionCounter);       /*  THIS FUNCTION IS TO GET ALL INSIDE questionList AT A SPECIFIC INDEX (questionCounter)
                                                                           AND SET IN currentQuestion .
                                                                         questionCounter STARTS FROM 0 UNTIL THE TOTAL NUMBER OF QUESTIONS IN questionList  */


            textViewQuestion.setText(currentQuestion.getQuestion());      //   THIS FUNCTION IS TO GET QUESTION IN currentQuestion AT THAT SPECIFIC INDEX
            radioButton1.setText(currentQuestion.getOption1());          //   THIS FUNCTION IS TO GET OPTION1 IN currentQuestion   AT THAT SPECIFIC INDEX
            radioButton2.setText(currentQuestion.getOption2());          //   THIS FUNCTION IS TO GET OPTION2 IN currentQuestion   AT THAT SPECIFIC INDEX
            radioButton3.setText(currentQuestion.getOption3());         //   THIS FUNCTION IS TO GET OPTION3 IN currentQuestion   AT THAT SPECIFIC INDEX

            questionCounter++;                                          // INCREASE  questionCounter BY 1

            textViewQuestioncount.setText("Question :"+questionCounter + "/" + questionCountTotal);   // questionCounter / questionCountTotal

            answered = false;                                         // ANSWERED = FALSE
            buttonConfirm.setText("Confirm");

            timeLeft_In_MilliS = countdown_In_MilliS;               //timeLeft_In_MilliS = 30000


            startCountDown();                                      // THIS FUNCTION IS TO START COUNTER


        }else {

            finishQuiz();                                        // THIS FUNCTION IS TO EXIT AFTER COMPLETING THE QUIZ


        }
    }



    private void startCountDown(){       // THIS FUNCTION IS TO START COUNTER

            countDownTimer = new CountDownTimer(timeLeft_In_MilliS, 1000) {



                                                 // onTick()

                         /*******************************************************************

                         THIS FUNCTION PROCESSES COUNTDOWN WHEN THE COUNTER IS RUNNING

                        *******************************************************************/

            @Override
            public void onTick(long millisUntilFinished) {

                timeLeft_In_MilliS = millisUntilFinished;

                int minutes = (int) (timeLeft_In_MilliS / 1000) / 60;               // (60000 / 1000) / 60 = 60 / 60 = 1
                int seconds = (int) (timeLeft_In_MilliS / 1000) % 60;              // (60000 / 1000) % 60 = 60 % 60 = 0

                String timeFormated = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);   // 00:30
                textViewcountdown.setText(timeFormated);                          // SET 00:30 IN textViewcountdown

                if (timeLeft_In_MilliS < 10000){

                    textViewcountdown.setTextColor(Color.RED);                   //THIS FUNCTION IS TO SET COLOR OF textViewcountdown RED WHEN COUNTER BECOMES BELOW 10

                } else {

                    textViewcountdown.setTextColor(textcolordefault_Countdown); //THIS FUNCTION IS TO SET COLOR OF textViewcountdown DEFAULT WHEN COUNTER BECOMES ABOVE 10


                }
            }


                                                  // onFinish()

                          /*******************************************************************

                           THIS FUNCTION PROCESSES COUNTDOWN WHEN THE COUNTER IS STOPPING

                         *******************************************************************/

            @Override
            public void onFinish() {

                checkhAnswer();

            }
        }.start();

    }


    private void checkhAnswer() {

        answered = true;                      // ANSWERED = TRUE

        countDownTimer.cancel();               // STOP THE COUNTER

        RadioButton rB_Selected = findViewById(radioGroup.getCheckedRadioButtonId());    // FIND ID FOR THE CHECKED BUTTON AND SET IN rB_Selected
        int answer_Nr = radioGroup.indexOfChild(rB_Selected) + 1;                        // FIND INDEX FOR CHECKED BUTTON THIS ID + 1 AND SET IN answer_Nr

        if (answer_Nr == currentQuestion.getAnswer()){                        // DO THIS OPERATION IF answer_Nr IS EQUAL TO ANSWER NUMBER IN currentQuestion AT THAT SPECIFIC INDEX

           score++;                                                         // INCREASE  SCORE BY 1
           textViewscore.setText("Score: "+score);                         // Score: score

        }

        showSolution();                                               // THIS FUNCTION IS TO SHOW THE WRONG AND RIGHT SOLUTIONS
    }

    private void showSolution() {

        radioButton1.setTextColor(Color.RED);       //THIS FUNCTION IS TO SET COLOR OF radioButton1 RED
        radioButton2.setTextColor(Color.RED);       //THIS FUNCTION IS TO SET COLOR OF radioButton2 RED
        radioButton3.setTextColor(Color.RED);      //THIS FUNCTION IS TO SET COLOR OF radioButton3 RED

        switch(currentQuestion.getAnswer()){

            case 1:   radioButton1.setTextColor(Color.GREEN);  textViewQuestion.setText("Answer 1 is correct");  break; //THIS FUNCTION IS TO SET COLOR OF radioButton1 GREEN

            case 2:   radioButton2.setTextColor(Color.GREEN);  textViewQuestion.setText("Answer 2 is correct");  break;//THIS FUNCTION IS TO SET COLOR OF radioButton1 GREEN

            case 3:   radioButton3.setTextColor(Color.GREEN);  textViewQuestion.setText("Answer 3 is correct");  break;//THIS FUNCTION IS TO SET COLOR OF radioButton1 GREEN

        }

        if (questionCounter < questionCountTotal){

            buttonConfirm.setText("Next");               // DO THIS OPERATION WHEN THE QUESTIONS ON THE questionList ARE NOT OVER


        }else {

            buttonConfirm.setText("Finish");             // DO THIS OPERATION WHEN THE QUESTIONS ON THE questionList ARE OVER

        }
    }

    private void finishQuiz() {        // THIS FUNCTION IS TO EXIT AFTER COMPLETING THE QUIZ

        Intent highScoreIntent = new Intent();
        highScoreIntent.putExtra(Extra_Score, score);        // PUT SCORE IN highScoreIntent
        setResult(RESULT_OK, highScoreIntent);               // SEND SCORE TO MAINACTIVIT
        finish();                                            // FINISH QUIZ

    }


    /*******************************************************
     * onBackPressed()
     * THIS FUNCTION IS TO MAKE SURE THAT THE USER IS PRESSED TWICE TO EXIT
     */

    @Override
    public void onBackPressed() {

        if (backPressedTime + 1500 > System.currentTimeMillis()){

            finishQuiz();

        }else {

            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        }

        backPressedTime = System.currentTimeMillis();

    }
}
