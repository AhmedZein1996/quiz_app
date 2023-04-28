package com.aljalad.quiz;

import android.provider.BaseColumns;

public final class Quizdb {

    private Quizdb(){

    }

    public static class Questionstable implements BaseColumns {

        //  DATABASE

        public static final String DATABASE_NAME = "Quiz_DB";
        public static final int DATABASE_VERSION = 1 ;

        //  TABLE

        public static final String TABLE_NAME = "quiz_questions";

        //  COLUMNS

        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1  = "option1";
        public static final String COLUMN_OPTION2  = "option2";
        public static final String COLUMN_OPTION3  = "option3";
        public static final String COLUMN_ANSWER_NUMBER   = "answer";
        public static final String COLUMN_DIFFCULITY  = "DIFFCULITY";
        public static final String COLUMN_CATEGORIES  = "CATEGORIES";
    }
}
