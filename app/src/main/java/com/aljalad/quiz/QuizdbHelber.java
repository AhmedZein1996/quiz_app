package com.aljalad.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.aljalad.quiz.Quizdb.Questionstable;
import com.aljalad.quiz.QuestionBank.QuestionBankArray;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizdbHelber extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public QuizdbHelber(@Nullable Context context) {
        super(context, Questionstable.DATABASE_NAME, null, Questionstable.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db=db;

        final String SQL_QUESTION_TABLE = "CREATE TABLE " +
                Questionstable.TABLE_NAME + " ( " +
                Questionstable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Questionstable.COLUMN_QUESTION + " TEXT, " +
                Questionstable.COLUMN_OPTION1 + " TEXT, " +
                Questionstable.COLUMN_OPTION2 + " TEXT, " +
                Questionstable.COLUMN_OPTION3 + " TEXT, " +
                Questionstable.COLUMN_ANSWER_NUMBER + " INTEGER, " +
                Questionstable.COLUMN_DIFFCULITY + " TEXT, " +
                Questionstable.COLUMN_CATEGORIES + " TEXT" +
                ")";
        db.execSQL(SQL_QUESTION_TABLE );
        FillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Questionstable.TABLE_NAME);
        onCreate(db);
    }


    private void FillQuestionsTable(){


        for ( int index=0 ; index < QuestionBankArray.QUESTION.length ; index++)

        {

            Question question = new Question(QuestionBankArray.QUESTION[index], QuestionBankArray.OPTION1[index], QuestionBankArray.OPTION2[index],
                                             QuestionBankArray.OPTION3[index],  QuestionBankArray.ANSWER_NUMBER[index], QuestionBankArray.DIFFCULITY[index],
                                             QuestionBankArray.CATEGORY[index]);


            addQuestions(question);

        }
    }


       /*

        Question question1 = new Question("Alpha (α) particles are helium (He) nucleus with a charge of","e","2e","3e",2, Question.DIFFCULITY_MEDIUM, Question.CATEGORY_PHYSICS);
        addQuestions(question1);

        Question question2 = new Question(" When two light nuclei combine to form a heavier nucleus, process is said to be","nuclear power","nuclear fusion","nuclear transmutation",2, Question.DIFFCULITY_HARD, Question.CATEGORY_PHYSICS);
        addQuestions(question2);

        Question question3 = new Question("If a C-14 has a half-life of 5730 years, then how long will it take for number of C-14 in a sample to drop to 1/8 of initial quantity?","2.58 × 104 years","1.44 × 104 years","1.72 × 104 years",3, Question.DIFFCULITY_HARD, Question.CATEGORY_PHYSICS);
        addQuestions(question3);

        Question question4 = new Question("Sound waves are produced by","linear motion","circular motion","vibrating bodies",3, Question.DIFFCULITY_EASY, Question.CATEGORY_PHYSICS);
        addQuestions(question4);

        Question question5 = new Question("When an acid reacts with metal carbonate, products are","water","carbon dioxide","all of above",3, Question.DIFFCULITY_HARD, Question.CATEGORY_CHEMISTRY);
        addQuestions(question5);

        Question question6 = new Question("Phenolphthalein in acidic solution is","colorless","pink colored","orange colored",1, Question.DIFFCULITY_MEDIUM, Question.CATEGORY_CHEMISTRY);
        addQuestions(question6);

        Question question7 = new Question("How many replaceable H-atoms are present in Carbonic acid?","two","three","four",1, Question.DIFFCULITY_EASY, Question.CATEGORY_CHEMISTRY);
        addQuestions(question7);

        Question question8 = new Question(" Which of following is used as an antacid and laxative?","sodium hydroxide","potassium hydroxide","magnesium hydroxide",3, Question.DIFFCULITY_HARD, Question.CATEGORY_CHEMISTRY);
        addQuestions(question8);

        Question question9 = new Question("Process in which acids (H+) and bases (OH-) react to form salts and water is called","neutralization","hydrogenation","halogenation",1, Question.DIFFCULITY_EASY, Question.CATEGORY_CHEMISTRY);
        addQuestions(question9);

        Question question10 = new Question("If a hemisphere has a curved surface area of 175cm² its radius is","5.28cm","4.8cm","3.55cm",1, Question.DIFFCULITY_MEDIUM, Question.CATEGORY_MATH);
        addQuestions(question10);

        Question question11 = new Question("Volume of cone with surface area 7cm and height 14cm is","160cm³","180cm³","120cm³",2, Question.DIFFCULITY_MEDIUM, Question.CATEGORY_MATH);
        addQuestions(question11);

        Question question12 = new Question("By solving inequality 1⁄3(x - 3) > 1⁄2(x + 2), answer will be","x < -10","x < -12","x < -14",2, Question.DIFFCULITY_HARD, Question.CATEGORY_MATH);
        addQuestions(question12);

        Question question13 = new Question("By solving equation a = 2 - 9a, value of a will be","1/5","1/2","2/3",1, Question.DIFFCULITY_EASY, Question.CATEGORY_MATH);
        addQuestions(question13);*/

    private void addQuestions(Question question){

        ContentValues Values = new ContentValues();

        Values.put(Questionstable.COLUMN_QUESTION,question.getQuestion());
        Values.put(Questionstable.COLUMN_OPTION1,question.getOption1());
        Values.put(Questionstable.COLUMN_OPTION2,question.getOption2());
        Values.put(Questionstable.COLUMN_OPTION3,question.getOption3());
        Values.put(Questionstable.COLUMN_ANSWER_NUMBER,question.getAnswer());
        Values.put(Questionstable.COLUMN_DIFFCULITY,question.getDiffculity());
        Values.put(Questionstable.COLUMN_CATEGORIES,question.getCategory());

        db.insert(Questionstable.TABLE_NAME,null,Values);

    }

    public List<Question> getAllQuestions(){

     List<Question> questionList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Questionstable.TABLE_NAME, null);

        if (cursor.moveToFirst()){

            do {

                Question question = new Question();

                question.setQuestion(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_OPTION3)));
                question.setAnswer(cursor.getInt(cursor.getColumnIndex(Questionstable.COLUMN_ANSWER_NUMBER)));
                question.setDiffculity(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_DIFFCULITY)));
                question.setCategory(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_CATEGORIES)));

                questionList.add(question);

            }while (cursor.moveToNext());
        }

        cursor.close();

        return questionList;
    }

    public List<Question> getQuestions(String diffculity , String category){

        List<Question> questionList = new ArrayList<>();

        db = getReadableDatabase();

        String[] selectionArgs = new String[]{diffculity, category};
        String selection = Questionstable.COLUMN_DIFFCULITY + " = ? " + " AND " + Questionstable.COLUMN_CATEGORIES + " = ? ";

        Cursor cursor = db.query(Questionstable.TABLE_NAME,null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()){

            do {

                Question question = new Question();

                question.setQuestion(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_OPTION3)));
                question.setAnswer(cursor.getInt(cursor.getColumnIndex(Questionstable.COLUMN_ANSWER_NUMBER)));
                question.setDiffculity(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_DIFFCULITY)));
                question.setCategory(cursor.getString(cursor.getColumnIndex(Questionstable.COLUMN_CATEGORIES)));

                questionList.add(question);

            }while (cursor.moveToNext());
        }

        cursor.close();

        return questionList;
    }

}
