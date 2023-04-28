package com.aljalad.quiz;

public class Question {

    public static final String DIFFCULITY_EASY = "Easy";
    public static final String DIFFCULITY_MEDIUM = "Medium";
    public static final String DIFFCULITY_HARD = "Hard";

    public static final String CATEGORY_MATH = "Math";
    public static final String CATEGORY_PHYSICS = "Physics";
    public static final String CATEGORY_CHEMISTRY = "Chemistry";

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int answer_number;
    private String diffculity;
    private String category;

    public Question(){

    }

    public Question(String question, String option1, String option2, String option3, int answer, String diffculity, String category) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answer_number = answer;
        this.diffculity = diffculity;
        this.category = category;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswer() {
        return answer_number;
    }

    public void setAnswer(int answer) {
        this.answer_number = answer;
    }

    public String getDiffculity() {
        return diffculity;
    }

    public void setDiffculity(String diffculity) {
        this.diffculity = diffculity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static String[] getALLDiffculityLevels(){

        return new String[]{DIFFCULITY_EASY, DIFFCULITY_MEDIUM, DIFFCULITY_HARD};

    }

    public static String[] getALLCategories(){

        return new String[]{CATEGORY_MATH, CATEGORY_PHYSICS, CATEGORY_CHEMISTRY};

    }
}
